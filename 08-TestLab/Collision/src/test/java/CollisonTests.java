
import dk.sdu.mmmi.cbse.collision.CollisionProcessing;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author oliver
 */
public class CollisonTests {
    
    private CollisionProcessing collisionProcessing;
    private Entity entity1;
    private Entity entity2;
    
    @Before
    public void setup() {
        entity1 = new Entity();
        entity1.setRadius(20);
        entity1.add(new PositionPart(0, 0, 0));
        
        entity2 = new Entity();
        entity2.setRadius(5);
        entity2.add(new PositionPart(15, 0, 0));
        
        collisionProcessing = new CollisionProcessing();
    }
    
    @Test
    public void entityCollisionTest() {
        Assert.assertTrue(collisionProcessing.entitesCollides(entity1, entity2));
    }
}
