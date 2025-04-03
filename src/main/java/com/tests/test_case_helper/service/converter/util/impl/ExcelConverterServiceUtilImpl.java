package com.tests.test_case_helper.service.converter.util.impl;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.service.converter.util.ExcelConverterServiceUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ExcelConverterServiceUtilImpl implements ExcelConverterServiceUtil {

    @Override
    public byte[] convertAndWriteToExcel(Map<String, List<TestCaseDTO>> testSuitesAndTestCases) {
        try {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("project");

                int currentRow = 0;

                for (Map.Entry<String, List<TestCaseDTO>> entry : testSuitesAndTestCases.entrySet()) {
                    Row testSuiteRow = sheet.createRow(currentRow);
                    Cell titleSuiteCell = testSuiteRow.createCell(0);

                    CellStyle testSuiteTitleCellStyle = workbook.createCellStyle();

                    testSuiteTitleCellStyle.setBorderTop(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderBottom(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderLeft(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setBorderRight(BorderStyle.THICK);
                    testSuiteTitleCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    testSuiteTitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    titleSuiteCell.setCellStyle(testSuiteTitleCellStyle);

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

    private void setBorderCellStyle(CellStyle cellStyle, BorderStyle borderStyle) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
    }

}
