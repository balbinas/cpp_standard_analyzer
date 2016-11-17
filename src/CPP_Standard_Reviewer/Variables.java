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
//&p-Variables
public class Variables {
    ArrayList<String> sArrVars = new ArrayList<>();
    ArrayList<String> sArrTypes = new ArrayList<>();
    String [] sVariables ={"bool", "int", "float", "string", "char", "double"};
    String sType;
    //&i
    public Variables() {
        sType = "";
    }
    //&i
    public Boolean isADeclaration(String sLine){
        for(int i = 0; i < sVariables.length; i++) {
            int iPos = sLine.indexOf(sVariables[i]);
            if(iPos > -1) {
                if(sLine.charAt(iPos + sVariables[i].length()) == ' ') {
                    System.out.print(iPos + " " + sVariables[i] + " ");
                    String sArr [] = split(sLine.substring(iPos + 
                            sVariables[i].length() + 1), ',');
                    sArr = deleteAssign(sArr, "=");
                    sArr = deleteChar(sArr, " ");
                    sArr = deleteChar(sArr, ",");
                    sArr = deleteChar(sArr, ";"); 
                    sType = sVariables[i];
                    System.out.println(sArr[0].matches("[a-zA-Z][a-zA-Z0-9]*([-_]?[a-zA-Z0-9]+)*"));
                    return (sArr[0].matches("[a-zA-Z][a-zA-Z0-9]*([-_]?[a-zA-Z0-9]+)*"));
                }
            }
        }
        String sAux [] = split(sLine.trim(),',');
        sAux = deleteAssign(sAux, "=");
        sAux = deleteChar(sAux, " ");
        sAux = deleteChar(sAux, ",");
        sAux = deleteChar(sAux, ";");
        if (sAux.length > 1) {
            if(sAux[0].matches("[A-Z][a-zA-Z]+")) {
                sType = sAux[0];
                System.out.println(sAux[1].matches("[a-zA-Z][a-zA-Z0-9]*([-_]?[a-zA-Z0-9]+)*"));
                return (sAux[1].matches("[a-zA-Z][a-zA-Z0-9]*([-_]?[a-zA-Z0-9]+)*"));
            }
        }
        return false;
    }
    //&i
    public int[] checkDeclarations(String sLine) {
        String sAux = sLine.substring(sLine.indexOf(sType) + sType.length());
        sAux = sAux.trim();
        int iConts [] = new int [3];
        String sArr [] = split(sAux, ',');
        sArr = deleteAssign(sArr, "=");
        sArr = deleteChar(sArr, " ");
        sArr = deleteChar(sArr, ",");
        sArr = deleteChar(sArr, ";");
        for(int i = 0; i < sArr.length; i++) {
            if('A' <= sType.charAt(0) && sType.charAt(0) <= 'Z') {
                if(sArr[i].substring(0,2).equals(sType.substring(0,2))) {
                    iConts[0]++;
                }
                else {
                    iConts[1]++;
                }
            } 
            else {
                if(sArr[i].charAt(0) == sType.charAt(0)) {
                    iConts[0]++;
                }
                else {
                    iConts[1]++;
                }
            }
            iConts[2] = iConts[0] + iConts[1];
        }
        return iConts;
    }
    //&i
    public int[] checkFunction(String sLine) {
        int iCont[] = new int [2];
        String sAux[] = split(sLine, ' ');
        for(int i = 0; i < sVariables.length; i++) {
            if(sAux[0].indexOf(sVariables[i]) > -1 || sAux[0].equals("void") || sAux[0].matches("[A-Z][a-z]+")) {
                iCont[0]++;
                i = sVariables.length;
            }
        }
        iCont[1] = (iCont[0] > 0)? 0 : 1;
        System.out.println(sAux[0] + " " + sAux[1]);
        if(split(sAux[1],'(')[0].matches("[a-z][a-zA-Z0-9]+")) {
            iCont[0]++;
        } 
        else {
            iCont[1]++;
        } 
        System.out.println(iCont[0] + " " + iCont[1]);
        return iCont;
    }
    //&i
    public int[] checkDeclarationsOfFunctions(String sLine) {
        int iConts[] = new int [3];
        sLine = sLine.substring(sLine.indexOf("(")+1);
        String sArr [] = split(sLine, ' ');
        sArr = multipleSplit(sArr, ',');
        sArr = deleteChar(sArr, ")");
        sArr = deleteChar(sArr, "{");
        //System.out.println("Tamaño parametros " + sArr.length + " ");
        //for(int i = 0; i < sArr.length; i++)
        //     System.out.print(sArr[i] + " ");
        //System.out.println();
        for(int i = 0; i < sArr.length; i += 2) {
            sArr[i] = sArr[i].trim();   //System.out.println(sArr[i + 1]);
            for(int j = 0; j < sVariables.length; j++) {
                if(sArr[i].equals(sVariables[j])) {
                    if(sArr[i + 1].charAt(0) == sVariables[j].charAt(0)) {
                        iConts[0]++;
                    }
                    else {
                        iConts[1]++;
                    }
                    j = sVariables.length;
                }
            }               
            iConts[2] = iConts[0] + iConts[1];
        }
        return iConts;
    }
    //&i
    public boolean isAFunction(String sLine) {
        String sArr[] = split(sLine, '(');
        String sAux2[] = split(sArr[0],' ');
        if(isAType(sAux2[0]) && sAux2.length > 1 && sArr.length > 1) {
            return true;
        }
        return false;
    }
    //&i
    private String [] deleteChar(String sAux[], String sCad) {
        String sRet[] = new String [sAux.length];
        for (int i = 0; i < sAux.length; i++) {
            //System.out.println(sAux[i]);
            sRet[i] = (sAux[i].indexOf(sCad) > -1)? sAux[i].replace(sCad, "") : sAux[i];
        }
        return sRet;
    }
    //&i
    private String [] deleteAssign(String sAux[], String sCad) {
        String sRet[] = new String [sAux.length];
        for (int i = 0; i < sAux.length; i++) {
            //System.out.println(sAux[i]);
            sRet[i] = (sAux[i].indexOf(sCad) > -1)? sAux[i].substring(0,sAux[i].indexOf(sCad)) : sAux[i];
        }
        return sRet;
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
    //&i
    private boolean isAType(String sLine) {
         for(int i = 0; i < sVariables.length; i++) {
            if(sLine.indexOf(sVariables[i]) > -1 || sLine.equals("void") || sLine.matches("[A-Z][a-z]+")) {
                return true;
            }
        }
        return false;
    }
}
