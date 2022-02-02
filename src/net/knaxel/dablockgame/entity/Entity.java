package net.knaxel.dablockgame.entity;

import java.util.HashMap;

import net.knaxel.dablockgame.entity.components.Camera;
import net.knaxel.dablockgame.entity.components.EntityComponent;

public abstract class Entity {

	private HashMap<Class<?>, EntityComponent> components;
	
	public Entity() {
		components = new HashMap<>();
	}
	
    public boolean hasComponent(Class<?> clazz) {
        if (components.containsKey(clazz)) {
            return true;
        }
        
        return false;
    }
    public <T> T getComponent(Class<T> clazz) throws IllegalArgumentException {
        if (components.containsKey(clazz)) {
            return clazz.cast(components.get(clazz));
        }
        
        throw new IllegalArgumentException("component not found "
                + clazz.getName());
    }
    public void addComponent(EntityComponent c) {
    	if(c instanceof Camera) {
    		Camera camera = ((Camera) c);
    		camera.followEntity(this);
    		camera.setPitch(-90);
    	}
        components.put(c.getClass(), c);
        
    }
}

