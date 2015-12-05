package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.units.Units;
import io.github.djarroba.zombiegame.weapons.Pistol;
import io.github.djarroba.zombiegame.weapons.Weapon;

public class Player implements io.github.djarroba.zombiegame.entities.Entity {

	public GameScreen screen;
	public Weapon primaryWeapon;

	public float speed = 1f;
    public float sprintMultiplier = 1.5f;

	public boolean isSprinting;

    public int maxStamina;
    public float stamina;
    public float staminaRegen;
    public float runCost;

	public Body body;
	public Sprite sprite;

	public Player(GameScreen screen, Vector2 startPos) {
		super();
		this.screen = screen;

		sprite = new Sprite(screen.game.assets.get("textures/player.png", Texture.class), 16, 16);
		sprite.setSize(sprite.getWidth() / Units.PPU, sprite.getHeight() / Units.PPU);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(startPos.x, startPos.y);

		body = createBody(startPos);

        maxStamina = 100;
        stamina = maxStamina;
        runCost = 15;
        staminaRegen = 20;

	}

	private Body createBody(Vector2 startPos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(startPos);

		Body body = screen.world.createBody(bodyDef);
		body.setFixedRotation(true);

		FixtureDef fixtureDef = new FixtureDef();

		CircleShape shape = new CircleShape();
		shape.setRadius(sprite.getWidth()/2);

		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);

		return body;
	}


	@Override
	public void onAdded(EntityManager entityManager) {
		primaryWeapon = new Pistol(this);
		screen.entityManager.add(primaryWeapon);
	}

	@Override
	public void onRemove(EntityManager entityManager) {

	}

	@Override
	public void update(float delta) {
		/*
		Rotate player towards mouse cursor
		 */
		Vector3 mousePos = screen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 playerPos = new Vector3(body.getPosition().x, body.getPosition().y, 0);

		Vector3 vectorToTarget = new Vector3(mousePos.x - playerPos.x, mousePos.y - playerPos.y, 0);

		double angle = Math.toDegrees(Math.atan2(vectorToTarget.y, vectorToTarget.x));

		body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.toRadians(angle));

		/*
		Move the player with the WASD keys
		 */
		Vector2 finalVelocity = new Vector2();

		if(Gdx.input.isKeyPressed(Input.Keys.D))
			finalVelocity.x += speed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			finalVelocity.x -= speed;
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			finalVelocity.y += speed;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			finalVelocity.y -= speed;

		isSprinting = false;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			if (stamina > runCost * delta) {
				stamina -= runCost * delta;
				finalVelocity.scl(sprintMultiplier);
				isSprinting = true;
			}
		} else {
			if (stamina < maxStamina) stamina += staminaRegen * delta;
			//This makes stamina NOT replenish itself until shift is released
		}

		body.setLinearVelocity(finalVelocity);
		body.applyLinearImpulse(finalVelocity.x,
				finalVelocity.y,
				body.getPosition().x,
				body.getPosition().y,
				true);
	}

	@Override
	public void drawUpdate(float delta, SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y-sprite.getHeight()/2);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		sprite.draw(batch);
	}

	@Override
	public void lateUpdate(float delta) {

	}
}
