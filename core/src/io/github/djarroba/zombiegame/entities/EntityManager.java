package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EntityManager {

	ArrayList<Entity> entities;

	public EntityManager() {
		entities = new ArrayList<Entity>();
	}

	public void update(float delta) {
		for(Entity entity : entities) {
			entity.update(delta);
		}
	}

	public void drawUpdate(float delta, SpriteBatch batch) {
		for(Entity entity : entities) {
			entity.drawUpdate(delta, batch);
		}
	}

	public void lateUpdate(float delta) {
		for(Entity entity : entities) {
			entity.lateUpdate(delta);
		}
	}

	public void add(Entity entity) {
		entities.add(entity);
		entity.onAdded(this);
	}

	public void remove(Entity entity) {
		entity.onRemove(this);
		entities.remove(entity);
	}

}
