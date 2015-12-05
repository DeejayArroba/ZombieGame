package io.github.djarroba.zombiegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {

	Vector2 position;
	float rotation;
	Entity entity;

	public TransformComponent(Entity entity) {
		this.position = new Vector2();
		this.rotation = 0f;
		this.entity = entity;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setPosition(Vector2 newPos) {
		position = newPos;

		BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);

		if(bodyComponent != null)
			bodyComponent.body.setTransform(newPos, bodyComponent.body.getAngle());
	}

	public void setRotation(float degrees) {
		rotation = degrees;

		BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);

		if(bodyComponent != null)
			bodyComponent.body.setTransform(bodyComponent.body.getPosition(), (float) Math.toRadians(degrees));
	}

}
