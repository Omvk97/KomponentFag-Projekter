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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author oliver
 */
public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    public BulletPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        bullet = createBullet(gameData);
        world.addEntity(bullet);
    }

    private Entity createBullet(GameData gameData) {

        float deacceleration = 0;
        float acceleration = 75;
        float maxSpeed = 75;
        float rotationSpeed = 0;
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 8;
        float radians = (float) (Math.PI / 2);
        
        Entity bulletEntity = new Bullet();
        bulletEntity.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bulletEntity.add(new PositionPart(x, y, radians));
        
        return bulletEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(bullet);
    }

}