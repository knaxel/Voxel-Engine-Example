package net.knaxel.dablockgame.event;

public class MouseMoveEvent extends Event{

	private double dx,dy;
	
	public MouseMoveEvent(double dx, double dy) {
		super(true);
		this.dx =dx;
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
	
	
}
