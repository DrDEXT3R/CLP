package app;

import core.Einstein;

/**
 * Main class.
 * 
 * @author Tomasz Strzoda
 */
public class CLP {

    public static void main(String[] args) {
        Einstein einstein = new Einstein();
        einstein.model();
        System.out.println(einstein.search());
    }
    
}
