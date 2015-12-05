package io.github.djarroba.zombiegame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import io.github.djarroba.zombiegame.components.BodyComponent;
import io.github.djarroba.zombiegame.components.SpriteComponent;
import io.github.djarroba.zombiegame.components.TransformComponent;

public class PositionSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, BodyComponent.class, TransformComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for(Entity e : entities) {
			TransformComponent transform = e.getComponent(TransformComponent.class);
			BodyComponent bodyComponent = e.getComponent(BodyComponent.class);
			SpriteComponent spriteComponent = e.getComponent(SpriteComponent.class);

			Vector2 bodyPos = bodyComponent.body.getPosition();
			float bodyRotation = bodyComponent.body.getAngle(); //radians

			transform.setPosition(bodyPos);
			transform.setRotation((float) Math.toDegrees(bodyRotation));

			spriteComponent.sprite.setPosition(transform.getX()-spriteComponent.sprite.getWidth()/2,
					transform.getY()-spriteComponent.sprite.getHeight()/2);
			spriteComponent.sprite.setRotation(transform.getRotation());

		}
	}
}
