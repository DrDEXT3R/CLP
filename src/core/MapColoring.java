package core;

import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Constraint;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.SelectChoicePoint;

/**
 * Class for solving map coloring problem.
 * 
 * @author Tomasz Strzoda
 */
public class MapColoring extends Base {
   
    IntVar[] regions;
    
    @Override
    public void model() {
        store = new Store(); 

        int NUMBER_OF_REGIONS = 6;
        
        // Define finite domain variables.
        regions = new IntVar[NUMBER_OF_REGIONS];
       
        for (int i=0; i<NUMBER_OF_REGIONS; i++) {
            regions[i] = new IntVar(store, "regions"+i, 1, NUMBER_OF_REGIONS);
        }
        
        // Regions that can't have the same color.
        IntVar[] ctr1 = {regions[2], regions[3], regions[4], regions[5]};
        Constraint ctr = new Alldifferent(ctr1);
        store.impose(ctr);
        
        IntVar[] ctr2 = {regions[0], regions[1], regions[2], regions[3]};
        ctr = new Alldifferent(ctr2);
        store.impose(ctr);
        
        IntVar[] ctr3 = {regions[0], regions[3], regions[5]};
        ctr = new Alldifferent(ctr3);
        store.impose(ctr);
    }
    
    @Override
    public String search() {
        String output = new String();
        long T1, T2;
	T1 = System.nanoTime();
        
        SelectChoicePoint select = new InputOrderSelect (store, regions, new IndomainMin());
        label = new DepthFirstSearch<>();   
        output += label.labeling(store, select);
        T2 = System.nanoTime();

        output += "\n\nTime: " + Long.toString(T2-T1) + "ns";
        return output;
    }
    
}
