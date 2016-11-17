/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.util.ArrayList;

/**
 *
 * @author Faintinger
 */
//&p-Comments
public class Comments {
    
    private String sComments, sFunctionName, sFileName;
    private int iInitialComments[];
    private int iComments[];
    private int iParameters[];
    private ArrayList<String> listVars;
    //&i
    Comments() {
        sFunctionName = "";
        sFileName = "";
        sComments = "Comentarios: ";
        iInitialComments = new int [2];
        iComments = new int [2];
        iParameters = new int [2];
        listVars = new ArrayList<String>();
    }
    
    //&i
    public void saveFileName(String sLine) {
        sLine = sLine.trim();
        sLine = sLine.toLowerCase();
        sFileName = sLine;
    }
    //&i
    public int[] checkFileName(String sName) {
        int iGrade[] = new int [2];
        String sAux[] = split(sName, '.');
        int iPos;
        System.out.println(sFileName + " " + sName);
        if (sFileName.indexOf(".") > -1) {
            iPos = (split(sFileName, '.')[0].equals(sAux[0]))? 0 : 1;
            iGrade[iPos]++;
        } else {
            if(sFileName.equals(sAux[0])) {
                iGrade[0]++;
                iGrade[1]++;
                sComments += "Name match but do not have the .cpp extension, ";
            } else {
                iGrade[1]++;
                sComments += "Initial Comment do not have the correct file name, ";
            }
        }
        return iGrade;
    }
    //&i
    public boolean isAComment(String sLine) {
        return (sLine.indexOf("/*") > -1);
    }
    //&i
    public boolean isAFunctionComment(String sLine) {
        sLine = sLine.trim();
        return (sLine.equals("/*"));
    }
    //&i
    public boolean endOfComment(String sLine) {
        sLine = sLine.trim();
        return (sLine.equals("*/"));
    }
    //&i
    public boolean startParameters(String sLine) {
        sLine = sLine.trim();
        sLine = sLine.toLowerCase();
        sLine = sLine.replace(":", "");
        return(sLine.equals("parameters"));
    }
    //&i
    public void saveParameters(String sLine) {
        sLine = sLine.trim();
        String sArr[] = split(sLine, ' ');
        listVars.add(sArr[0]);
    }
    //&i
    public boolean startReturn(String sLine) {
        sLine = sLine.trim();
        sLine = sLine.toLowerCase();
        sLine = sLine.replace(":", "");
        return(sLine.equals("returns"));
    }
    //&i
    public ArrayList<String> getParameters() {
        return listVars;
    }
    //&i
    public void addParameters(int iCorrect, int iWrong) {
        iParameters[0] += iCorrect;
        iParameters[1] += iWrong;
    }
    //&i
    public void setFunctionName(String sLine) {
        sFunctionName = sLine.trim().toLowerCase();
        sFunctionName = (sFunctionName.indexOf(" ") > 0)? sFunctionName.substring(0, sFunctionName.indexOf(" ")) : sFunctionName;
    }
    //&i
    public void cleanParameters() {
        listVars.clear();
    }
    //&i
    public int[] getParametersGrade() {
        return iParameters;
    }
    //&i
    public String getFunctionName() {
        return sFunctionName;
    }
    //&i
    public boolean checkFunctionName(String sLine) {
        String sArr [] = split(sLine, ' ');
        sArr[1] = split(sArr[1].toLowerCase(),'(')[0];
        return (sFunctionName.equals(sArr[1]));
    }
    //&i
    public int[] checkDeclarationsOfFunctions(String sLine) {
        ArrayList<String> sAux = new ArrayList<String>();
        if(listVars.size() > 0)
            sAux.addAll(listVars);
        int iConts[] = new int [3], iCantVars = 0, iRes = 0;
        sLine = sLine.substring(sLine.indexOf("(")+1);
        String sArr [] = split(sLine, ' ');
        sArr = multipleSplit(sArr, ',');
        sArr[sArr.length - 1] = sArr[sArr.length - 1].replace(")", "");
        sArr[sArr.length - 1] = sArr[sArr.length - 1].replace("{", "");
        //System.out.println("Tamaño parametros " + sArr.length + " ");
        //for(int i = 0; i < sArr.length; i++)
        //     System.out.print(sArr[i] + " ");
        System.out.println("Lista de parametros: " + listVars.toString() + " " + listVars.size());
        for(int i = 1; i < sArr.length; i += 2) {
            sArr[i] = sArr[i].trim();
            //System.out.println(sArr[i]);
            for (int j = 0; j < sAux.size(); j++) {
                if(sArr[i].equals(sAux.get(j))) {
                    iConts[0]++;
                    sAux.remove(j);
                    j = sAux.size() + 1;
                    
                }
            }
            iCantVars++;
        }
        iRes = iCantVars - iConts[0];
        if (iCantVars != listVars.size()) {
             iConts[1] += 1;
             sComments += sFunctionName + " parameters size do not match, ";
        }
        if(iRes != 0) {
            iConts[1] += iRes;
        }
        else {
            iConts[0]++;
        }
        iConts[2] = iConts[0] + iConts[1];
        return iConts;
    }
    //&i
    private String [] split(String sLine, char sChar) {
        ArrayList<String> sALAux = new ArrayList<String>();
        int iStart = 0;
        for(int i = 0; i < sLine.length(); i++) {
            if(sLine.charAt(i) == sChar) {
                sALAux.add(sLine.substring(iStart, i));
                iStart = i + 1;
            }
        }
        sALAux.add(sLine.substring(iStart));
        String [] sArr = new String[sALAux.size()];
        sArr = sALAux.toArray(sArr);
        return sArr;
    }
     //&i
    private String [] multipleSplit(String sArr[], char sChar) {
        ArrayList<String> sList = new ArrayList<String>();
        for(int i = 0; i < sArr.length; i++) {
            String sAux [] = split(sArr[i], sChar);
            for(int j = 0; j < sAux.length; j++) {
                if(!sAux[j].trim().equals(""))
                    sList.add(sAux[j]);
            }
        }
        String [] sRes = new String[sList.size()];
        sRes = sList.toArray(sArr);
        //System.out.println(sList.toString());
        return sRes;
    }
}
