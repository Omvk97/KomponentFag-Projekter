/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author oliver
 */
public class CollisionPlugin implements IGamePluginService {

    boolean running;

    public CollisionPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        running = true;

        while (running) {
            for (Entity entity1 : world.getEntities()) {

                PositionPart entity1PositionPart = entity1.getPart(PositionPart.class);
                for (Entity entity2 : world.getEntities()) {
                    PositionPart entity2PositionPart = entity2.getPart(PositionPart.class);
                    float dx = entity1PositionPart.getX() - entity2PositionPart.getX();
                    float dy = entity1PositionPart.getY() - entity2PositionPart.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < entity1.getRadius() + entity2.getRadius()) {
                        System.out.println("Collision detected!");
                    }
                }
            }
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        running = false;
    }

}
