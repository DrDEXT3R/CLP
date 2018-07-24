package core;

import java.util.ArrayList;
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
 * Class for solving Einstein's Riddle.
 *
 * @author Tomasz Strzoda
 */
public class Einstein extends Base {
    
    enum Pet { DOGS , BIRDS, HORSES, CATS, FISH; }
    enum Drink { TEA, COFFEE, MILK, BEER, WATER; }
    enum Color { RED, GREEN, WHITE, YELLOW, BLUE; }
    enum Nation { ENGLISHMAN, SWEDE, DANE, NORWEGIAN, GERMAN; }
    enum Cigarette { PALLMALL, DUNHILL, BLUEMASTER, PRINCE, BLEND; }
    
    @Override
    public void model() {
        store = new Store();
        vars = new ArrayList<IntVar>();
        
        final int SIZE = 5;
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

        output += "\n\nTime: " + Long.toString(T2-T1) + "ns";
        return output;
    }
    
}