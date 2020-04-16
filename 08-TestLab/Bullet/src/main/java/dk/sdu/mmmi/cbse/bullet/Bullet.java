/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityType;
import dk.sdu.mmmi.cbse.common.data.entityparts.IdentityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

/**
 *
 * @author oliver
 */
public class Bullet extends Entity {

    public static Entity createBullet(float x, float y, float radians) {

        float deacceleration = 0;
        float acceleration = 7500;
        float maxSpeed = 500;
        float rotationSpeed = 0;

        Entity bulletEntity = new Bullet();
        bulletEntity.setRadius(2);
        bulletEntity.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        bulletEntity.add(new PositionPart(x, y, radians));
        bulletEntity.add(new IdentityPart(EntityType.Projectile));

        return bulletEntity;
    }
}
