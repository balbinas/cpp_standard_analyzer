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
//&p-Format
public class Format {
    int iArrSpaces[];
    int iArrIdentation[];
    int iInstructions[];
    int iIdent;
    String sComments;
    
    boolean bStatement;
    boolean bFunction;
    
    String arrOp[] = {"+=", "-=", "*=", "/=", "+", "-", "*", "/", "&&", "||", ">>", "<<", "<=", "=<", "==", "!=", ">", "<", "=", "%"};
    String darrOp[] = {"+=", "-=", "*=", "/=", "&&", "||", ">>", "<<", "<=", "=<", "==", "!="};
    Format() {
        iArrSpaces = new int[2];
        iArrIdentation = new int[2];
        iInstructions = new int[2];
        sComments = "Format: ";
        iIdent = 0;
        bStatement = bFunction = false;
    }
    //&i
    private int existAnOperation(String sLine, String[] arrOpS) {
        for(int i = 0; i < arrOpS.length; i++)
            if(sLine.indexOf(arrOpS[i]) > -1)
                return sLine.indexOf(arrOpS[i]);
        return -1;
    }
    //&i
    public void checkSpaces(String sLine) {
        sLine = sLine.trim();
        int iPos = existAnOperation(sLine, arrOp), iComment, desp = 1, despN = 0;
        iComment = sLine.indexOf("//");
        while((iComment > 0 || iComment == -1) && iPos > -1) {
            if(iPos > -1) {
                //System.out.print(sLine + " " + sLine.charAt(iPos - 1)+ "/" + sLine.charAt(iPos + 1));
                desp = (iPos == existAnOperation(sLine, darrOp)? 2 : 1 );
                despN = (iPos == 0)? 0 : -1;
                if(sLine.charAt(iPos + despN) == ' ' && sLine.charAt(iPos + desp) == ' ') {
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
    //&i
    public void checkInstructionsPerLine(String sLine) {
        sLine = sLine.trim();
        //System.out.print(sLine + " ");
        String sEnd = "";
        if(sLine.equals("{") || sLine.equals("}")) {
            iInstructions[0]++;
        }
        else {
            if(sLine.contains("for") || sLine.contains("while (") || sLine.contains("while(") ||
                    sLine.contains("if (") || sLine.contains("if(") || sLine.contains("switch") ||
                    sLine.equals("do") || bFunction) 
                sEnd = ")";
            else if(sLine.contains("case"))
                sEnd = ":";
            else
                sEnd = ";";
            int iCom = sLine.indexOf("//");
            if(sLine.contains("else")) {
                if(sLine.equals("else"))
                    iInstructions[0]++;
                else
                    iInstructions[1]++;
            }
            else {
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
        }
        //System.out.println(Integer.toString(sLine.length() - 1) + " " + Integer.toString(sLine.indexOf(sEnd)) + " " + sEnd);
    }
    
    public void checkIndentation(String sLine) {
        if(sLine.trim().indexOf("//") != 0) {
            if(sLine.contains("for") || sLine.contains("while (") || sLine.contains("while(") ||
                    sLine.contains("if (") || sLine.contains("if(") || sLine.contains("switch") || 
                    sLine.contains("else") || sLine.equals("do") || bFunction) {
                iIdent++;
            } else {
                if(sLine.contains("}")) {
                    iIdent--;
                } else {
                    int iTabs = 0, iSpaces = 0, i = 0;
                    while(sLine.charAt(i) == ' ' || sLine.charAt(i) == '\t') {
                        if(sLine.charAt(i) == ' ')
                            iSpaces++;
                        else
                            iTabs++;
                        i++;
                    }
                    iSpaces = (sLine.charAt(i) == '{' || sLine.charAt(i) == '}')? (iSpaces / 4) - 1 : iSpaces / 4;
                    if(iIdent == (iSpaces + iTabs))
                        iArrIdentation[0]++;
                    else
                        iArrIdentation[1]++;
                    //System.out.println(sLine + " " + Integer.toString(iArrIdentation[0]) + " " + Integer.toString(iArrIdentation[1]) + " " + Integer.toString(iIdent) + " " + Integer.toString(iSpaces + iTabs));
                }
            }
        }  
    }
    
    //&i
    public void setbFunction(boolean bFun) {
        bFunction = bFun;
    }
    //&i
    public int[] getEvalSpaces() {
        return iArrSpaces;
    }
    //&i
    public int[] getEvalIdentation() {
        return iArrIdentation;
    }
    //&i
    public int[] getEvalInstructions() {
        return iInstructions;
    }
    //&i
    public String getComments() {
        if(iArrSpaces[1] > 0)
            sComments += "operations without spaces between them, ";
        if(iArrIdentation[1] > 0)
            sComments += "identation of the statements do not match, ";
        if(iInstructions[1] > 0)
            sComments += "bad ending of instructions or more than one instruction per line, ";
        return sComments;
    }
}
