/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.awt.Font;
import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author Faintinger
 */
public class Excel {
    //ArrayList<String> listArchivos = new ArrayList<String>();
    private double dGradeCriterea[][] = null;

    
    
    
    public Excel (double grades[][], ArrayList<String> listArchivos, String sPath, String sName){
                  
        listArchivos.add("Average");
        int iSize = listArchivos.size();
        //dGradeCriterea = grades;
        dGradeCriterea = new double[iSize][13];
        
        for(int i = 0; i < iSize - 1; i++){
           for(int j = 0; j < 12; j++){
               dGradeCriterea[i][j] = grades[i][j];
               //System.out.print(dGradeCriterea[i][j] + " ");
            }
           System.out.println();
        }
        
        promVertical(iSize);
        promHorizontal(iSize);
            
            
        String sCritereas[] = {
            "Name",
            "File names",
            "Variable names",
            "Constant names",
            "Program contents",
            "Initial comments",
            "Library directives",
            "Comments for funtion",
            "Funtion header",
            "Indentation",
            "Comments",
            "Instructions per line",
            "Blank Spaces",     
            "GRADE"
            };
               
        try {             
            XSSFWorkbook workbook = new XSSFWorkbook();            
            XSSFFont font1 = createFont(workbook);
            XSSFFont font2 = createFont2(workbook);            
            XSSFCellStyle style = style1(workbook);
            XSSFCellStyle style2 = style2(workbook);

            style.setFont(font1);
            style2.setFont(font2);
            
            CellStyle st = workbook.createCellStyle();
            
            
            
            XSSFSheet sheet = workbook.createSheet("Results");
            
            XSSFRow rowhead = sheet.createRow((short)0);  
            rowhead.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
            XSSFCell cell;
              for (int i = 0; i < 14; i++){
                cell = rowhead.createCell(i);
                cell.setCellValue(sCritereas[i]);
                cell.setCellStyle(style);
                sheet.autoSizeColumn(i);
                if(i == 0){
                    XSSFCellStyle styleAux = style1(workbook);
                    XSSFFont fontAux = createFont(workbook);
                    fontAux.setColor(new XSSFColor(new java.awt.Color(0, 102, 204)));
                    styleAux.setFont(fontAux);
                    styleAux.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
                    styleAux.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(styleAux);
                }
                else if(i == 13){
                    XSSFCellStyle styleAux = style1(workbook);
                    styleAux.setFillForegroundColor(new XSSFColor(new java.awt.Color(146, 208, 80)));
                    styleAux.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    styleAux.setFont(font1);                    
                    cell.setCellStyle(styleAux);
                }
                    
            }
                    
            for(int i = 0; i < iSize; i++){
                XSSFRow row = sheet.createRow((short)i+1);
                cell = row.createCell(0);
                String n = listArchivos.get(i);
                cell.setCellValue(n);
                cell.setCellStyle(style2);
                for (int j = 0; j < 13; j++) {
                    cell = row.createCell(j+1);
                    cell.setCellValue(dGradeCriterea[i][j]);
                    cell.setCellStyle(style2);
                      
                }
                
            }
            sheet.autoSizeColumn(0);
            
            //FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
            FileOutputStream fileOut = new FileOutputStream(sPath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private XSSFFont createFont(XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)16);
        font.setFontName("Calibri");
        font.setBold(true);
        font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        return font;
    }
    
    private XSSFFont createFont2(XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("Calibri");
        font.setColor(new XSSFColor(new java.awt.Color(64, 64, 64)));
        return font;
    }
        static private XSSFFont failFont(XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("Calibri");
        font.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
        return font;
    }
    
    private XSSFCellStyle style1 (XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 102, 204)));
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBottomBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderLeft(BorderStyle.DOTTED);
        style.setLeftBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderRight(BorderStyle.DOTTED);
        style.setRightBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderTop(BorderStyle.DOTTED);
        style.setTopBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        return style;
    }
    
    private XSSFCellStyle style2 (XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBottomBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderLeft(BorderStyle.DOTTED);
        style.setLeftBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderRight(BorderStyle.DOTTED);
        style.setRightBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderTop(BorderStyle.DOTTED);
        style.setTopBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        return style;
    }
    
    static private XSSFCellStyle style3 (XSSFWorkbook wb){
        // Create a new font and alter it.
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBottomBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderLeft(BorderStyle.DOTTED);
        style.setLeftBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderRight(BorderStyle.DOTTED);
        style.setRightBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        style.setBorderTop(BorderStyle.DOTTED);
        style.setTopBorderColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
        return style;
    }
    
    private void promVertical(int iSize){
        double dSuma;
        for (int i = 0; i < 12; i++) {
            dSuma = 0;
            for (int j = 0; j < iSize - 1; j++) {
            dSuma += dGradeCriterea[j][i]; 
            }
            dGradeCriterea[iSize - 1][i] = dSuma/(iSize - 1);
        }
        
    }
    
    private void promHorizontal(int iSize){
        double dSuma;
        for (int i = 0; i < iSize; i++) {
            dSuma = 0;
            for (int j = 0; j < 12; j++) {
            dSuma += dGradeCriterea[i][j]; 
            }
            //dGradeCriterea[i][12] = dSuma/12;
            dGradeCriterea[i][12] = dSuma;
        }
    }
}
