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
//&p-Report
public class Report {
    Files fArr;
    Variables vVars;
    Comments comComments;
    ArrayList<File> fList;
    ArrayList<String> sLComments = new ArrayList<String>();
    int iVars [] = new int [2];
    int iCommentsFunctions[] = new int[2];
    Double [][] iReportCriterias;
    int [] iGradCrit;
    public Report(ArrayList<File> fAux, int [] iGC) {
        fList = fAux;
        iGradCrit = iGC;
        iReportCriterias = new Double [fAux.size()][12];
    }
    //&i
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
    //&i
    public Boolean gradeFiles() {
        //try {
            for(int i = 0; i < fList.size(); i++) {
                calculateFile(i);
                iReportCriterias[i][1] = iGradCrit[1] * ((iVars[0] * 1.0)/(iVars[0] + iVars[1]));
                iReportCriterias[i][6] = iGradCrit[6] * ((iCommentsFunctions[0] * 1.0)
                            / (iCommentsFunctions[0] + iCommentsFunctions[1]));
            }
        //}
        //catch(Exception ex) {
           // return false;
       // }
        return true;  
    }
    //&i
    private Boolean calculateFile(int iPos) {
        fArr = new Files();
        vVars = new Variables();
        comComments = new Comments();
        int iCont = 0, iName = 0;
        if(fArr.openFile(fList.get(iPos).getPath())) {
            System.out.println("Abre Archivo");
            fArr.removeBlankLines();
            System.out.println("Cantidad de lineas: " + fArr.length());
            // Comentario Inicial
            if(comComments.isAFunctionComment(fArr.getLine(iCont))) {
                    String sLine = fArr.getLine(iCont).trim();
                    while(!sLine.equals("*/")) {
                        iCont++;
                        sLine = fArr.getLine(iCont).trim();
                    }
            }
            // Leer resto del archivo
            for(int i = iCont + 1; i < fArr.length(); i++) {
                // System.out.println(fArr.getLine(i));
                // Si es un comentario
                if(comComments.isAFunctionComment(fArr.getLine(i))) {
                    iName = 0;
                    String sLine = fArr.getLine(i);
                    while(!comComments.endOfComment(sLine)) {
                        i++;
                        sLine = fArr.getLine(i); 
                        if(!comComments.startParameters(sLine) && (iName == 0)) {
                            comComments.setFunctionName(sLine);
                            iName++;
                        }
                        else if(comComments.startParameters(sLine)) {
                            while(!comComments.startReturn(sLine) && !comComments.endOfComment(sLine)){
                                i++;
                                sLine = fArr.getLine(i);
                                comComments.saveParameters(sLine);
                            }
                        }                       }
                    // viene funcion
                    sLine = fArr.getLine(++i);
                    int iIndex = (comComments.checkFunctionName(sLine))? 0 : 1;
                    int iArrCom[] = comComments.checkDeclarationsOfFunctions(sLine);
                    iCommentsFunctions[iIndex] += 1; 
                    iCommentsFunctions[0] = iArrCom[0];
                    iCommentsFunctions[1] = iArrCom[1];
                    System.out.println("Funcion: " + comComments.getFunctionName() 
                            + " Comentarios: " + iCommentsFunctions[0] + " Incorrectas: " + iCommentsFunctions[1]);

                    int iArr[] = vVars.checkDeclarationsOfFunctions(sLine);
                    iVars[0] += iArr[0];
                    iVars[1] += iArr[1];
                    i++;
                }
                //Si es una declaración de variables
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
