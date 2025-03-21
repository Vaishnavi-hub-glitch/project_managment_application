package com.yash.pma.service;

import com.yash.pma.domain.Task;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface TaskReportService {
    ByteArrayResource exportTasksToExcel(Integer projectId);

}