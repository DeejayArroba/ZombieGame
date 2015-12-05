package io.github.djarroba.zombiegame.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.djarroba.zombiegame.components.BodyComponent;
import io.github.djarroba.zombiegame.components.SpriteComponent;
import io.github.djarroba.zombiegame.components.TransformComponent;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.units.Units;
import io.github.djarroba.zombiegame.weapons.Pistol;
import io.github.djarroba.zombiegame.weapons.Weapon;

public class Player extends Entity {

	public GameScreen screen;
	public Weapon primaryWeapon;

	public float speed = 1f;
    public float sprintMultiplier = 1.5f;

	public boolean isSprinting;

    public int maxStamina;
    public float stamina;
    public float staminaRegen;
    public float runCost;

	public TransformComponent transformComponent;
	public BodyComponent bodyComponent;
	public SpriteComponent spriteComponent;


	public Player(GameScreen screen, Vector2 startPos) {
		super();
		this.screen = screen;

		transformComponent = new TransformComponent(this);
		add(transformComponent);

		spriteComponent = new SpriteComponent(new Sprite(screen.game.assets.get("textures/player.png", Texture.class), 16, 16));
		spriteComponent.sprite.setSize(spriteComponent.sprite.getWidth() / Units.PPU, spriteComponent.sprite.getHeight() / Units.PPU);
		spriteComponent.sprite.setOrigin(spriteComponent.sprite.getWidth()/2, spriteComponent.sprite.getHeight()/2);
		spriteComponent.sprite.setPosition(startPos.x, startPos.y);
		add(spriteComponent);

		bodyComponent = new BodyComponent(createBody(startPos));
		add(bodyComponent);

        maxStamina = 100;
        stamina = maxStamina;
        runCost = 15;
        staminaRegen = 20;

        primaryWeapon = new Pistol(this);
		screen.engine.addEntity(primaryWeapon);
	}

	private Body createBody(Vector2 startPos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(startPos);

		Body body = screen.world.createBody(bodyDef);
		body.setFixedRotation(true);

		FixtureDef fixtureDef = new FixtureDef();

		CircleShape shape = new CircleShape();
		shape.setRadius(spriteComponent.sprite.getWidth()/2);

		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);

		return body;
	}


}
