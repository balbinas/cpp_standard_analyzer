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
    Libraries libLibraries;
    ArrayList<File> fList;
    ArrayList<String> sLComments = new ArrayList<String>();
    int iVars [] = new int [2]; //grades of variables
    int iCommentsFunctions[] = new int[2]; // grades of comments
    int iFileName[] = new int[2]; // grades of file name
    int iInitialComments[] = new int [2]; // grade of the initial comments
    int iFunctionHeaders[] = new int [2]; // grade of the function declaration
    int iLibraries [] = new int [2]; // grade of the libraries
    double [][] iReportCriterias;
    int [] iGradCrit;
    //&i
    public Report(ArrayList<File> fAux, int [] iGC) {
        fList = fAux;
        iGradCrit = iGC;
        iReportCriterias = new double [fAux.size()][13];
    }
    //&i
    private ArrayList<String> listFiles() {
        ArrayList<String> files = new ArrayList<String>();
        for(int i = 0; i < fList.size(); i++)
            files.add(fList.get(i).getName());
        return files;
    }
    //&i
    private void cleanVariables() {
        iVars = new int [2]; //grades of variables
        iCommentsFunctions = new int[2]; // grades of comments
        iFileName = new int[2]; // grades of file name
        iInitialComments = new int [2]; // grade of the initial comments
        iFunctionHeaders = new int [2]; // grade of the functions
        iLibraries = new int [2]; // grade of the libraries
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
        Excel excReport = new Excel(iReportCriterias, listFiles());
        return true;
    }
    //&i
    public Boolean gradeFiles() {
        //try {
            for(int i = 0; i < fList.size(); i++) {
                cleanVariables();
                calculateFile(i);
                iReportCriterias[i][0] = iGradCrit[0] * ((iFileName[0] * 1.0) 
                        / (iFileName[0] + iFileName[1]));
                iReportCriterias[i][1] = iGradCrit[1] * ((iVars[0] * 1.0)/(iVars[0] + iVars[1]));
                iReportCriterias[i][4] = iGradCrit[4] * (iInitialComments[0] * 1.0)
                        / (iInitialComments[0] + iInitialComments[1]);
                iReportCriterias[i][5] = iGradCrit[5] * ((iLibraries[0] * 1.0) 
                        / (iLibraries[0] + iLibraries[1]));
                iReportCriterias[i][6] = iGradCrit[6] * ((iCommentsFunctions[0] * 1.0)
                        / (iCommentsFunctions[0] + iCommentsFunctions[1]));
                iReportCriterias[i][7] = iGradCrit[7] * ((iFunctionHeaders[0] * 1.0)
                        / (iFunctionHeaders[0] + iFunctionHeaders[1]));
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
        libLibraries = new Libraries();
        int iCont = 0, iName = 0;
        if(fArr.openFile(fList.get(iPos).getPath())) {
            System.out.println("Abre Archivo");
            fArr.removeBlankLines();
            System.out.println("Cantidad de lineas: " + fArr.length());
            // Initial Comment
            if(comComments.isAFunctionComment(fArr.getLine(iCont))) {
                    String sLine = fArr.getLine(++iCont).trim();
                    comComments.saveFileName(sLine);
                    int iAux[] = comComments.checkFileName(fList.get(iPos).getName());
                    iFileName[0] = iAux[0];
                    iFileName[1] = iAux[1];
                    while(!comComments.endOfComment(sLine)) {
                        iCont++;
                        sLine = fArr.getLine(iCont).trim();
                    }
            }
            // Read the rest of the file
            for(int i = iCont + 1; i < fArr.length(); i++) {
                // System.out.println(fArr.getLine(i));
                //if is an include statement
                if(libLibraries.isALibraryStatement(fArr.getLine(i))) {
                    libLibraries.checkLibrary(fArr.getLine(i));
                }
                //if is a using namespace
                if(libLibraries.isUsing(fArr.getLine(i))) {
                   libLibraries.checkSTD(fArr.getLine(i));
                }
                // if it's a comment
                if(comComments.isAFunctionComment(fArr.getLine(i))) {
                    iName = 0;
                    String sLine = fArr.getLine(i);
                    while(!comComments.endOfComment(sLine)) {
                        i++; sLine = fArr.getLine(i); 
                        //System.out.println(fArr.getLine(i));
                        if(!comComments.startParameters(sLine) && (iName == 0)) {
                            //System.out.println("Funcion: " + sLine);
                            comComments.setFunctionName(sLine);
                            iName++;
                        }
                        else {
                            if(comComments.startParameters(sLine)) {
                                comComments.cleanParameters();
                                i++; sLine = fArr.getLine(i).trim();
                                while(!comComments.startReturn(sLine) && !comComments.endOfComment(sLine)){
                                    //System.out.println("Parametros agregar: " + sLine);
                                    if(!sLine.split(" ")[0].toLowerCase().equals("none"))
                                        comComments.saveParameters(sLine);
                                    i++; sLine = fArr.getLine(i).trim();
                                }
                            }
                        }
                    }
                    sLine = fArr.getLine(++i);
                    int iIndex = (comComments.checkFunctionName(sLine))? 0 : 1;
                    //System.out.println("Index: " + iIndex);
                    int iArrCom[] = comComments.checkDeclarationsOfFunctions(sLine);
                    iCommentsFunctions[iIndex] += 1; 
                    System.out.println("Funcion Name: " + comComments.getFunctionName() 
                            + " Comentarios: " + iCommentsFunctions[0] + " Incorrectas: " + iCommentsFunctions[1]);
                    iCommentsFunctions[0] += iArrCom[0];
                    iCommentsFunctions[1] += iArrCom[1];
                    System.out.println("Funcion: " + comComments.getFunctionName() 
                            + " Comentarios: " + iArrCom[0] + " Incorrectas: " + iArrCom[1]);
                }
                // if it's a function
                String sLine = fArr.getLine(i);
                if(vVars.isAFunction(sLine)) {
                    int iFun[] = vVars.checkFunction(sLine);
                    iFunctionHeaders[0] += iFun[0];
                    iFunctionHeaders[1] += iFun[1];
                    System.out.println("Declaracion de funcion Correctas: " + iFun[0] + " Incorrectas: " + iFun[1]);
                    // viene funcion
                    int iArr[] = vVars.checkDeclarationsOfFunctions(sLine);
                    iVars[0] += iArr[0];
                    iVars[1] += iArr[1];
                    System.out.println("Variables en funciones Correctas: " + iArr[0] + " Incorrectas: " + iArr[1]);
                    i++;
                }
                //if is a variables declaration
                if(vVars.isADeclaration(fArr.getLine(i))) {
                    int iArr[] = vVars.checkDeclarations(fArr.getLine(i));
                    iVars[0] += iArr[0];
                    iVars[1] += iArr[1];
                    System.out.println("Variables Correctas: " + iArr[0] + " Incorrectas: " + iArr[1]);
                }
            }
            iLibraries = libLibraries.getGrades();
            System.out.println(libLibraries.getComments());
            return true;
        } 
        else {
            return false;
        }
    }
    
}
