/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityType;
import dk.sdu.mmmi.cbse.common.data.entityparts.IdentityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.events.AsteroidCollisionEvent;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author oliver
 */
public class CollisionProcessing implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity1 : world.getEntities()) {
            PositionPart entity1PositionPart = entity1.getPart(PositionPart.class);
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }
                PositionPart entity2PositionPart = entity2.getPart(PositionPart.class);
                float dx = entity1PositionPart.getX() - entity2PositionPart.getX();
                float dy = entity1PositionPart.getY() - entity2PositionPart.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < entity1.getRadius() + entity2.getRadius()) {
                    IdentityPart entity1IdentityPart = entity1.getPart(IdentityPart.class);
                    IdentityPart entity2IdentityPart = entity2.getPart(IdentityPart.class);

                    if (entity1IdentityPart.getType() == EntityType.Asteroid && entity2IdentityPart.getType() == EntityType.Projectile) {
                        world.removeEntity(entity2);
                        gameData.addEvent(new AsteroidCollisionEvent(entity1));
                        break;
                    } else if (entity2IdentityPart.getType() == EntityType.Asteroid && entity1IdentityPart.getType() == EntityType.Projectile) {
                        world.removeEntity(entity1);
                        gameData.addEvent(new AsteroidCollisionEvent(entity2));
                        break;
                    } else if (entity1IdentityPart.getType() == EntityType.Player) {
                        world.removeEntity(entity1);
                    } else if (entity2IdentityPart.getType() == EntityType.Player) {
                        world.removeEntity(entity2);
                    }
                }
            }
        }
    }
}