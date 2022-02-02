package net.knaxel.dablockgame.entity.components;

import net.knaxel.dablockgame.entity.Entity;

public abstract class EntityComponent {
	protected Entity entity;
	public EntityComponent() {
		
	}
	public EntityComponent(Entity e) {
		this.entity=entity;
	}
}
