package net.knaxel.dablockgame.engine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import net.knaxel.dablockgame.engine.systems.EventSystem;
import net.knaxel.dablockgame.event.MouseMoveEvent;

public class Mouse  extends GLFWCursorPosCallback{
	private double lx =0,ly=0;
	private double dx =0,dy=0;
	
	private EventSystem eventSystem;
	
	public Mouse(EventSystem eventSystem) {
		this.eventSystem = eventSystem;
	}
	public void invoke(long win, double x, double y) {
		
		
		dx=x-lx;dy=y-ly;
		lx =x;ly=y;
		
		eventSystem.callEvent(new MouseMoveEvent(dx,dy) );
		
		
	}

}
