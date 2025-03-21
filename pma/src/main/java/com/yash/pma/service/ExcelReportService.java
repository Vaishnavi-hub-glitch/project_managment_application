package com.yash.pma.service;

import org.springframework.core.io.ByteArrayResource;

public interface ExcelReportService {
    ByteArrayResource exportTasksToExcel(Integer projectId);
    ByteArrayResource exportProjectsToExcel();

}