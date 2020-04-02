package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
import dk.sdu.mmmi.cbse.enemy.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemy.EnemyPlugin;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private final List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private final List<IPostEntityProcessingService> postEntityProcessingServices = new ArrayList<>();
    private final List<IGamePluginService> entityPlugins = new ArrayList<>();
    private final World world = new World();
    private ApplicationContext context;

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        
        context = new ClassPathXmlApplicationContext("Beans.xml");

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessingService : getPostEntityProcessingServices()) {
            postEntityProcessingService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    
     private Collection<? extends IGamePluginService> getPluginServices() {
        List<IGamePluginService> gamePluginServices = new ArrayList<>();
        EnemyPlugin enemyPlugin = (EnemyPlugin) context.getBean("enemyPluginBean");
        gamePluginServices.add(enemyPlugin);
        PlayerPlugin playerPlugin = (PlayerPlugin) context.getBean("playerPluginBean");
        gamePluginServices.add(playerPlugin);
        
        gamePluginServices.addAll(SPILocator.locateAll(IGamePluginService.class));
        
        return gamePluginServices;
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        List<IEntityProcessingService> processingServices = new ArrayList<>();
        EnemyControlSystem enemyControlSystem = (EnemyControlSystem) context.getBean("enemyControlSystemBean");
        processingServices.add(enemyControlSystem);
        
        PlayerControlSystem playerControlSystem = (PlayerControlSystem) context.getBean("playerControlSystemBean");
        processingServices.add(playerControlSystem);
        
        processingServices.addAll(SPILocator.locateAll(IEntityProcessingService.class));
        
        return processingServices;
    }
    
       private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
