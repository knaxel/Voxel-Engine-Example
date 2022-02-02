package net.knaxel.dablockgame;

import net.knaxel.dablockgame.engine.Engine;

public class Main {

	private final Engine engine  = new Engine();
	public Main() {
		engine.init();
	}
	
	public static void main(String[] args) {
		Main main  = new Main();
	}

}