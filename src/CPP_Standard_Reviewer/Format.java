/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

/**
 *
 * @author Faintinger
 */
public class Format {
    int iArrSpaces[];
    int iArrIdentation[];
    int iInstructions[];
    
    boolean bStatement;
    boolean bFunction;
    
    String arrOp[] = {"+", "-", "*", "/", "&&", "||", ">>", "<<", "<=", "=<", "==", "!=", ">", "<", "=", "%"};
    String darrOp[] = {"&&", "||", ">>", "<<", "<=", "=<", "==", "!="};
    Format() {
        iArrSpaces = new int[2];
        iArrIdentation = new int[2];
        iInstructions = new int[2];
        
        bStatement = bFunction = false;
    }
    
    private int existAnOperation(String sLine, String[] arrOpS) {
        for(int i = 0; i < arrOpS.length; i++)
            if(sLine.indexOf(arrOpS[i]) > -1)
                return sLine.indexOf(arrOpS[i]);
        return -1;
    }
    
    public void checkSpaces(String sLine) {
        sLine = sLine.trim();
        int iPos = existAnOperation(sLine, arrOp), iComment, desp = 1;
        iComment = sLine.indexOf("//");
        while((iComment > 0 || iComment == -1) && iPos > -1) {
            if(iPos > -1) {
                //System.out.print(sLine + " " + sLine.charAt(iPos - 1)+ "/" + sLine.charAt(iPos + 1));
                desp = (iPos == existAnOperation(sLine, darrOp)? 2 : 1 );
                if(sLine.charAt(iPos - 1) == ' ' && sLine.charAt(iPos + desp) == ' ') {
                    iArrSpaces[0]++;
                }
                else {
                    iArrSpaces[1]++;
                }
                //System.out.println(Integer.toString(iArrSpaces[0]) + " " + Integer.toString(iArrSpaces[1]));
                desp = (iPos == existAnOperation(sLine, darrOp)? 2 : 1 );
                sLine = sLine.substring(iPos + desp);
                iPos = existAnOperation(sLine,arrOp);
            }
        }
        
    }
    
    public void checkInstructionsPerLine(String sLine) {
        sLine = sLine.trim();
        //System.out.print(sLine + " ");
        String sEnd = "";
        if(sLine.equals("{") || sLine.equals("}")) {
            iInstructions[0]++;
        }
        else {
            if(sLine.contains("for") || sLine.contains("while (") || sLine.contains("while(") ||
                    sLine.contains("if") || sLine.contains("switch") || 
                    sLine.equals("do") || bFunction) 
                sEnd = ")";
            else if(sLine.contains("case"))
                sEnd = ":";
            else
                sEnd = ";";
            int iCom = sLine.indexOf("//");
            if(iCom > -1) {
                sLine = sLine.substring(0,iCom).trim();
            }
            if(sLine.length() - 1 == sLine.indexOf(sEnd)) {   
                iInstructions[0]++;
            }
            else {
                iInstructions[1]++;
            } 
        }
        //System.out.println(Integer.toString(sLine.length() - 1) + " " + Integer.toString(sLine.indexOf(sEnd)) + " " + sEnd);
    }
    
    
    
    public void setbFunction(boolean bFun) {
        bFunction = bFun;
    }
    
    public int[] getEvalSpaces() {
        return iArrSpaces;
    }
    
    public int[] getEvalIdentation() {
        return iArrIdentation;
    }
    
    public int[] getEvalInstructions() {
        return iInstructions;
    }
}
