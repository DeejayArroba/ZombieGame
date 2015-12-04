package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;

public class Pistol extends Weapon {

	public Pistol() {
		super();


	}

	@Override
	public void setup() {
		attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pistol.wav"));
		attackDelay = 0.2f;
	}

	@Override
	public void attack() {

	}
}
