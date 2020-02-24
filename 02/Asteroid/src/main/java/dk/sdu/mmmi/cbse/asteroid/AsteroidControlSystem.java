
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.AsteroidCollisionEvent;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Random;

/**
 *
 * @author oliver
 */
public class AsteroidControlSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        if (world.getEntities(Asteroid.class).size() < 3) {
            world.addEntity(Asteroid.createAsteroid(gameData));
        }

        for (Entity entity : world.getEntities(Asteroid.class)) {
            Asteroid asteroid = (Asteroid) entity;
            PositionPart positionPart = asteroid.getPart(PositionPart.class);

            boolean asteroidRemoved = false;

            for (Event event : gameData.getEvents(AsteroidCollisionEvent.class, asteroid.getID())) {
                if (asteroid.getSize() == AsteroidSize.BIG) {
                    asteroid.setSize(AsteroidSize.SMALL);
                    asteroid.setRadius(5);
                    world.addEntity(Asteroid.createSmallAsteroid(gameData, positionPart.getX(), positionPart.getY(),
                            (float) (positionPart.getRadians() + (Math.PI / 3))));
                } else if (asteroid.getSize() == AsteroidSize.SMALL) {
                    world.removeEntity(asteroid);
                    asteroidRemoved = true;
                }
                gameData.removeEvent(event);
            }

            if (asteroidRemoved) {
                continue;
            }

            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            int randomInteger = random.nextInt(60) + 1;
            boolean moveLeft = randomInteger % 10 == 0;
            boolean moveRight = randomInteger % 15 == 0;
            movingPart.setLeft(moveLeft);
            movingPart.setRight(moveRight);
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateAsteroid((Asteroid) asteroid);
        }
    }

    private void updateAsteroid(Asteroid asteroid) {
        float[] shapex = asteroid.getShapeX();
        float[] shapey = asteroid.getShapeY();
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        if (asteroid.getSize() == AsteroidSize.BIG) {
            shapex[0] = (float) (x + Math.cos(radians + (2 * Math.PI) / (3)) * 20);
            shapey[0] = (float) (y + Math.sin(radians + (2 * Math.PI) / (3)) * 20);

            shapex[1] = (float) (x + Math.cos(radians + (4 * Math.PI) / (3)) * 20);
            shapey[1] = (float) (y + Math.sin(radians + (4 * Math.PI) / (3)) * 20);

            shapex[2] = (float) (x + Math.cos(radians + (5 * Math.PI) / (3)) * 20);
            shapey[2] = (float) (y + Math.sin(radians + (5 * Math.PI) / (3)) * 20);

            shapex[3] = (float) (x + Math.cos(radians + Math.PI / 3) * 20);
            shapey[3] = (float) (y + Math.sin(radians + Math.PI / 3) * 20);
        } else {
            shapex[0] = (float) (x + Math.cos(radians + (2 * Math.PI) / (3)) * 5);
            shapey[0] = (float) (y + Math.sin(radians + (2 * Math.PI) / (3)) * 5);

            shapex[1] = (float) (x + Math.cos(radians + (4 * Math.PI) / (3)) * 5);
            shapey[1] = (float) (y + Math.sin(radians + (4 * Math.PI) / (3)) * 5);

            shapex[2] = (float) (x + Math.cos(radians + (5 * Math.PI) / (3)) * 5);
            shapey[2] = (float) (y + Math.sin(radians + (5 * Math.PI) / (3)) * 5);

            shapex[3] = (float) (x + Math.cos(radians + Math.PI / 3) * 5);
            shapey[3] = (float) (y + Math.sin(radians + Math.PI / 3) * 5);
        }

        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

}
