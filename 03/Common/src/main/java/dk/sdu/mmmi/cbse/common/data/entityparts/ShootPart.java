/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;


/**
 *
 * @author oliver
 */
public class ShootPart implements EntityPart {
    
    boolean shooting;

    @Override
    public void process(GameData gameData, Entity entity) {
        
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean isShooting) {
        this.shooting = isShooting;
    }
    
}
