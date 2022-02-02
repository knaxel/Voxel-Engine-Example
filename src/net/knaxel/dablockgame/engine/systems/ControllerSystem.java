package net.knaxel.dablockgame.engine.systems;

import static org.lwjgl.glfw.GLFW.GLFW_RAW_MOUSE_MOTION;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwRawMouseMotionSupported;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import org.lwjgl.glfw.GLFW;

import net.knaxel.dablockgame.engine.Engine;
import net.knaxel.dablockgame.engine.input.KeyBoard;
import net.knaxel.dablockgame.engine.input.Mouse;
import net.knaxel.dablockgame.engine.utils.vector.Vector3;
import net.knaxel.dablockgame.entity.Entity;
import net.knaxel.dablockgame.entity.components.Location;
import net.knaxel.dablockgame.entity.components.Velocity;
import net.knaxel.dablockgame.event.EventHandler;
import net.knaxel.dablockgame.event.KeyPressEvent;
import net.knaxel.dablockgame.event.KeyReleaseEvent;
import net.knaxel.dablockgame.event.Listener;
import net.knaxel.dablockgame.event.MouseMoveEvent;

public class ControllerSystem extends EngineSystem implements Listener{
	
	private boolean[] pressed = new boolean[350];
	private float speed = .5f;
	private float fastSpeed = 10f;
	private float acc = .2f;
	private float airfric = .23f;
	private Location loc;
	private Velocity vel;
	
	

	private void initInput() {
		Mouse mouse = new Mouse( engine.getSystem(EventSystem.class) );
		KeyBoard keyboard = new KeyBoard(engine.getSystem(EventSystem.class) );
		GLFW.glfwSetKeyCallback(engine.getDisplay().getWindow(), keyboard);
		GLFW.glfwSetCursorPosCallback(engine.getDisplay().getWindow(), mouse);
		
		if (glfwRawMouseMotionSupported()) glfwSetInputMode(engine.getDisplay().getWindow(), GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
		GLFW.glfwSetInputMode(engine.getDisplay().getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public ControllerSystem(Engine e) {
		super(e);
		this.loc = (Location) e.getPlayer().getComponent(Location.class);
		this.vel = (Velocity) e.getPlayer().getComponent(Velocity.class);
		initInput();
		}
	public void setEntity(Entity c) {
		this.loc = (Location) c.getComponent(Location.class);
		this.vel = (Velocity) c.getComponent(Velocity.class);
		initInput();
	}
	
	@EventHandler
	public void onMouseMoveEvent(MouseMoveEvent e) {
		double dx = e.getDx();
		double dy = e.getDy();
		loc.addYaw((float) (dx) / 5.0f);
		loc.addPitch( (float) (dy) / 5.0f);
		Vector3 l = Vector3.getVectorFromRotation(loc.getDirection());

	}
	public void tick(double dt) {
		Vector3 v = vel.getVector();
		Vector3 p = loc.getPosition();
		Vector3 d = Vector3.getVectorFromRotation(loc.getDirection());
		float length  = vel.getVector().length();

		float a = acc;
		if(pressed[GLFW.GLFW_KEY_LEFT_SHIFT]) {
			a = fastSpeed;
		}
		
		v.scale((float)(airfric));
		if(pressed[GLFW.GLFW_KEY_W]) {
			
			if(v.length()+a < speed) {
				v.add(d.x*a, d.y*a, d.z*a);
			}else if(v.length()+a > speed) {
				v.set(d.x*a, d.y*a,d.z*a);
			}
		}else if(pressed[GLFW.GLFW_KEY_S]) {
			if(v.length()+a < speed) {
				v.add(-d.x*a, -d.y*a, -d.z*a);
			}else if(v.length()+a > speed) {
				v.set(-d.x*a, -d.y*a,-d.z*a);
			}

		}
		if(pressed[GLFW.GLFW_KEY_SPACE]){
			v.y = 1;
		}
		
		
		p.add(v.x*(float)dt,v.y*(float)dt,v.z*(float)dt);
	}
	@EventHandler
	public void onKeyReleaseEvent(KeyReleaseEvent e) {

		pressed[e.getKey()] = false;
		
	}
	@EventHandler
	public void onKeyPressEvent(KeyPressEvent e) {
		
		int key = e.getKey();
		
		pressed[key] = true;
		
		if(key == GLFW.GLFW_KEY_E) {
			RenderSystem r = ((RenderSystem)this.engine.getSystem(RenderSystem.class));
			r.toggleWireframe();
		}
		if(key == GLFW.GLFW_KEY_ESCAPE) {
			this.engine.close();
		}
		
		
		
	}
}
