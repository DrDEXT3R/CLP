package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Constraint;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.SelectChoicePoint;

/**
 * The class for solving map coloring problem.
 * 
 * @author Tomasz Strzoda
 */
public class MapColoring extends Base {

    /** The array for storing the domain of each region. */
    IntVar[] regions;

    /** The array for storing neighboring regions. */
    IntVar[] group;

    /** The variable for storing number of regions. */
    int noOfRegions;

    /** The enum of all possible colors related to the solution. */
    public enum Colors { blue, green, red, yellow }

    
    @Override
    public void model() {
        store = new Store(); 
        
        // Define finite domain variables.
        regions = new IntVar[noOfRegions];
       
        for (int i=0; i<noOfRegions; i++) {
            regions[i] = new IntVar(store, "regions"+i, 1, noOfRegions);
        }
    }
    
    @Override
    public String search() {
        String output = "";
        long T1, T2;
	    T1 = System.nanoTime();
        
        SelectChoicePoint select = new InputOrderSelect (store, regions, new IndomainMin());
        label = new DepthFirstSearch<>();   
        output += label.labeling(store, select);
        T2 = System.nanoTime();

        time_ns = T2 - T1;

        return output;
    }

    /**
     * This method provides different colors for neighboring regions.
     * @param neighboringRegions set of neighboring regions.
     */
    public void sendGroup(Set<Character> neighboringRegions) {
        group = new IntVar[neighboringRegions.size()];
        int regionInAscii;

        List<Character> lisfOfRegions = new ArrayList<>(neighboringRegions);
        for (int i = 0; i < neighboringRegions.size(); i++) {
            regionInAscii  = (int)lisfOfRegions.get(i) - 65;
            group[i] = regions[regionInAscii];
        }
        Constraint ctr = new Alldifferent(group);
        store.impose(ctr);  
    }

    /**
     * This method sets the number of regions specified by the user.
     * @param i number of regions.
     */
    public void setNoOfRegions(int i) {
        noOfRegions = i;
    }
    
    @Override
    public String[][] getSolutionAsArray() {
        String[][] solution = new String[4][3];
        for (int i = 0; i < 4; i++) 
            solution[i][0] = "";

        for (int i = 0; i < noOfRegions; i++) {
            int j = this.getColorIndex(i);
            solution[j-1][0] += Character.toString((char)(65+i));
            solution[j-1][0] += " ";
            solution[j-1][1] = Integer.toString( this.getColorIndex(i) ); 
            solution[j-1][2] = this.getColorName(this.getColorIndex(i)-1).name();   
        }

        return solution;
    }

    /**
     * This method obtains a unique color index number.
     * @param i color number.
     * @return  color index.
     */
    public int getColorIndex(int i){
        return regions[i].value();
    }

    /**
     * This method obtains a color from enum of colors.
     * @param i color number.
     * @return  color.
     */
    public Colors getColorName(int i){
        return Colors.values()[i];
    }

}
