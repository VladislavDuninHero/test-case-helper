package com.tests.test_case_helper.controller.converter;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.project.ExtendedProjectDTO;
import com.tests.test_case_helper.exceptions.ExcelFileParsedException;
import com.tests.test_case_helper.service.converter.ExcelConverterService;
import com.tests.test_case_helper.service.converter.WordConverterService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(Route.API_CONVERTERS_ROUTE)
public class ConvertersController {

    private final ExcelConverterService excelConverterService;
    private final WordConverterService wordConverterService;

    public ConvertersController(
            ExcelConverterService excelConverterService,
            WordConverterService wordConverterService
    ) {
        this.excelConverterService = excelConverterService;
        this.wordConverterService = wordConverterService;
    }

    @GetMapping(Route.API_EXCEL_CONVERTER_ROUTE)
    public ResponseEntity<byte[]> convertToExcel(
            @RequestParam
            @NotNull
            Long projectId
    ) {
        byte[] excel = excelConverterService.convertToExcel(projectId);

        return ResponseEntity.ok()
                .header(
                        "Content-Type",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                )
                .header(
                        "Content-Disposition",
                        "attachment; filename=project_backup.xlsx"
                )
                .contentLength(excel.length)
                .body(excel);
    }

    @PostMapping(Route.API_EXCEL_CONVERTER_ROUTE)
    public ResponseEntity<ExtendedProjectDTO> convertFromExcel(
            @RequestParam("excelFile")
            MultipartFile excelFile,
            @RequestParam("projectId")
            Long projectId
    ) {
        ExtendedProjectDTO projectDTO;
        try(InputStream fileInputStream = excelFile.getInputStream()) {
            projectDTO = excelConverterService.convertFromExcel(fileInputStream, projectId);
        } catch (IOException e) {
            throw new ExcelFileParsedException(ExceptionMessage.EXCEL_FILE_PARSED_EXCEPTION_MESSAGE);
        }

        return ResponseEntity.ok(projectDTO);
    }

    @GetMapping(Route.API_END_RUN_TEST_SUITE_SESSION_CONVERT_TO_WORD_ROUTE)
    public ResponseEntity<byte[]> convertToWord(
            @PathVariable
            Long id,
            @RequestParam
            @NotNull
            Long sessionId
    ) {
        byte[] word = wordConverterService.convertEndedTestSuiteRunSessionToWord(id, sessionId);

        return ResponseEntity.ok()
                .header(
                        "Content-Type",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                )
                .header(
                        "Content-Disposition",
                        "attachment; filename=test_suite_run_session_report.docx"
                )
                .contentLength(word.length)
                .body(word);
    }
}
