package com.spring.main.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spring.main.entity.BaiDang;

public class BaiDangExport {
	public final int COLUMN_INDEX_ID = 0;
	public final int COLUMN_INDEX_TITLE = 1;
	public final int COLUMN_INDEX_AUTHOR = 2;
	public final int COLUMN_INDEX_DATE = 3;
	public final int COLUMN_INDEX_VIEW = 4;
	public final int COLUMN_INDEX_IMAGE = 5;
	public final int COLUMN_INDEX_ACCOUT = 6;
	public final int COLUMN_INDEX_DESCRIPTION = 7;
	public final int COLUMN_INDEX_CONTENT = 8;
	
	public void writeExcelBlogs(List<BaiDang> baiDangs, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);
 
        // Create sheet
        Sheet sheet = workbook.createSheet("Books"); // Create sheet with sheet name
 
        int rowIndex = 0;
         
        // Write header
        writeHeader(sheet, rowIndex);
 
        // Write data
        rowIndex++;
        for (BaiDang baiDang : baiDangs) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(baiDang, row);
            rowIndex++;
        }
 
        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);
 
        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }
 
 
    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;
 
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
 
    // Write header with format
    private  void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
        // Create row
        Row row = sheet.createRow(rowIndex);
         
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("ID BÀI ĐĂNG");
   
        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TIÊU ĐỀ BÀI ĐĂNG");
 
        cell = row.createCell(COLUMN_INDEX_AUTHOR);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TÁC GIẢ BÀI ĐĂNG");
 
        cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NGÀY ĐĂNG");
 
        cell = row.createCell(COLUMN_INDEX_VIEW);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("LƯỢT XEM");
        
        cell = row.createCell(COLUMN_INDEX_IMAGE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("ẢNH NỀN");
 
        cell = row.createCell(COLUMN_INDEX_ACCOUT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("TÀI KHOẢN ĐĂNG");
 
        cell = row.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("MÔ TẢ");
        
        cell = row.createCell(COLUMN_INDEX_CONTENT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NỘI DUNG");
    }
 
    // Write data
    private  void writeBook(BaiDang baiDang, Row row) {
         
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(baiDang.getIdBaiDang());
 
        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellValue(baiDang.getTieuDeBaiDang());
 
        cell = row.createCell(COLUMN_INDEX_AUTHOR);
        cell.setCellValue(baiDang.getTacGiaBaiDang());
 
        cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellValue(baiDang.getNgayDang().toString());
 
        cell = row.createCell(COLUMN_INDEX_VIEW);
        cell.setCellValue(baiDang.getLuotXemBaiDang());
        
        cell = row.createCell(COLUMN_INDEX_IMAGE);
        cell.setCellValue(baiDang.getAnhNen());
 
        cell = row.createCell(COLUMN_INDEX_ACCOUT);
        cell.setCellValue(baiDang.getTaiKhoanBaiDang().getTenDangNhap());
 
        cell = row.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellValue(baiDang.getMoTaBaiDang());
        
        cell = row.createCell(COLUMN_INDEX_CONTENT);
        cell.setCellValue(baiDang.getNoiDungBaiDang());
         
        // Create cell formula
   
    }
 
    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Open sans"); 
        font.setBold(true);
        font.setFontHeightInPoints((short) 10); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color
 
        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }
     
     
    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
     
    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
	
}
