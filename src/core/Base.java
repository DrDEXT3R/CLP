package core;

import java.util.ArrayList;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.Search;

/**
 * The abstract class template for CLP problems.
 * 
 * @author Tomasz Strzoda
 */
public abstract class Base {

    /** The store for CLP variables. */
    protected Store store;

    /** The label for searching solution.*/
    protected Search label;

    /** The ArrayList of CLP variables. */
    protected ArrayList<IntVar> vars;

    /** The variable for storing the solution search time. */
    protected long time_ns;


    /**
     * The class constructor responsible for initializing the ArrayList of CLP variables.
     */
    public Base() {
        vars = new ArrayList<>();
    }
    
    /**
     * The method for modeling a CLP problem.
     */
    public abstract void model();
    
    /**
     * The method for searching solution of a CLP problem.
     * @return  solution of CLP problem.
     */
    public abstract String search();

    /**
     * This method obtains the solution of a CLP problem.
     * @return  solution in the form of an array.
     */
    public abstract String[][] getSolutionAsArray();

    /**
     * This method obtains the solution search time.
     * @return  solution search time in seconds.
     */
    public double getTime() {
        return (time_ns/1000000000.0);
    }
    
}
