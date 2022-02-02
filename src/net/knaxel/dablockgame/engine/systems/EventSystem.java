package net.knaxel.dablockgame.engine.systems;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.knaxel.dablockgame.engine.Engine;
import net.knaxel.dablockgame.event.Event;
import net.knaxel.dablockgame.event.EventHandler;
import net.knaxel.dablockgame.event.Listener;

public class EventSystem extends EngineSystem{

    public EventSystem(Engine e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	private List<Listener> registered = new ArrayList<Listener>();
    private List<Event> events = new ArrayList<>();

    public  void register(Listener listener) {
        if (!registered.contains(listener)) {
            registered.add(listener);
        }
    }
    public  void unregister(Listener listener) {
        if (registered.contains(listener)) {
            registered.remove(listener);
        }
    }

    public  List<Listener> getRegistered() {
        return registered;
    }

	@Override
    public void tick(double dt) { 
    	for(final Event e: events) {
    		execute(e);
    	}
    	events.clear();
    }
	public void callEvent(final Event event) {
		if(event.isSynchronous()) {
			events.add(event);
			return;
		}
		new Thread() {
			public void run() {
				execute(event);
			}
		}.start();
	}

	private void execute(final Event event) {
		for (Listener listener : registered) {
			for (Method method : listener.getClass().getMethods()) {
				if (!method.isAnnotationPresent(EventHandler.class))
					continue;
				if(!method.getParameterTypes()[0].equals(event.getClass()))
					continue;
				try {
					method.invoke(listener, event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
}
