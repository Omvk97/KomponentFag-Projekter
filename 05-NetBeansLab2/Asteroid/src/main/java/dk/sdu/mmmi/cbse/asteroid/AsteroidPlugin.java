/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityType;
import dk.sdu.mmmi.cbse.common.data.entityparts.IdentityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author oliver
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)
})
public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        asteroid = createBigAsteroid(gameData);
        world.addEntity(asteroid);
    }

    public static Entity createBigAsteroid(GameData gameData) {

        float deacceleration = 0;
        float acceleration = 5000;
        float maxSpeed = 75;
        float rotationSpeed = 3;
        Random random = new Random();
        float x = gameData.getDisplayWidth() / (random.nextInt(32) + 1);
        float y = 0;
        float radians = (float) (Math.PI / 4);

        Asteroid asteroidEntity = new Asteroid(AsteroidSize.BIG);
        asteroidEntity.setRadius(15);
        asteroidEntity.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroidEntity.add(new PositionPart(x, y, radians));
        asteroidEntity.add(new IdentityPart(EntityType.Asteroid));

        return asteroidEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
}
