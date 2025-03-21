package com.yash.pma.controller;

import com.yash.pma.service.ExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ExcelReportController {

    @Autowired
    private ExcelReportService taskReportService;

    @GetMapping("/tasks/export/project/{projectId}")
    public ResponseEntity<ByteArrayResource> exportTasksByProject(@PathVariable Integer projectId) {
        ByteArrayResource resource = taskReportService.exportTasksToExcel(projectId);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project-tasks.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/projects/export")
    public ResponseEntity<ByteArrayResource> exportAllProjects() {
        ByteArrayResource resource = taskReportService.exportProjectsToExcel();

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=projects.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}