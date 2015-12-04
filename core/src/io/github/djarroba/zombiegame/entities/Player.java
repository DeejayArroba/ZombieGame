package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.units.Units;
import io.github.djarroba.zombiegame.weapons.Pistol;
import io.github.djarroba.zombiegame.weapons.Weapon;

public class Player extends Sprite {

	public GameScreen screen;
	Weapon primaryWeapon;
	Body body;
	float speed = 2f;
    boolean isSprinting;
    Vector2 sprintSpeed = new Vector2(2, 2);
    int maxStamina;
    float stamina;
    float staminaRegen;
    float runCost;

	public Player(GameScreen screen, Vector2 startPos) {
		super(screen.game.assets.get("textures/test.png", Texture.class), 16, 16);
		this.screen = screen;

		setSize(getWidth() / Units.PPU, getHeight() / Units.PPU);
		setOrigin(getWidth()/2, getHeight()/2);

		createBody(startPos);

        maxStamina = 100;
        stamina = maxStamina;
        runCost = 15;
        staminaRegen = 20;

        primaryWeapon = new Pistol(this);
	}

	private void createBody(Vector2 startPos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(startPos);

		body = screen.world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();

		CircleShape shape = new CircleShape();
		shape.setRadius(getWidth()/2);

		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);
	}

	public void update() {
		calculateRotation();
		movement();

		// Update position
		setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);

		primaryWeapon.update();
	}

	public void draw() {
		draw(screen.batch);
		primaryWeapon.draw();
	}

	private void movement() {
		Vector2 finalVelocity = new Vector2();

		if(Gdx.input.isKeyPressed(Input.Keys.D))
			finalVelocity.x += speed;
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			finalVelocity.x -= speed;
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			finalVelocity.y += speed;
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			finalVelocity.y -= speed;

        this.isSprinting = false;
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if (stamina > runCost * deltaTime) {
                stamina -= runCost * deltaTime;
                finalVelocity.scl(sprintSpeed);
                this.isSprinting = true;
            }
        } else {
            if (stamina < maxStamina) stamina += staminaRegen * deltaTime;
            //This is make stamina to NOT replenish until shift is released,
            //that's on purpose don't know if it's right.
        }
        //System.out.println(stamina); //Debug only
        body.setLinearVelocity(finalVelocity);
	}

	private void calculateRotation() {
		Vector3 mousePos = screen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 playerPos = new Vector3(getX()+getWidth()/2, getY()+getHeight()/2, 0);

		Vector3 vectorToTarget = new Vector3(mousePos.x - playerPos.x, mousePos.y - playerPos.y, 0);

		double angle = Math.toDegrees(Math.atan2(vectorToTarget.y, vectorToTarget.x));

		body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.toRadians(angle));
		setRotation((float) angle);
	}

	public void teleport(float x, float y) {
		body.getPosition().set(x, y);
	}

}
