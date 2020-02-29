/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()) {
            ShootPart shootPart = entity.getPart(ShootPart.class);
            PositionPart positionPart = entity.getPart(PositionPart.class);

            // entity is a shooting entity, such as player or enemy
            if (shootPart != null) {
                // add bullets to the world 
                if (shootPart.isShooting()) {
                    float radians = positionPart.getRadians();
                    Entity newBullet = Bullet.createBullet((positionPart.getX() + (float) Math.cos(radians) * 15),
                            (positionPart.getY() + (float) Math.sin(radians) * 15), positionPart.getRadians());
                    world.addEntity(newBullet);
                }
                continue;
            }

            if (entity.getClass().equals(Bullet.class)) {

                MovingPart movingPart = entity.getPart(MovingPart.class);

                // Remove bullets when they go over the screen width
                if (positionPart.getX() >= gameData.getDisplayWidth() || positionPart.getX() <= 0) {
                    world.removeEntity(entity);
                    continue;
                }
                if (positionPart.getY() >= gameData.getDisplayHeight() || positionPart.getY() <= 0) {
                    world.removeEntity(entity);
                    continue;
                }

                movingPart.setUp(true);

                movingPart.process(gameData, entity);
                positionPart.process(gameData, entity);

                updateShape(entity);
            }
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians + (2 * Math.PI) / (3)) * 2);
        shapey[0] = (float) (y + Math.sin(radians + (2 * Math.PI) / (3)) * 2);

        shapex[1] = (float) (x + Math.cos(radians + (4 * Math.PI) / (3)) * 2);
        shapey[1] = (float) (y + Math.sin(radians + (4 * Math.PI) / (3)) * 2);

        shapex[2] = (float) (x + Math.cos(radians + (5 * Math.PI) / (3)) * 2);
        shapey[2] = (float) (y + Math.sin(radians + (5 * Math.PI) / (3)) * 2);

        shapex[3] = (float) (x + Math.cos(radians + Math.PI / 3) * 2);
        shapey[3] = (float) (y + Math.sin(radians + Math.PI / 3) * 2);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
