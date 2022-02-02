package net.knaxel.dablockgame.event;

public class KeyReleaseEvent extends Event{
	private int key_code;
	public KeyReleaseEvent(int key) {
		super(true);
		key_code = key;// TODO Auto-generated constructor stub
	}
	
	public int getKey() {
		return key_code;
	}

}
