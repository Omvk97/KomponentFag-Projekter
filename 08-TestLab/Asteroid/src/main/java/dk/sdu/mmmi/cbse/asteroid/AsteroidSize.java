/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

/**
 *
 * @author oliver
 */
public enum AsteroidSize {
    BIG("BIG"),
    SMALL("SMALL");
    
    private String size;

    private AsteroidSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
