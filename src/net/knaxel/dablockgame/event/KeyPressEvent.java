package net.knaxel.dablockgame.event;

public class KeyPressEvent extends Event{
	
	private int key_code;
	
	public KeyPressEvent(int key) {
		super(true);
		key_code = key;
	}
	public int getKey() {
		return key_code;
	}
}
