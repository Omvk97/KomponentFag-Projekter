/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityType;
import dk.sdu.mmmi.cbse.common.data.entityparts.IdentityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.Random;

/**
 *
 * @author oliver
 */
public class Asteroid extends Entity {

    private AsteroidSize size;

    public void setSize(AsteroidSize size) {
        this.size = size;
    }

    public Asteroid(AsteroidSize type) {
        this.size = type;
    }

    public AsteroidSize getSize() {
        return size;
    }
    
    public static Entity createAsteroid(GameData gameData) {

        float deacceleration = 0;
        float acceleration = 5000;
        float maxSpeed = 75;
        float rotationSpeed = 3;
        Random random = new Random();
        float x = gameData.getDisplayWidth() / (random.nextInt(32) + 1);
        float y = 0;
        float radians = (float) (Math.PI / 4);
               
        Asteroid asteroidEntity = new Asteroid(AsteroidSize.BIG);
        asteroidEntity.setRadius(20);
        asteroidEntity.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroidEntity.add(new PositionPart(x, y, radians));
        asteroidEntity.add(new IdentityPart(EntityType.Asteroid));
        
        return asteroidEntity;
    }
    
        public static Entity createSmallAsteroid(GameData gameData, float x, float y, float radians) {
        
        float deacceleration = 0;
        float acceleration = 5000;
        float maxSpeed = 75;
        float rotationSpeed = 3;
               
        Asteroid asteroidEntity = new Asteroid(AsteroidSize.SMALL);
        asteroidEntity.setRadius(8);
        asteroidEntity.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroidEntity.add(new PositionPart(x, y, radians));
        asteroidEntity.add(new IdentityPart(EntityType.Asteroid));
        
        return asteroidEntity;
    }
}
