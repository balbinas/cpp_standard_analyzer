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
public class Variables {
    ArrayList<String> sArrVars = new ArrayList<>();
    ArrayList<String> sArrTypes = new ArrayList<>();
    String [] sVariables= {"bool", "int", "float", "string"};
    public Variables() {
        
    }
    
    public Boolean isADeclarations(){
        return true;
    }
    
    public int[] checkDeclarations() {
        return new int [1];
    }
    
    public int[] checkDeclarationsOfFunctions(String [] sArrVars) {
        return new int [1];
    }
}
