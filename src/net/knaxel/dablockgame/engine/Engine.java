package net.knaxel.dablockgame.engine;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.knaxel.dablockgame.engine.systems.ChunkLoadSystem;
import net.knaxel.dablockgame.engine.systems.ControllerSystem;
import net.knaxel.dablockgame.engine.systems.EngineSystem;
import net.knaxel.dablockgame.engine.systems.EventSystem;
import net.knaxel.dablockgame.engine.systems.MeshingSystem;
import net.knaxel.dablockgame.engine.systems.RenderSystem;
import net.knaxel.dablockgame.engine.utils.vector.Vector2;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.entity.Entity;
import net.knaxel.dablockgame.entity.Player;
import net.knaxel.dablockgame.entity.components.Camera;
import net.knaxel.dablockgame.world.World;

public final class Engine {

	private Display display = new Display();
	
	private Camera camera = new Camera(((float)display.getWidth()/display.getHeight()), 80f, .001f, Float.MAX_VALUE);;
	//special player entity probably change later, just comes with default req components
	private Player player ;
	
	//eventually will be list if multi worlds 
	private World world = new World();
	
	//list of entities loaded we do nothing but hold on these like the world, just data
	private final HashMap<Class<?>, List<Entity>> entity_groups = new HashMap<>();
	private final List<Entity> entities = new ArrayList<>();
	
	//list of the systems attatched to game engine
	private final List<EngineSystem> systems = new ArrayList<EngineSystem>();
	private RenderSystem renderSystem ;
	
	private boolean shouldntClose = true;
	
	public Engine() {}
	
	public void init() {
		player = new Player(new Vector3(32, 128, 32), new Vector2(2f, 1f));
		player.getLocation().setRotation(-.45f,.85f,0f);
		player.addComponent(camera);
		addSystem(new EventSystem(this));
		addSystem(new ControllerSystem(this));
		addSystem(new MeshingSystem(this));
		ChunkLoadSystem cd = new ChunkLoadSystem(this);
		cd.bleh(0);
		addSystem(cd);
		renderSystem = new RenderSystem(this);
		addSystem(renderSystem);
		
		(getSystem(EventSystem.class)).register(getSystem(ControllerSystem.class));


		loop();

		display.close();
	}

	private void loop() {
		long lastTime = System.currentTimeMillis();
		long lastfTime = System.currentTimeMillis();

		double amountOfTicks = 60.0;
		double nsBetweenTicks = 1000.0 / amountOfTicks;
		double delta = 0;

		int fps = 0;
		while (shouldntClose) {
			shouldntClose = !display.shouldClose() ;
			long now = System.currentTimeMillis();
			delta += (now - lastTime) / nsBetweenTicks;
			lastTime = now;

			if (delta >= 1) {
				glfwPollEvents();
				
				for(EngineSystem sys: systems) {
					sys.tick(delta);
				}

				delta=0;
			}

			if (now - lastfTime >= 1000) {
				lastfTime = now;

				System.out.println("fps: " + fps);
				fps = 0;
			}
			fps++;
			
			renderSystem.render();
			display.update();
		}
		
	}
	
	public void close() {
		shouldntClose=false;
	}
	public World getWorld() {
		return world;
	}
	public Player getPlayer() {
		return player;
	}
	public Camera getCamera() {
		return camera;
	}
	public Display getDisplay() {
		return display;
	}
	
	public  List<Entity> getEntities(Class<?> type) {
		List<Entity> group = entity_groups.get(type);
        if (!entity_groups.containsKey(type)) {
        	groupEntities(type, group);
        	entity_groups.put(type, group);
        }
        return Collections.unmodifiableList(group); 
		
	}
	
	private void groupEntities(Class<?> c, List<Entity> group) {
	       assert group.isEmpty();
	        for (Entity e : entities) {
	            if (c.isInstance(e)) {
	                group.add(e);
	            }
	        }
	}
    public void addSystem(EngineSystem s) throws IllegalStateException,
            IllegalArgumentException {

        if (systems.contains(s)) {
            throw new IllegalArgumentException("system already added");
        }
        
        s.setEngine(this);
        systems.add(s);
        s.addedToEngine(this);
    }

    public void removeSystem(EngineSystem s) throws IllegalStateException,
            IllegalArgumentException {

        if (!systems.contains(s)) {
            throw new IllegalArgumentException("system is unknown");
        }
        s.removedFromEngine(this);
        systems.remove(s);
        s.setEngine(null);        
    }
    public boolean hasSystem(Class<?> clazz) {
        for (EngineSystem s : systems) {
            if (clazz.isInstance(s)) {
                return true;
            }
        }
        return false;
    }
    public <T> T getSystem(Class<T> clazz) throws IllegalArgumentException {
        for (EngineSystem s : systems) {
            if (clazz.isInstance(s)) {
                return clazz.cast(s);
            }
        }
        
        throw new IllegalArgumentException("system not found "
                + clazz.getName());
    }

}
