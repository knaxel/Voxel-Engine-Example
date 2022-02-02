package net.knaxel.dablockgame.event;

public abstract class Event {
	private String name;
	private boolean sync;

	
	public Event(boolean sync) {
		this.sync=sync;
	}
	public String getEventName() {
	    if (this.name == null) {
	        this.name = this.getClass().getSimpleName();
	    }
	    return this.name;
	}

	protected void setName(String name) {
	    this.name = name;
	}
	public boolean isSynchronous() {
		return sync;
	}
	
}
