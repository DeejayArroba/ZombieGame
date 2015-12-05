package io.github.djarroba.zombiegame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.djarroba.zombiegame.components.BodyComponent;
import io.github.djarroba.zombiegame.components.SpriteComponent;
import io.github.djarroba.zombiegame.components.TransformComponent;
import io.github.djarroba.zombiegame.entities.Player;

public class PlayerSystem extends EntitySystem {

	Player player;

	@Override
	public void addedToEngine(Engine engine) {
		for(Entity e : engine.getEntities()) {
			if(e instanceof Player) {
				player = (Player) e;
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		TransformComponent transform = player.getComponent(TransformComponent.class);
		BodyComponent bodyComponent = player.getComponent(BodyComponent.class);
		SpriteComponent spriteComponent = player.getComponent(SpriteComponent.class);

		/*
		Rotate player towards mouse cursor
		 */
		Vector3 mousePos = player.screen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 playerPos = new Vector3(transform.getX(), transform.getY(), 0);

		Vector3 vectorToTarget = new Vector3(mousePos.x - playerPos.x, mousePos.y - playerPos.y, 0);

		double angle = Math.toDegrees(Math.atan2(vectorToTarget.y, vectorToTarget.x));

		bodyComponent.body.setTransform(bodyComponent.body.getPosition().x, bodyComponent.body.getPosition().y, (float) Math.toRadians(angle));
		transform.setRotation((float) angle);

		/*
		Move the player with the WASD keys
		 */
		Vector2 finalVelocity = new Vector2();

		if(Gdx.input.isKeyPressed(Input.Keys.D))
			finalVelocity.x += player.speed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			finalVelocity.x -= player.speed;
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			finalVelocity.y += player.speed;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			finalVelocity.y -= player.speed;

		player.isSprinting = false;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			if (player.stamina > player.runCost * deltaTime) {
				player.stamina -= player.runCost * deltaTime;
				finalVelocity.scl(player.sprintMultiplier);
				player.isSprinting = true;
			}
		} else {
			if (player.stamina < player.maxStamina) player.stamina += player.staminaRegen * deltaTime;
			//This makes stamina NOT replenish itself until shift is released
		}

		bodyComponent.body.setLinearVelocity(finalVelocity);
		bodyComponent.body.applyLinearImpulse(finalVelocity.x,
				finalVelocity.y,
				bodyComponent.body.getPosition().x,
				bodyComponent.body.getPosition().y,
				true);
	}
}
