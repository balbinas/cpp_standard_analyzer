/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CPP_Standard_Reviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Faintinger
 */
public class Files {
    ArrayList<String> sArrLines = new ArrayList<String>();
    String sName;
    
    Files() {
        sName = "";
    }
    
    public Boolean openFile(String sName) {
        File fFile = new File(sName);
        String line;
        try (
            InputStream fis = new FileInputStream("the_file_name");
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                sArrLines.add(line);
            }
        } catch(Exception ex) {
            return false;
        }
        return true;
    }
    
    public void removeBlankLines() {
        ArrayList<String> sArrAux = new ArrayList<String>();
            for (int i = 0; i < sArrLines.size(); i++) { 
                for (int j = 0; j < sArrLines.get(i).length(); j++) { 
                    if ((sArrLines.get(i).charAt(j) != ' ' 
                        && sArrLines.get(i).charAt(j) != '\t' 
                        && sArrLines.get(i).charAt(j) != '\n')) { 
                        sArrAux.add(sArrLines.get(i)); 
                        break;
                    }
                }
            }
            sArrLines = sArrAux;
    }
    
    public String getLine(int iPos) {
        return sArrLines.get(iPos);
    }
    
    public int Length() {
        return sArrLines.size();
    }
    
    public String getName(){
        return sName;
    }
    

}
