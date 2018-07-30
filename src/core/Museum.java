package core;

import org.jacop.constraints.XplusYlteqZ;
import org.jacop.constraints.cumulative.CumulativeUnary;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;

/**
 * Class for creating museum time schedule.
 * 
 * @author Tomasz Strzoda
 */
public class Museum extends Base {
    
    final int SIZE = 4;
    final int museumStart = 10*60;  
     
    @Override
    public void model() {
        store = new Store();        
        //vars = new ArrayList<IntVar>();

        IntVar[] americans = new IntVar[SIZE];
        IntVar[] belgians = new IntVar[SIZE];
        IntVar[] czech = new IntVar[SIZE];
        IntVar[] danish = new IntVar[SIZE];

        IntVar[] drawings = new IntVar[SIZE];
        drawings[0] = new IntVar(store, "durationAmericans", 60, 60);
        drawings[1] = new IntVar(store, "durationBelgians", 20, 20);
        drawings[2] = new IntVar(store, "durationCzech", 30, 30);
        drawings[3] = new IntVar(store, "durationDanish", 45, 45);
        
        IntVar[] paintings = new IntVar[SIZE];
        paintings[0] = new IntVar(store, "durationAmericans", 45, 45);
        paintings[1] = new IntVar(store, "durationBelgians", 15, 15);
        paintings[2] = new IntVar(store, "durationCzech", 60, 60);
        paintings[3] = new IntVar(store, "durationDanish", 10, 10);
        
        IntVar[] sculptures = new IntVar[SIZE];
        sculptures[0] = new IntVar(store, "durationAmericans", 30, 30);
        sculptures[1] = new IntVar(store, "durationBelgians", 60, 60);
        sculptures[2] = new IntVar(store, "durationCzech", 20, 20);
        sculptures[3] = new IntVar(store, "durationDanish", 60, 60);
        
        IntVar[] photographs = new IntVar[SIZE];
        photographs[0] = new IntVar(store, "durationAmericans", 15, 15);
        photographs[1] = new IntVar(store, "durationBelgians", 60, 60);
        photographs[2] = new IntVar(store, "durationCzech", 45, 45);
        photographs[3] = new IntVar(store, "durationDanish", 30, 30);

        IntVar[][] durations = new IntVar[SIZE][];
        durations[0] = drawings;
        durations[1] = paintings;
        durations[2] = sculptures;
        durations[3] = photographs;

        // The maximum duration of nationality in the museum.
        for (int i = 0; i < SIZE; i++) {
            americans[i] = new IntVar(store, "Americans[" + i + "]", 0, 300);
            belgians[i] = new IntVar(store, "Belgians[" + i + "]", 60, 300);
            czech[i] = new IntVar(store, "Czech[" + i + "]", 90, 300);
            danish[i] = new IntVar(store, "Danish[" + i + "]", 105, 300);
            vars.add(americans[i]);
            vars.add(belgians[i]);
            vars.add(czech[i]);
            vars.add(danish[i]);
        }

        IntVar one = new IntVar(store, "one", 1, 1);
        IntVar[] four = new IntVar[SIZE];
        IntVar[] fourOnes = {one, one, one, one};

        four[0] = americans[0];
        four[1] = belgians[0];
        four[2] = czech[0];
        four[3] = danish[0];
       // Exhibition can be visited by only one group at the same time.
       store.impose(new CumulativeUnary(four, drawings, fourOnes, one));

        four[0] = americans[1];
        four[1] = belgians[1];
        four[2] = czech[1];
        four[3] = danish[1];
        // Exhibition can be visited by only one group at the same time.
        store.impose(new CumulativeUnary(four, paintings, fourOnes, one));

        four[0] = americans[2];
        four[1] = belgians[2];
        four[2] = czech[2];
        four[3] = danish[2];
        // Exhibition can be visited by only one group at the same time.
        store.impose(new CumulativeUnary(four, sculptures, fourOnes, one));

        four[0] = americans[3];
        four[1] = belgians[3];
        four[2] = czech[3];
        four[3] = danish[3];
        // Exhibition can be visited by only one group at the same time.
        store.impose(new CumulativeUnary(four, photographs, fourOnes, one));

        // Setting opening time.
        IntVar openingTime = new IntVar(store, "openingTime", 0, 300);

        int[] americansPrecedence = {1, 2, 3, 4};
        for (int i = 0; i < 3; i++)
            store.impose(new XplusYlteqZ(americans[americansPrecedence[i] - 1], 
                            durations[americansPrecedence[i] - 1][0], 
                            americans[americansPrecedence[i + 1] - 1]));

        store.impose(new XplusYlteqZ(americans[3], photographs[0], openingTime));

        int[] belgiansPrecedence = {2, 3, 1, 4};
        for (int i = 0; i < 3; i++)
            store.impose(new XplusYlteqZ(belgians[belgiansPrecedence[i] - 1], 
                            durations[belgiansPrecedence[i] - 1][1],
                            belgians[belgiansPrecedence[i + 1] - 1]));

        store.impose(new XplusYlteqZ(belgians[3], photographs[1], openingTime));

        int[] czechPrecedence = {3, 2, 1, 4};
        for (int i = 0; i < 3; i++)
            store.impose(new XplusYlteqZ(czech[czechPrecedence[i] - 1], 
                                        durations[czechPrecedence[i] - 1][2],
                                        czech[czechPrecedence[i + 1] - 1]));

        store.impose(new XplusYlteqZ(czech[3], photographs[2], openingTime));

        int[] danishPrecedence = {4, 1, 2, 3};
        for (int i = 0; i < 3; i++)
            store.impose(new XplusYlteqZ(danish[danishPrecedence[i] - 1], 
                            durations[danishPrecedence[i] - 1][3], 
                            danish[danishPrecedence[i + 1] - 1]));

        store.impose(new XplusYlteqZ(danish[2], sculptures[3], openingTime));
       
        vars.add(openingTime);
    }
    
    @Override
    public String search() {
        String output = new String();
        long T1, T2;
	T1 = System.nanoTime();
        
        SelectChoicePoint<IntVar> select = new SimpleSelect<>(vars.toArray(new IntVar[1]), null, new IndomainMin<>());
        label = new DepthFirstSearch<>();   
        output += label.labeling(store, select);
        T2 = System.nanoTime();
        
        time_ns = T2 - T1;
        
        return output;
    }
    
    @Override
    public String[][] getSolutionAsArray() {
        String[][] solution = new String[SIZE][SIZE+1];
        
        // Setting first column - nationality names.
        for (int i = 0; i < SIZE; i++) {
            String s = vars.get(i).id;
            solution[i][0] = s.substring(0, s.indexOf("["));
        }
        // Setting main content - time.
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) 
                solution[j][i+1] = min2time(vars.get(i*SIZE+j).value()+museumStart);

        return solution;
    }
    
    private static String min2time(final int min) {
        final int hour = min / 60, minute = min % 60;
        String time = (hour < 10 ? "0" : "") + hour;
        time += ":";
        time += (minute < 10 ? "0" : "") + minute;
        return time;
    }
    
}
