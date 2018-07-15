package core;

import java.util.ArrayList;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.Search;

/**
 * Abstract class template for CLP problems.
 * 
 * @author Tomasz Strzoda
 */
public class Base {
    
    // Store for CLP variables
    protected Store store;
    // Label for searching solution
    protected Search label;
    // ArrayList of CLP variables
    protected ArrayList<IntVar> vars;
    
    /**
     * Method for modeling CLP problem.
     */
    public void model() {   
    }
    
    /**
     * Method for searching solution CLP problem.
     * @return solution of CLP problem.
     */
    public String search() {    
       return "";
    }
    
}
