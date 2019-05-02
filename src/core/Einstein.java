package core;

import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Distance;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XplusCeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;

/**
 * The class for solving Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class Einstein extends Base {

    /** The constant related to the number of pets, drinks, colors, nations and cigarettes. */
    final int SIZE = 5;

    /** The enum of all possible pets. */
    enum Pet { DOGS, BIRDS, HORSES, CATS, FISH }

    /** The enum of all possible drinks. */
    enum Drink { TEA, COFFEE, MILK, BEER, WATER }

    /** The enum of all possible colors. */
    enum Color { RED, GREEN, WHITE, YELLOW, BLUE }

    /** The enum of all possible nations. */
    enum Nation { ENGLISHMAN, SWEDE, DANE, NORWEGIAN, GERMAN }

    /** The enum of all possible cigarettes. */
    enum Cigarette { PALLMALL, DUNHILL, BLUEMASTER, PRINCE, BLEND }


    @Override
    public void model() {
        store = new Store();
        
        final IntVar DIST_1 = new IntVar(store, 1, 1);
		 
        IntVar pet[] = new IntVar[SIZE];
        IntVar drink[] = new IntVar[SIZE];
        IntVar color[] = new IntVar[SIZE];
        IntVar nation[] = new IntVar[SIZE];
        IntVar cigarette[] = new IntVar[SIZE];
        
        for (int i = 0; i < SIZE; i++) {
            pet[i] = new IntVar(store, Pet.values()[i].toString(), 1, SIZE);
            drink[i] = new IntVar(store, Drink.values()[i].toString(), 1, SIZE);
            color[i] = new IntVar(store, Color.values()[i].toString(), 1, SIZE);
            nation[i] = new IntVar(store, Nation.values()[i].toString(), 1, SIZE);
            cigarette[i] = new IntVar(store, Cigarette.values()[i].toString(), 1, SIZE);
            vars.add(pet[i]); 
            vars.add(drink[i]);
            vars.add(color[i]); 
            vars.add(nation[i]);
            vars.add(cigarette[i]); 
        }
        
        store.impose(new Alldifferent(pet));
        store.impose(new Alldifferent(drink));
        store.impose(new Alldifferent(color));
        store.impose(new Alldifferent(nation));
        store.impose(new Alldifferent(cigarette));

        // Constraints.
        store.impose(new XeqY( nation[Nation.ENGLISHMAN.ordinal()], color[Color.RED.ordinal()] ));                  // 1
        store.impose(new XeqY( nation[Nation.SWEDE.ordinal()], pet[Pet.DOGS.ordinal()] ));                          // 2
        store.impose(new XeqY( nation[Nation.DANE.ordinal()], drink[Drink.TEA.ordinal()] ));                        // 3
        store.impose(new XplusCeqZ( color[Color.GREEN.ordinal()], 1, color[Color.WHITE.ordinal()] ));               // 4
        store.impose(new XeqY( color[Color.GREEN.ordinal()], drink[Drink.COFFEE.ordinal()] ));                      // 5
        store.impose(new XeqY( cigarette[Cigarette.PALLMALL.ordinal()], pet[Pet.BIRDS.ordinal()] ));                // 6
        store.impose(new XeqY( color[Color.YELLOW.ordinal()], cigarette[Cigarette.DUNHILL.ordinal()] ));            // 7
        store.impose(new XeqC( drink[Drink.MILK.ordinal()], 3 ));                                                   // 8
        store.impose(new XeqC( nation[Nation.NORWEGIAN.ordinal()], 1 ));                                            // 9
        store.impose(new Distance( cigarette[Cigarette.BLEND.ordinal()], pet[Pet.CATS.ordinal()], DIST_1 ));        // 10
        store.impose(new Distance( cigarette[Cigarette.DUNHILL.ordinal()], pet[Pet.HORSES.ordinal()], DIST_1 ));    // 11
        store.impose(new XeqY( cigarette[Cigarette.BLUEMASTER.ordinal()], drink[Drink.BEER.ordinal()] ));           // 12
        store.impose(new XeqY( nation[Nation.GERMAN.ordinal()], cigarette[Cigarette.PRINCE.ordinal()] ));           // 13
        store.impose(new Distance( nation[Nation.NORWEGIAN.ordinal()], color[Color.BLUE.ordinal()], DIST_1 ));      // 14
        store.impose(new Distance( cigarette[Cigarette.BLEND.ordinal()], drink[Drink.WATER.ordinal()], DIST_1 ));   // 15     
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
        
        // Setting first column - house number.
        for (int i = 0; i < SIZE; i++)
            solution[i][0] = Integer.toString(i+1);
        
        // Setting main content - information about the house.
        boolean found = false;
        for (int i = 0; i < vars.size(); i++, found = false) {
            // Var is a Pet.
            for (int j = 0; j < SIZE && found != true; j++) 
                if (vars.get(i).id.equals( Pet.values()[j].toString() )) {
                    solution[vars.get(i).value()-1][3] = vars.get(i).id;
                    found = true;
                }
            // Var is a Colour.
            for (int j = 0; j < SIZE && found != true; j++) 
                if (vars.get(i).id.equals( Color.values()[j].toString() )) {
                    solution[vars.get(i).value()-1][1] = vars.get(i).id;
                    found = true;
                }
            // Var is a Drink.
            for (int j = 0; j < SIZE && found != true; j++) 
                if (vars.get(i).id.equals( Drink.values()[j].toString() )) {
                    solution[vars.get(i).value()-1][4] = vars.get(i).id;
                    found = true;
                }
            // Var is a Nation.
            for (int j = 0; j < SIZE && found != true; j++) 
                if (vars.get(i).id.equals( Nation.values()[j].toString() )) {
                    solution[vars.get(i).value()-1][2] = vars.get(i).id;
                    found = true;
                }
            // Var is a Cigar.
            for (int j = 0; j < SIZE && found != true; j++) 
                if (vars.get(i).id.equals( Cigarette.values()[j].toString() )) {
                    solution[vars.get(i).value()-1][5] = vars.get(i).id;
                    found = true;
                }
        }
        
        // Converting upper case to lower case in main content (e.g. WORD -> Word).
        String s;
        for (int i = 0; i < SIZE; i++)
            for (int j = 1; j < SIZE+1; j++) {
                s = solution[i][j];
                s = s.substring(0, 1) + s.substring(1).toLowerCase();
                solution[i][j] = s;
            }

        return solution;
    }
    
}
