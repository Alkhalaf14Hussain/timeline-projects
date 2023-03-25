package com.example.demo1;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileReader {
    private static ArrayList<Project>  listOfProjects = new ArrayList<>();


    private static void loadProjects(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        Sheet sheet = (Sheet) workbook.getSheet("Sheet1");
        DateTimeFormatter longFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"); // TODO will be used later
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        int cout = 1;
        Row row = sheet.getRow(cout);

        // looping through rows
        while (row != null){
            row = sheet.getRow(cout);
            if (row == null) break; // new row could be null.

            // Set Project Information.
            Project project = new Project(row.getCell(0).toString());
            String projectId = row.getCell(1).toString();
            int reachedStage = (int) row.getCell(2).getNumericCellValue();
            if (row.getCell(3)!=null) {
                String sdate = row.getCell(3).toString();
                LocalDate dateTime = LocalDate.parse(sdate, shortFormatter);
                project.setStartDate(dateTime);
            }
            if (row.getCell(4)!=null){
                String sdate = row.getCell(4).toString();
                LocalDate dateTime = LocalDate.parse(sdate, shortFormatter);
                project.setEndDate(dateTime);
            }
            if (row.getCell(7)!= null){
                String sdate = row.getCell(7).toString();
                LocalDate dateTime = LocalDate.parse(sdate, longFormatter);
                project.setCreatedOn(dateTime);
            }
            if (row.getCell(8)!= null){
                String sdate = row.getCell(8).toString();
                LocalDate dateTime = LocalDate.parse(sdate, longFormatter);
                project.setChangedOn(dateTime);
            }
            project.setProjectId(projectId);
            project.setStageCount(reachedStage);
            // Adding the project to project list.
            listOfProjects.add(project);

            cout++;
        }
    }
    private static void loadStages(String filePath1, String filePath2) throws IOException {
        FileInputStream file1 = new FileInputStream(filePath1);
        HSSFWorkbook workbook1 = new HSSFWorkbook(file1);
        Sheet sheet1 = (Sheet) workbook1.getSheet("Sheet1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        FileInputStream file2 = new FileInputStream(filePath2);
        HSSFWorkbook workbook2 = new HSSFWorkbook(file2);
        Sheet sheet2 = (Sheet) workbook2.getSheet("Sheet1"); //Object Value	Document Number	Date	Time	Language Key

        int cout = 1; // skip first row
        Row row1 = sheet1.getRow(cout);
        Row row2 = sheet2.getRow(cout);

//        looping through rows TODO CLEAN THE CODE
        int index = 0;
        int currentMax = 0;
        while (row1 != null) {
            row1 = sheet1.getRow(cout);
            row2 = sheet2.getRow(cout);
            if (row1 == null) break;

            String object_val = row1.getCell(0).toString();
            // To correctly map stages to the right project
            if (!object_val.equals(listOfProjects.get(index).getProjectNum())) {
                currentMax = 0;
                index++;
                continue;
            }
            Stages stage;
            try {
                int newValue = (int) row1.getCell(5).getNumericCellValue();
                if (newValue >= currentMax) {
                    currentMax = newValue;
                    stage = new ProgressStage((int) row1.getCell(1).getNumericCellValue());
                } else {
                    stage = new ReworkStage((int) row1.getCell(1).getNumericCellValue());
                }
                stage.setNewValue(newValue);
                if (row1.getCell(6) != null)
                    stage.setOldValue((int) row1.getCell(6).getNumericCellValue());
                if (row2.getCell(2) != null) {
                    String sdate = row2.getCell(2).toString();
                    LocalDate dateTime = LocalDate.parse(sdate, formatter);
                    stage.setDate(dateTime);
                }
                listOfProjects.get(index).addStage(stage);
            } catch (Exception e ) {
                continue;
            }
            cout++;
        }
    }
    public static ArrayList<Project> getProjects(String filePath1, String filePath2, String filePath3) throws IOException {
        loadProjects(filePath1);
        loadStages(filePath2, filePath3);
        return listOfProjects;
    }
}
