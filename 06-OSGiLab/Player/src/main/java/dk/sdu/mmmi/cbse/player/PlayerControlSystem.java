package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            ShootPart shootPart = player.getPart(ShootPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            
            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                shootPart.setShooting(true);
            } else {
                shootPart.setShooting(false);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * Math.PI / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * Math.PI / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + Math.PI) * 5);
        shapey[2] = (float) (y + Math.sin(radians + Math.PI) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * Math.PI / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * Math.PI / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}