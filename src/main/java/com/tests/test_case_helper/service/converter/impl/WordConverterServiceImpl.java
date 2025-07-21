package com.tests.test_case_helper.service.converter.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.suite.run.TestSuiteRunSessionStatisticDTO;
import com.tests.test_case_helper.enums.TestCaseStatus;
import com.tests.test_case_helper.exceptions.EndedTestSuiteRunSessionConvertToWordException;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import com.tests.test_case_helper.repository.projections.TestCaseRunResultSlimProjection;
import com.tests.test_case_helper.repository.projections.TestSuiteRunSessionProjection;
import com.tests.test_case_helper.service.converter.WordConverterService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.suite.run.TestSuiteRunSessionService;
import com.tests.test_case_helper.service.suite.run.result.TestCaseResultUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordConverterServiceImpl implements WordConverterService {

    private final TestSuiteRunSessionService testSuiteRunSessionService;
    private final TestCaseResultUtils testCaseResultService;
    private final ProjectUtils projectUtils;
    private final TestSuiteService testSuiteService;
    private final TestCaseRunResultsRepository testCaseRunResultsRepository;

    public WordConverterServiceImpl(
            TestSuiteRunSessionService testSuiteRunSessionService,
            TestCaseResultUtils testCaseResultService,
            ProjectUtils projectUtils,
            TestSuiteService testSuiteService,
            TestCaseRunResultsRepository testCaseRunResultsRepository
    ) {
        this.testSuiteRunSessionService = testSuiteRunSessionService;
        this.testCaseResultService = testCaseResultService;
        this.projectUtils = projectUtils;
        this.testSuiteService = testSuiteService;
        this.testCaseRunResultsRepository = testCaseRunResultsRepository;
    }

    @Override
    public byte[] convertEndedTestSuiteRunSessionToWord(Long suiteId, Long sessionId) {

        try (XWPFDocument doc = new XWPFDocument()) {
            TestSuiteRunSessionProjection session = testSuiteRunSessionService
                    .findTestSuiteRunSessionByIdAndReturn(sessionId);

            String totalTime = testSuiteRunSessionService.calculateRunTestSuiteSessionExecutionTimeForWord(session);

            List<TestSuiteRunSessionStatisticDTO> statistic = testCaseResultService
                    .getStatusStatisticsBySessionId(sessionId);

            Long project = testSuiteService.getTestSuite(suiteId).getProjectId();
            String projectTitle = projectUtils.getProjectById(project).getTitle();

            List<TestCaseRunResultSlimProjection> results = testCaseRunResultsRepository
                    .findAllTestCaseRunResultsSlimWithoutPageableById(sessionId);

            addTitle(doc, "Test Suite Run Session report:");
            addSessionInfo(doc, session, totalTime, projectTitle);
            addTestingSessionStatistic(doc, statistic, sessionId);
            addSessionResults(doc, results);

            return convertToByteArray(doc);
        } catch (IOException e) {
            throw new EndedTestSuiteRunSessionConvertToWordException(
                    ExceptionMessage.ENDED_TEST_SUITE_RUN_SESSION_CONVERT_TO_WORD_EXCEPTION
            );
        }

    }

    private void addTitle(XWPFDocument doc, String newTitle) {
        XWPFParagraph title = doc.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = title.createRun();
        run.setText(newTitle);
        run.setBold(true);
        run.setFontSize(16);
        run.addBreak();
    }

    private void addSessionInfo(
            XWPFDocument doc,
            TestSuiteRunSessionProjection session,
            String totalTime,
            String projectTitle
    ) {
        XWPFParagraph sessionInfo = doc.createParagraph();

        addKeyValueLine(sessionInfo, "QA: ", session.getExecutedBy().getLogin());
        addKeyValueLine(sessionInfo, "Project: ", projectTitle);
        addKeyValueLine(sessionInfo, "Test-suite: ", session.getTestSuiteTitle());
        addKeyValueLine(sessionInfo, "Environment: ", session.getEnvironment());
        addKeyValueLine(sessionInfo, "Time: ", totalTime);
    }

    private void addTestingSessionStatistic(
            XWPFDocument doc,
            List<TestSuiteRunSessionStatisticDTO> statistic,
            Long sessionId
    ) {
        Map<String, List<String>> statusMap = new LinkedHashMap<>();
        statusMap.put(TestCaseStatus.PASSED.name(), List.of("Passed: ", "00FF00"));
        statusMap.put(TestCaseStatus.FAILED.name(), List.of("Failed: ", "FF0000"));
        statusMap.put(TestCaseStatus.BLOCKED.name(), List.of("Blocked: ", "DC143C"));
        statusMap.put(TestCaseStatus.SKIPPED.name(), List.of("Skipped: ", "0000FF"));
        statusMap.put(TestCaseStatus.NOT_TESTING.name(), List.of("Not testing: ", "808080"));
        statusMap.put(TestCaseStatus.PASSED.name(), List.of("Passed: ", "00FF00"));

        addTitle(doc, "Testing session results:");

        Integer totalResultsCount = testCaseRunResultsRepository.countAllResultsForSession(sessionId);

        XWPFParagraph sessionStatisticResultInfo = doc.createParagraph();

        Long progress = analyzeStatistic(statistic, totalResultsCount);

        addKeyValueLine(
                sessionStatisticResultInfo,
                "Progress: ",
                progress + " of " + totalResultsCount
        );

        Map<String, Long> statusCountMap = statistic.stream().collect(Collectors.toMap(
                TestSuiteRunSessionStatisticDTO::getStatus,
                TestSuiteRunSessionStatisticDTO::getCount
        ));

        statusMap.forEach((status, displayInfo) -> {
                    Long count = statusCountMap.getOrDefault(status, 0L);
                    addKeyValueLineWithColor(
                            sessionStatisticResultInfo,
                            displayInfo.get(0),
                            count.toString(),
                            displayInfo.get(1)
                    );
                }
        );

    }

    private void addSessionResults(
            XWPFDocument doc,
            List<TestCaseRunResultSlimProjection> results
    ) {
        Map<String, List<String>> statusMap = Map.ofEntries(
                Map.entry(TestCaseStatus.PASSED.name(), List.of("PASSED", "00FF00")),
                Map.entry(TestCaseStatus.FAILED.name(), List.of("FAILED", "FF0000")),
                Map.entry(TestCaseStatus.BLOCKED.name(), List.of("BLOCKED", "DC143C")),
                Map.entry(TestCaseStatus.SKIPPED.name(), List.of("SKIPPED", "0000FF")),
                Map.entry(TestCaseStatus.NOT_TESTING.name(), List.of("NOT TESTING", "808080"))
        );

        addTitle(doc, "Details:");

        XWPFTable table = doc.createTable();

        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Title");
        headerRow.addNewTableCell().setText("Actual result");
        headerRow.addNewTableCell().setText("Comment");
        headerRow.addNewTableCell().setText("Status");

        setHeaderStyle(headerRow.getTableCells());

        int[] columnWidths = {4000, 500, 500, 4000};
        int tableWidth = Arrays.stream(columnWidths).sum();

        CTTbl ctTable = table.getCTTbl();
        CTTblPr tblPr = ctTable.addNewTblPr();
        CTTblWidth tblWidth = tblPr.addNewTblW();
        tblWidth.setType(STTblWidth.DXA);
        tblWidth.setW(BigInteger.valueOf(tableWidth));

        CTTblGrid grid = ctTable.addNewTblGrid();
        for (int width : columnWidths) {
            grid.addNewGridCol().setW(BigInteger.valueOf(width));
        }

        for (TestCaseRunResultSlimProjection result : results) {
            XWPFTableRow row = table.createRow();

            String actualResult = result.getActualResult().isEmpty() ? "-" : result.getActualResult();
            String comment = result.getComment().isEmpty() ? "-" : result.getComment();

            XWPFTableCell titleCell = row.getCell(0);
            titleCell.setText(result.getTestCaseTitle());

            XWPFTableCell actualResultCell = row.getCell(1);
            actualResultCell.setText(actualResult);
            actualResultCell.setWidth(String.valueOf(columnWidths[1]));
            CTTcPr tcpr = actualResultCell.getCTTc().addNewTcPr();
            CTTblWidth tcw = tcpr.addNewTcW();
            tcw.setType(STTblWidth.DXA);
            tcw.setW(BigInteger.valueOf(columnWidths[1]));
            actualResultCell.getParagraphs()
                    .forEach(paragraph -> paragraph.setAlignment(ParagraphAlignment.CENTER));


            XWPFTableCell commentCell = row.getCell(2);
            commentCell.setText(comment);
            commentCell.setWidth(String.valueOf(columnWidths[2]));
            commentCell.getParagraphs()
                    .forEach(paragraph -> paragraph.setAlignment(ParagraphAlignment.CENTER));

            XWPFTableCell statusCell = row.getCell(3);
            statusCell.setText(statusMap.get(result.getStatus()).get(0));
            statusCell.setWidth(String.valueOf(columnWidths[3]));

            for (XWPFParagraph paragraph : statusCell.getParagraphs()) {
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setColor(statusMap.get(result.getStatus()).get(1));
                }
            }

        }

        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                cell.getCTTc().addNewTcPr().addNewNoWrap();
            }
        }

    }

    private void addKeyValueLine(XWPFParagraph paragraph, String key, String value) {
        XWPFRun run = paragraph.createRun();

        run.setText(key);
        run.setBold(true);
        run = paragraph.createRun();
        run.setText(value);
        run.addBreak();
    }

    private void addKeyValueLineWithColor(XWPFParagraph paragraph, String key, String value, String color) {
        XWPFRun run = paragraph.createRun();
        run.setText(key);
        run.setColor(color);
        run.setBold(true);

        run = paragraph.createRun();
        run.setText(value);
        run.addBreak();
    }

    private Long analyzeStatistic(List<TestSuiteRunSessionStatisticDTO> statistic, Integer totalResults) {
        Long notTestingCount = 0L;
        for (TestSuiteRunSessionStatisticDTO stat : statistic) {
            if (TestCaseStatus.NOT_TESTING.name().equals(stat.getStatus())) {
                notTestingCount = stat.getCount();
                break;
            }
        }

        return totalResults - notTestingCount;
    }

    private void setHeaderStyle(List<XWPFTableCell> cells) {
        for (XWPFTableCell cell : cells) {
            cell.setColor("DDDDDD");

            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                    run.setFontSize(14);
                }
            }
        }
    }

    private byte[] convertToByteArray(XWPFDocument doc) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            doc.write(out);

            return out.toByteArray();
        } catch (IOException e) {
            throw new EndedTestSuiteRunSessionConvertToWordException(
                    ExceptionMessage.ENDED_TEST_SUITE_RUN_SESSION_CONVERT_TO_WORD_EXCEPTION
            );
        }
    }
}
