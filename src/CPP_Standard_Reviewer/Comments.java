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
    
    private String sComments, sFunctionName;
    private int iInitialComments[];
    private int iComments[];
    private int iParameters[];
    private ArrayList<String> listVars;
    //&i
    Comments() {
        sFunctionName = "";
        sComments = "Comentarios: ";
        iInitialComments = new int [2];
        iComments = new int [2];
        iParameters = new int [2];
        listVars = new ArrayList<String>();
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
        sLine.replace(":", "");
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
        sLine.replace(":", "");
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
        System.out.println(sLine);
        String sArr [] = split(sLine, ' ');
        sArr[1] = split(sArr[1].toLowerCase(),'(')[0];
        System.out.println(sArr[1]);
        return (sFunctionName.equals(sArr[1]));
    }
    //&i
    public int[] checkDeclarationsOfFunctions(String sLine) {
        ArrayList<String> sAux = new ArrayList<String>();
        sAux.addAll(listVars);
        int iConts[] = new int [4], iCantVars = 0, iRes;
        sLine = sLine.substring(sLine.indexOf("(")+1);
        String sArr [] = split(sLine, ',');
        sArr[sArr.length - 1] = sArr[sArr.length - 1].replace(")", "");
        sArr[sArr.length - 1] = sArr[sArr.length - 1].replace("{", "");
        for(int i = 1; i < sArr.length; i += 2) {
            sArr[i] = sArr[i].trim();
            for (int j = 0; j < sAux.size(); j++) {
                if(sArr[i].equals(sAux.get(j))) {
                    iConts[0]++;
                    sAux.remove(j);
                    j = sAux.size();
                }
            }
            iCantVars++;
        }
        iRes = iCantVars - iConts[0];
        if (iCantVars == listVars.size()) {
             iConts[1] += 1;
             sComments += "Parametros no concuerda tamaño, ";
        }
       
        if(iRes != 0) {
            iConts[1] = iRes;
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
}
