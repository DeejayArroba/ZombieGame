package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.entities.Entity;
import io.github.djarroba.zombiegame.entities.EntityManager;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.units.Units;

import java.util.ArrayList;

public class Weapon implements Entity {

	public Player player;
	ZombieGame game;

	public String name;

	public float attackDelay;
	public ArrayList<Texture> attackingTextures;
	public Texture weaponTexture;
	public Sound attackSound;

	public float timeFromLastAttack = 0;
	public boolean justAttacked = false;
	Sprite sprite;

	public Weapon(String name, Player player, Texture weaponTexture, Sound attackSound) {
		this.name = name;
		this.player = player;
		this.weaponTexture = weaponTexture;
		this.attackSound = attackSound;

		this.game = player.screen.game;
		attackingTextures = new ArrayList<Texture>();

		sprite = new Sprite(weaponTexture, weaponTexture.getWidth(), weaponTexture.getHeight());
		sprite.setSize(sprite.getWidth() / Units.PPU, sprite.getHeight() / Units.PPU);
		sprite.setOrigin(sprite.getWidth()/2 - player.sprite.getWidth(), sprite.getHeight()/2);
	}

	public boolean canAttack() {
		return timeFromLastAttack >= attackDelay;
	}

	public void attack() {
		timeFromLastAttack = 0;
		justAttacked = true;
		attackSound.play();
	}

	@Override
	public void onAdded(EntityManager entityManager) {

	}

	@Override
	public void onRemove(EntityManager entityManager) {

	}

	@Override
	public void update(float delta) {
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
		sprite.setPosition(player.body.getPosition().x+1-sprite.getWidth()/2,
				player.body.getPosition().y-sprite.getHeight()/2);
		sprite.setRotation((float) Math.toDegrees(player.body.getAngle()));
	}

	@Override
	public void drawUpdate(float delta, SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void lateUpdate(float delta) {

	}

}
