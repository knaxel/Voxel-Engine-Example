package net.knaxel.dablockgame.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import net.knaxel.dablockgame.engine.systems.EventSystem;
import net.knaxel.dablockgame.event.KeyPressEvent;
import net.knaxel.dablockgame.event.KeyReleaseEvent;

public class KeyBoard  extends GLFWKeyCallback{

    private EventSystem eventSystem;
	public KeyBoard(EventSystem e) {
		eventSystem = e;
	}


	@Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
		
		if(action == GLFW.GLFW_PRESS) {
			eventSystem.callEvent(new KeyPressEvent(key) );
		}else if(action == GLFW.GLFW_RELEASE){
			eventSystem.callEvent(new KeyReleaseEvent(key) ); 
		}

    }


	

}
