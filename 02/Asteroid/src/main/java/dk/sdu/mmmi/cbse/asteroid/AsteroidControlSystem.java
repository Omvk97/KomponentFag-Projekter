/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

/**
 *
 * @author oliver
 */
public class AsteroidControlSystem implements IEntityProcessingService {

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            
            int randomInteger = random.nextInt(60) + 1;
            boolean moveLeft = randomInteger % 10 == 0;
            boolean moveRight = randomInteger % 15 == 0;
            movingPart.setLeft(moveLeft);
            movingPart.setRight(moveRight);
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape(asteroid);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians + (2 * Math.PI) / (3)) * 20);
        shapey[0] = (float) (y + Math.sin(radians + (2 * Math.PI) / (3)) * 20);

        shapex[1] = (float) (x + Math.cos(radians + (4 * Math.PI) / (3)) * 20);
        shapey[1] = (float) (y + Math.sin(radians + (4 * Math.PI) / (3)) * 20);

        shapex[2] = (float) (x + Math.cos(radians + (5 * Math.PI) / (3)) * 20);
        shapey[2] = (float) (y + Math.sin(radians + (5 * Math.PI) / (3)) * 20);

        shapex[3] = (float) (x + Math.cos(radians + Math.PI / 3) * 20);
        shapey[3] = (float) (y + Math.sin(radians + Math.PI / 3) * 20);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
