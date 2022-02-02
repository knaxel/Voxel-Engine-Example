package net.knaxel.dablockgame.engine.systems;

import net.knaxel.dablockgame.engine.Engine;

public abstract class EngineSystem {

    protected Engine engine;
    public EngineSystem(Engine e) {
    	setEngine(e);
    }
    public final void setEngine(Engine e) {
        engine = e;
    }
    public final Engine getEngine() {
        return engine;
    }
    public void tick(double delta) {}
	public void addedToEngine(Engine e) {}
	public void removedFromEngine(Engine e) {}
}
