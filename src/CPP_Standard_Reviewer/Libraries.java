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
//&p-Libraries
public class Libraries {
    int iGrades[], iStdCount;
    String sComments;
    //&i
    Libraries() {
        iGrades = new int [2];
        sComments = "";
        iStdCount = 0;
    }
    //&i
    public boolean isALibraryStatement(String sLine) {
        return(sLine.indexOf("include") > -1);
    }
    //&i
    public int[] checkLibrary(String sLine) {
        int iArr [] = new int[2];
        sLine = sLine.trim();
        if(sLine.matches("#include[\\s]?<[a-zA-Z0-9]+([-_][a-zA-Z0-9])*>")) {
            iArr[0]++;
            iGrades[0]++;
        }
        else {
            iArr[1]++;
            iGrades[1]++;
            sComments += "Wrong include declaration: " + sLine.trim() + ", ";
        }
        return iArr;
    }
    //&i
    public boolean isUsing(String sLine) {
        return (sLine.contains("using namespace"));
    }
    public void checkSTD(String sLine) {
        sLine = sLine.trim();
        if(sLine.equals("using namespace std;")) {
            if(iStdCount == 0){
                iGrades[0]++;
                iStdCount++;
            }
            else if(iStdCount == 1) {
                iGrades[1]++;
                sComments += "File with more than one using std, ";
                iStdCount++;
            }
        } else {
            iGrades[1]++;
            sComments += "Bad using std declaration, ";
        }
    }
    //&i
    public int[] getGrades() {
        return iGrades;
    }
    //&i
    public String getComments() {
        return sComments;
    }
}
