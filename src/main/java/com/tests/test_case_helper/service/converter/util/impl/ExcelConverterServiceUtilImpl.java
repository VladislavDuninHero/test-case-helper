package com.tests.test_case_helper.service.converter.util.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.cases.*;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.exceptions.ExcelFileParsedException;
import com.tests.test_case_helper.service.converter.util.ExcelConverterServiceUtil;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ExcelConverterServiceUtilImpl implements ExcelConverterServiceUtil {

    private final ProjectUtils projectUtils;
    private final EvictService evictService;

    public ExcelConverterServiceUtilImpl(
            ProjectUtils projectUtils,
            EvictService evictService
    ) {
        this.projectUtils = projectUtils;
        this.evictService = evictService;
    }

    @Override
    public byte[] convertAndWriteToExcel(Map<String, List<TestCaseDTO>> testSuitesAndTestCases) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            WriteCellStyle testSuiteTitleCellStyle = new WriteCellStyle();
            testSuiteTitleCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            testSuiteTitleCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            setBorderCellStyle(testSuiteTitleCellStyle, BorderStyle.THICK);

            WriteCellStyle headerCellStyle = new WriteCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            setBorderCellStyle(headerCellStyle, BorderStyle.THICK);

            WriteCellStyle contentCellStyle = new WriteCellStyle();
            contentCellStyle.setWrapped(true);
            contentCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            setBorderCellStyle(contentCellStyle, BorderStyle.THIN);

            // Создаем список данных для записи
            List<List<Object>> data = new ArrayList<>();
            List<WriteCellStyle> rowStyles = new ArrayList<>();

            for (Map.Entry<String, List<TestCaseDTO>> entry : testSuitesAndTestCases.entrySet()) {
                // Добавляем строку тест-сьюта
                data.add(Arrays.asList("Test-suite:", entry.getKey()));
                rowStyles.add(testSuiteTitleCellStyle);

                data.add(Arrays.asList("ID", "Title", "Testing data", "Precondition", "Steps", "Expected result"));
                rowStyles.add(headerCellStyle);

                for (TestCaseDTO testCaseDTO : entry.getValue()) {
                    List<Object> row = new ArrayList<>();
                    row.add(testCaseDTO.getId());
                    row.add(testCaseDTO.getTitle());

                    StringBuilder testDataBuilder = new StringBuilder();
                    for (int i = 0; i < testCaseDTO.getTestCaseData().size(); i++) {
                        testDataBuilder.append(i + 1).append(". ")
                                .append(testCaseDTO.getTestCaseData().get(i).getStep());
                        if (i < testCaseDTO.getTestCaseData().size() - 1) {
                            testDataBuilder.append("\n");
                        }
                    }
                    row.add(testDataBuilder.toString());

                    // Preconditions
                    StringBuilder preconditionsBuilder = new StringBuilder();
                    for (int i = 0; i < testCaseDTO.getPreconditions().size(); i++) {
                        preconditionsBuilder.append(i + 1).append(". ")
                                .append(testCaseDTO.getPreconditions().get(i).getStep());
                        if (i < testCaseDTO.getPreconditions().size() - 1) {
                            preconditionsBuilder.append("\n");
                        }
                    }
                    row.add(preconditionsBuilder.toString());

                    // Steps
                    StringBuilder stepsBuilder = new StringBuilder();
                    for (int i = 0; i < testCaseDTO.getSteps().size(); i++) {
                        stepsBuilder.append(i + 1).append(". ")
                                .append(testCaseDTO.getSteps().get(i).getStep());
                        if (i < testCaseDTO.getSteps().size() - 1) {
                            stepsBuilder.append("\n");
                        }
                    }
                    row.add(stepsBuilder.toString());

                    // Expected result
                    StringBuilder erBuilder = new StringBuilder();
                    for (int i = 0; i < testCaseDTO.getExpectedResult().size(); i++) {
                        erBuilder.append(i + 1).append(". ")
                                .append(testCaseDTO.getExpectedResult().get(i).getStep());
                        if (i < testCaseDTO.getExpectedResult().size() - 1) {
                            erBuilder.append("\n");
                        }
                    }
                    row.add(erBuilder.toString());

                    data.add(row);
                    rowStyles.add(contentCellStyle);
                }

                // Добавляем пустую строку между сьютами
                data.add(new ArrayList<>());
                rowStyles.add(new WriteCellStyle());
            }

            ExcelWriter excelWriter = EasyExcel.write(out)
                    .registerWriteHandler(new HorizontalCellStyleStrategy(null, rowStyles))
                    .build();

            WriteSheet sheet = EasyExcel.writerSheet("project").build();
            excelWriter.write(data, sheet);
            excelWriter.finish();

            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TestSuite> parseFromExcel(InputStream excelInputStream, Long projectId) {
        Project project = projectUtils.getProjectById(projectId);
        List<TestSuite> projectTestSuites = project.getTestsSuites();
        List<TestSuite> newTestSuites = new ArrayList<>(projectTestSuites);

        try {
            List<Map<Integer, String>> data = EasyExcel.read(excelInputStream)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();

            TestSuite currentTestSuite = null;

            for (int i = 0; i < data.size(); i++) {
                Map<Integer, String> row = data.get(i);
                if (row.isEmpty() || row.get(0) == null || row.get(0).trim().isEmpty()) {
                    continue;
                }

                String firstCellValue = row.get(0);

                if (firstCellValue != null && firstCellValue.toLowerCase().contains("test-suite:")) {
                    currentTestSuite = new TestSuite();
                    currentTestSuite.setTitle(row.get(1));
                    currentTestSuite.setTag(Tag.NOT_ASSIGNED);
                    currentTestSuite.setProject(project);
                    currentTestSuite.setTestsCases(new ArrayList<>());
                    newTestSuites.add(currentTestSuite);
                    continue;
                }

                if (firstCellValue != null && firstCellValue.equalsIgnoreCase("ID")) {
                    continue;
                }

                if (currentTestSuite != null && row.get(1) != null) {
                    TestCase testCase = new TestCase();
                    testCase.setTitle(row.get(1));

                    // Testing data
                    String[] testDataSteps = splitCell(row.get(2));
                    List<TestCaseData> testCaseData = new ArrayList<>();
                    for (String step : testDataSteps) {
                        TestCaseData dataItem = new TestCaseData();
                        dataItem.setStep(step);
                        testCaseData.add(dataItem);
                    }
                    testCase.setTestCaseData(testCaseData);

                    // Preconditions
                    String[] preconditionSteps = splitCell(row.get(3));
                    List<TestCasePrecondition> preconditions = new ArrayList<>();
                    for (String step : preconditionSteps) {
                        TestCasePrecondition precondition = new TestCasePrecondition();
                        precondition.setStep(step);
                        preconditions.add(precondition);
                    }
                    testCase.setTestCasePrecondition(preconditions);

                    // Steps
                    String[] stepSteps = splitCell(row.get(4));
                    List<TestCaseStep> steps = new ArrayList<>();
                    for (String step : stepSteps) {
                        TestCaseStep caseStep = new TestCaseStep();
                        caseStep.setStep(step);
                        steps.add(caseStep);
                    }
                    testCase.setSteps(steps);

                    // Expected result
                    String[] erSteps = splitCell(row.get(5));
                    List<TestCaseExpectedResult> expectedResults = new ArrayList<>();
                    for (String step : erSteps) {
                        TestCaseExpectedResult er = new TestCaseExpectedResult();
                        er.setStep(step);
                        expectedResults.add(er);
                    }
                    testCase.setExpectedResult(expectedResults);

                    testCase.setTestSuite(currentTestSuite);
                    currentTestSuite.getTestsCases().add(testCase);
                }
            }
        } catch (Exception e) {
            throw new ExcelFileParsedException("Parsed exception");
        }

        return newTestSuites;
    }

    private String[] splitCell(String cellValue) {
        if (cellValue == null) {
            return new String[0];
        }

        String[] splitCell = cellValue.split("\n");
        for (int i = 0; i < splitCell.length; i++) {
            if (splitCell[i].contains(".")) {
                splitCell[i] = splitCell[i].substring(splitCell[i].indexOf(".") + 1).trim();
            }
        }
        return splitCell;
    }

    private void setBorderCellStyle(WriteCellStyle style, BorderStyle borderStyle) {
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
    }
}