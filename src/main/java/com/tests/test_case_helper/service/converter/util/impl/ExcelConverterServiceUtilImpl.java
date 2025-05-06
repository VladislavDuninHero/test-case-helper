package com.tests.test_case_helper.service.converter.util.impl;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.cases.*;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.exceptions.ExcelFileParsedException;
import com.tests.test_case_helper.service.converter.util.ExcelConverterServiceUtil;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    public ExcelConverterServiceUtilImpl(ProjectUtils projectUtils) {
        this.projectUtils = projectUtils;
    }
    
    @Override
    public byte[] convertAndWriteToExcel(Map<String, List<TestCaseDTO>> testSuitesAndTestCases) {
        try {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("project");

                int currentRow = 0;

                for (Map.Entry<String, List<TestCaseDTO>> entry : testSuitesAndTestCases.entrySet()) {
                    Row testSuiteRow = sheet.createRow(currentRow);
                    Cell testSuiteAnchor = testSuiteRow.createCell(0);
                    Cell titleSuiteCell = testSuiteRow.createCell(1);

                    CellStyle testSuiteTitleCellStyle = workbook.createCellStyle();

                    testSuiteTitleCellStyle.setBorderTop(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderBottom(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderLeft(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderRight(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    testSuiteTitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    titleSuiteCell.setCellStyle(testSuiteTitleCellStyle);
                    testSuiteAnchor.setCellStyle(testSuiteTitleCellStyle);

                    testSuiteAnchor.setCellValue("Test-suite:");
                    titleSuiteCell.setCellValue(entry.getKey());

                    currentRow++;

                    Row testSuiteHeadersRow = sheet.createRow(currentRow);

                    String[] headers = {"ID", "Title", "Testing data", "Precondition", "Steps", "Expected result"};
                    for (int headIndex = 0; headIndex < headers.length; headIndex++) {
                        sheet.autoSizeColumn(headIndex);
                    }

                    for (int i = 0; i < headers.length; i++) {
                        Cell headerCell = testSuiteHeadersRow.createCell(i);

                        CellStyle headerCellStyle = workbook.createCellStyle();

                        setBorderCellStyle(headerCellStyle, BorderStyle.THICK);

                        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        headerCell.setCellStyle(headerCellStyle);

                        headerCell.setCellValue(headers[i]);
                    }

                    currentRow++;

                    for (int i = 0; i < entry.getValue().size(); i++) {
                        TestCaseDTO testCaseDTO = entry.getValue().get(i);

                        CellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.setWrapText(true);
                        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        setBorderCellStyle(cellStyle, BorderStyle.THIN);

                        Row testCaseRow = sheet.createRow(currentRow);

                        Cell idCell = testCaseRow.createCell(0);
                        Cell titleCell = testCaseRow.createCell(1);
                        idCell.setCellStyle(cellStyle);
                        titleCell.setCellStyle(cellStyle);
                        idCell.setCellValue(testCaseDTO.getId());
                        titleCell.setCellValue(testCaseDTO.getTitle());

                        List<TestCaseDataDTO> testCaseData = testCaseDTO.getTestCaseData();
                        List<PreconditionDTO> preconditions = testCaseDTO.getPreconditions();
                        List<StepDTO> steps = testCaseDTO.getSteps();
                        List<ExpectedResultDTO> er = testCaseDTO.getExpectedResult();

                        StringBuilder multilineTestData = new StringBuilder();
                        Cell testDataCell = testCaseRow.createCell(2);
                        testDataCell.setCellStyle(cellStyle);

                        for (int dataIndex = 0; dataIndex < testCaseData.size(); dataIndex++) {
                            int step = dataIndex + 1;
                            multilineTestData.append(step);
                            multilineTestData.append(". ");
                            multilineTestData.append(testCaseData.get(dataIndex).getStep());
                            multilineTestData.append("\n");
                        }

                        StringBuilder multilineTestPreconditions = new StringBuilder();
                        Cell testPrecondtionCell = testCaseRow.createCell(3);
                        testPrecondtionCell.setCellStyle(cellStyle);

                        for (int preconditionIndex = 0; preconditionIndex < preconditions.size(); preconditionIndex++) {
                            int step = preconditionIndex + 1;
                            multilineTestPreconditions.append(step);
                            multilineTestPreconditions.append(". ");
                            multilineTestPreconditions.append(preconditions.get(preconditionIndex).getStep());
                            multilineTestPreconditions.append("\n");
                        }

                        StringBuilder multilineTestSteps = new StringBuilder();
                        Cell testStepsCell = testCaseRow.createCell(4);
                        testStepsCell.setCellStyle(cellStyle);

                        for (int stepsIndex = 0; stepsIndex < steps.size(); stepsIndex++) {
                            int step = stepsIndex + 1;
                            multilineTestSteps.append(step);
                            multilineTestSteps.append(". ");
                            multilineTestSteps.append(steps.get(stepsIndex).getStep());
                            multilineTestSteps.append("\n");
                        }

                        StringBuilder multilineTestEr = new StringBuilder();
                        Cell testErCell = testCaseRow.createCell(5);
                        testErCell.setCellStyle(cellStyle);

                        for (int erIndex = 0; erIndex < er.size(); erIndex++) {
                            int step = erIndex + 1;
                            multilineTestEr.append(step);
                            multilineTestEr.append(". ");
                            multilineTestEr.append(er.get(erIndex).getStep());
                            multilineTestEr.append("\n");
                        }

                        testDataCell.setCellValue(multilineTestData.toString());
                        testPrecondtionCell.setCellValue(multilineTestPreconditions.toString());
                        testStepsCell.setCellValue(multilineTestSteps.toString());
                        testErCell.setCellValue(multilineTestEr.toString());

                        currentRow++;
                    }

                    currentRow = currentRow + 1;
                }

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);

                return out.toByteArray();
            }
        } catch(IOException e) {
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
            try(XSSFWorkbook workbook = new XSSFWorkbook(excelInputStream)) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                TestSuite currentTestSuite = null;

                for (int i = 0; i <= sheet.getLastRowNum(); i++) {

                    if (sheet.getRow(i) == null) {
                        continue;
                    }

                    Cell firstCell = sheet.getRow(i).getCell(0);

                    if (
                            firstCell == null
                            || firstCell.toString().trim().isEmpty()
                            || firstCell.getCellType() == CellType.BLANK
                            || firstCell.getCellType() == CellType._NONE
                    ) {
                        continue;
                    }

                    if (
                            firstCell.getCellType() == CellType.STRING
                                    && firstCell.getStringCellValue().toLowerCase()
                                    .contains("test-suite:")
                    ) {
                        currentTestSuite = new TestSuite();
                        currentTestSuite.setTitle(sheet.getRow(i).getCell(1).getStringCellValue());
                        currentTestSuite.setTag(Tag.NOT_ASSIGNED);
                        currentTestSuite.setProject(project);
                        currentTestSuite.setTestsCases(new ArrayList<>());
                        newTestSuites.add(currentTestSuite);

                        continue;
                    }

                    if (
                            firstCell.getCellType() == CellType.STRING
                            && firstCell.getStringCellValue().toUpperCase().contains("ID")
                    ) {
                        continue;
                    }

                    TestCase testCase = new TestCase();
                    testCase.setTitle(sheet.getRow(i).getCell(1).getStringCellValue());
                    String[] splitTestingData = splitCell(sheet.getRow(i).getCell(2));
                    List<TestCaseData> testCaseData = Arrays.stream(splitTestingData)
                            .map(data -> {
                                TestCaseData tcData = new TestCaseData();
                                tcData.setStep(data);

                                return tcData;
                            })
                            .toList();
                    testCase.setTestCaseData(testCaseData);
                    String[] splitPrecondition = splitCell(sheet.getRow(i).getCell(3));
                    List<TestCasePrecondition> preconditions = Arrays.stream(splitPrecondition)
                            .map(precondition -> {
                                TestCasePrecondition testPrecondition = new TestCasePrecondition();
                                testPrecondition.setStep(precondition);

                                return testPrecondition;
                            })
                            .toList();
                    testCase.setTestCasePrecondition(preconditions);
                    String[] splitSteps = splitCell(sheet.getRow(i).getCell(4));
                    List<TestCaseStep> steps = Arrays.stream(splitSteps)
                            .map(step -> {
                                TestCaseStep testCaseStep = new TestCaseStep();
                                testCaseStep.setStep(step);

                                return testCaseStep;
                            })
                            .toList();
                    testCase.setSteps(steps);
                    String[] splitEr = splitCell(sheet.getRow(i).getCell(5));
                    List<TestCaseExpectedResult> testCaseExpectedResults = Arrays.stream(splitEr)
                            .map(er -> {
                                TestCaseExpectedResult tcer = new TestCaseExpectedResult();
                                tcer.setStep(er);

                                return tcer;
                            })
                            .toList();
                    testCase.setExpectedResult(testCaseExpectedResults);

                    testCase.setTestSuite(currentTestSuite);
                    if (currentTestSuite != null) {
                        currentTestSuite.getTestsCases().add(testCase);
                    }

                }

            }

        } catch (IOException e) {
            throw new ExcelFileParsedException("Parsed exception");
        }

        return newTestSuites;
    }


    private void setBorderCellStyle(CellStyle cellStyle, BorderStyle borderStyle) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
    }

    private String[] splitCell(Cell cell) {
        String[] splitCell = cell.getStringCellValue().split("\n");

        for (int stepIndex = 0; stepIndex < splitCell.length; stepIndex++) {
            String step = splitCell[stepIndex];

            if (step.contains(".")) {
                splitCell[stepIndex] = step.substring(step.indexOf(".") + 1).trim();
            }
        }

        return splitCell;
    }

}
