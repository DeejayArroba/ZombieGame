package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public abstract class Weapon {

	float attackDelay;
	ArrayList<Texture> attackingTextures;
	Texture weaponTexture;
	Sound attackSound;
	Sprite sprite;

	float timeFromLastAttack;
	boolean justAttacked = false;

	public Weapon() {
		attackingTextures = new ArrayList<Texture>();
		setup();
	}

	abstract public void setup();

	public boolean canAttack() {
		return timeFromLastAttack >= attackDelay;
	}

	public void update() {
		Texture currentTexture = null;

		timeFromLastAttack += Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched()) {
			if(canAttack())
				attack();
		} else {
			if(justAttacked) justAttacked = false;
		}

		if(timeFromLastAttack == 0) {
			if(canAttack()) {
				currentTexture = weaponTexture;
			} else {
				// Animation
				float animationProgress = timeFromLastAttack / attackDelay;
				currentTexture = attackingTextures.get((int) ((attackingTextures.size()-1)*animationProgress));
			}
		}

		sprite.setTexture(currentTexture);
	}

	public void attack() {
		timeFromLastAttack = 0;
		justAttacked = true;
	}

}
