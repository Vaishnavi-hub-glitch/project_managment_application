package com.yash.pma.serviceimpl;

import com.yash.pma.domain.Project;
import com.yash.pma.domain.Task;
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.repository.TaskRepository;
import com.yash.pma.service.ExcelReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelReportServiceImpl implements ExcelReportService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public ByteArrayResource exportTasksToExcel(Integer projectId) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Tasks");

            // Header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Task ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Start Date");
            headerRow.createCell(3).setCellValue("End Date");
            headerRow.createCell(4).setCellValue("User List");
            headerRow.createCell(5).setCellValue("Priority");
            headerRow.createCell(6).setCellValue("Status");
            headerRow.createCell(7).setCellValue("Project ID");

            // Data rows
            int rowNum = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<Task> tasks = taskRepository.findAll().stream().filter(projects -> projects.getProjectId()==projectId).collect(Collectors.toUnmodifiableList());
            for (Task task : tasks) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(task.getTaskId());
                row.createCell(1).setCellValue(task.getName());
                row.createCell(2).setCellValue(task.getStartDate() != null ? dateFormat.format(task.getStartDate()) : "");
                row.createCell(3).setCellValue(task.getEndDate() != null ? dateFormat.format(task.getEndDate()) : "");
                row.createCell(4).setCellValue(task.getUserList() != null ? task.getUserList().toString() : "");
                row.createCell(5).setCellValue(task.getTaskPriority() != null ? task.getTaskPriority().toString() : "");
                row.createCell(6).setCellValue(task.getStatus() != null ? task.getStatus().toString() : "");
                row.createCell(7).setCellValue(task.getProjectId() != null ? task.getProjectId().toString() : "");
            }

            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ByteArrayResource exportProjectsToExcel() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Projects");

            // Header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Project ID");
            headerRow.createCell(1).setCellValue("Project Name");
            headerRow.createCell(2).setCellValue("Project Description");
            headerRow.createCell(3).setCellValue("Start Date");
            headerRow.createCell(4).setCellValue("End Date");
            headerRow.createCell(5).setCellValue("User List");

            // Data rows
            int rowNum = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<Project> projects = projectRepository.findAll();
            for (Project project : projects) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(project.getProjectId());
                row.createCell(1).setCellValue(project.getProjectName());
                row.createCell(2).setCellValue(project.getProjectDescription());
                row.createCell(3).setCellValue(project.getStartDate() != null ? dateFormat.format(project.getStartDate()) : "");
                row.createCell(4).setCellValue(project.getEndDate() != null ? dateFormat.format(project.getEndDate()) : "");
                row.createCell(5).setCellValue(project.getUserList() != null ? project.getUserList().toString() : "");
            }

            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}