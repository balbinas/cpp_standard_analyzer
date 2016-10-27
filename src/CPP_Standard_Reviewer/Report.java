/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Faintinger
 */
public class Report {
    Files fArr;
    Variables vVars;
    ArrayList<File> fList;
    ArrayList<String> sLComments = new ArrayList<String>();
    int iVars [] = new int [2];
    Double [][] iReportCriterias;
    int [] iGradCrit;
    public Report(ArrayList<File> fAux, int [] iGC) {
        fList = fAux;
        iGradCrit = iGC;
        iReportCriterias = new Double [fAux.size()][12];
    }
    
    public Boolean generateReport(String sPath, String sName) {
        gradeFiles();
        for(int i = 0; i < fList.size(); i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(iReportCriterias[i][j] + " ");
            }
             System.out.println();
        }
        return true;
    }
    
    public Boolean gradeFiles() {
        //try {
            for(int i = 0; i < fList.size(); i++) {
                calculateFile(i);
                iReportCriterias[i][1] = iGradCrit[1] * ((iVars[0] * 1.0)/(iVars[0] + iVars[1]));
            }
        //}
        //catch(Exception ex) {
           // return false;
       // }
        return true;  
    }
    
    private Boolean calculateFile(int iPos) {
        fArr = new Files();
        vVars = new Variables();
        if(fArr.openFile(fList.get(iPos).getPath())) {
            System.out.println("Abre Archivo");
            fArr.removeBlankLines();
            System.out.println("Cantidad de lineas: " + fArr.length());
            for(int i = 0; i < fArr.length(); i++) {
                //System.out.println(fArr.getLine(i));
                if(fArr.getLine(i).trim().equals("/*")){
                    String sLine = fArr.getLine(i);
                    while(!sLine.trim().equals("*/")) {
                        i++;
                        sLine = fArr.getLine(i); 
                    }
                    i++;
                }
                if(vVars.isADeclaration(fArr.getLine(i))) {
                    int iArr[] = vVars.checkDeclarations(fArr.getLine(i));
                    iVars[0] += iArr[0];
                    iVars[1] += iArr[1];
                    System.out.println("Variables Correctas: " + iArr[0] + " Incorrectas: " + iArr[1]);
                }
            }
            return true;
        } 
        else {
            return false;
        }
    }
    
}
