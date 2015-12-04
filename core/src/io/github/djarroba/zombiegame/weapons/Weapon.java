package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.units.Units;

import java.util.ArrayList;

public abstract class Weapon {

	float attackDelay;
	ArrayList<Texture> attackingTextures;
	Texture weaponTexture;
	Sound attackSound;
	Sprite sprite;

	float timeFromLastAttack = 0;
	boolean justAttacked = false;

	Player player;
	ZombieGame game;

	String name;

	public Weapon(String name, Player player, Texture weaponTexture, Sound attackSound) {
		this.name = name;
		this.player = player;
		this.game = player.screen.game;
		this.weaponTexture = weaponTexture;
		this.attackSound = attackSound;
		attackingTextures = new ArrayList<Texture>();

		sprite = new Sprite(weaponTexture, weaponTexture.getWidth(), weaponTexture.getHeight());
		sprite.setSize(sprite.getWidth() / Units.PPU, sprite.getHeight() / Units.PPU);
		sprite.setOrigin(sprite.getWidth()/2 - player.getWidth(), sprite.getHeight()/2);
	}

	public boolean canAttack() {
		return timeFromLastAttack >= attackDelay;
	}

	public void update() {

		Texture currentTexture;

		timeFromLastAttack += Gdx.graphics.getDeltaTime();

		if(Gdx.input.justTouched()) {
			if(canAttack()) {
				attack();
			}
		} else {
			if(justAttacked) justAttacked = false;
		}

		if(canAttack()) {
			currentTexture = weaponTexture;
		} else {
			// Animation
			float animationProgress = (timeFromLastAttack / attackDelay) + 0.2f;
			currentTexture = attackingTextures.get((int)  ((attackingTextures.size()-1) * Math.min(1, animationProgress)));
		}

		sprite.setTexture(currentTexture);
		sprite.setPosition(player.getX()+1, player.getY());
		sprite.setRotation(player.getRotation());
		sprite.draw(game.gameScreen.batch);
	}

	public void attack() {
		timeFromLastAttack = 0;
		justAttacked = true;
		attackSound.play();
	}

}
