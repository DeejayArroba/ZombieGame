package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.units.Units;
import io.github.djarroba.zombiegame.weapons.Pistol;
import io.github.djarroba.zombiegame.weapons.Weapon;

public class Player extends Sprite {

	public GameScreen screen;
	Weapon primaryWeapon;

	public Player(GameScreen screen) {
		super(screen.game.assets.get("textures/test.png", Texture.class), 16, 16);
		this.screen = screen;

		setSize(getWidth() / Units.PPU, getHeight() / Units.PPU);
		setOrigin(getWidth()/2, getHeight()/2);

		primaryWeapon = new Pistol(this);
	}

	public void update() {
		calculateRotation();
		movement();
		primaryWeapon.update();
		draw();
	}

	private void draw() {
		draw(screen.batch);
	}

	private void movement() {
		float delta = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.D))
			setPosition(getX()+delta, getY());
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			setPosition(getX()-delta, getY());
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			setPosition(getX(), getY()+delta);
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			setPosition(getX(), getY()-delta);
	}

	private void calculateRotation() {
		Vector3 mousePos = screen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 playerPos = new Vector3(getX()+getWidth()/2, getY()+getHeight()/2, 0);

		Vector3 vectorToTarget = new Vector3(mousePos.x - playerPos.x, mousePos.y - playerPos.y, 0);

		double angle = Math.toDegrees(Math.atan2(vectorToTarget.y, vectorToTarget.x));

		setRotation((float) angle);
	}

}
