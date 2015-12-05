package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Entity {

	void onAdded(EntityManager entityManager);
	void onRemove(EntityManager entityManager);

	void update(float delta);
	void drawUpdate(float delta, SpriteBatch batch);
	void lateUpdate(float delta);

}