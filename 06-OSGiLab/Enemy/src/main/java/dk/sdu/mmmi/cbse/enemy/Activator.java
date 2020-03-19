/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 *
 * @author oliver
 */
public class Activator implements BundleActivator {
    
    private EnemyControlSystem controlSystem = new EnemyControlSystem();
    private EnemyPlugin plugin = new EnemyPlugin();
    
    private ServiceRegistration controlSystemServiceRegistration;
    private ServiceRegistration enemyPluginServiceRegistration;
    
    public void start(BundleContext context) throws Exception {
        //TODO add activation code here
        controlSystemServiceRegistration = context.registerService(IEntityProcessingService.class, controlSystem, null);
        enemyPluginServiceRegistration = context.registerService(IGamePluginService.class, plugin, null);
    }
    
    public void stop(BundleContext context) throws Exception {
        //TODO add deactivation code here
        controlSystemServiceRegistration.unregister();
        enemyPluginServiceRegistration.unregister();
    }
    
}
