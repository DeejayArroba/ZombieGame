package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

	public float timeFromLastAttack = 20;
	public boolean justAttacked = false;
	Sprite sprite;

	Vector2 handlePos;

	public Weapon(String name, Player player, Texture weaponTexture, Sound attackSound, Vector2 handlePos) {
		this.name = name;
		this.player = player;
		this.weaponTexture = weaponTexture;
		this.attackSound = attackSound;
		this.handlePos = handlePos;

		this.game = player.screen.game;
		attackingTextures = new ArrayList<Texture>();

		sprite = new Sprite(weaponTexture, weaponTexture.getWidth(), weaponTexture.getHeight());
		sprite.setSize(sprite.getWidth() / Units.PPU, sprite.getHeight() / Units.PPU);
		sprite.setOrigin(sprite.getWidth()/2 - player.sprite.getWidth(), sprite.getHeight()/2-player.weaponOffset.y);
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

		if(Gdx.input.isTouched()) {
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
			float animationProgress = (timeFromLastAttack / attackDelay);
			currentTexture = attackingTextures.get((int) (attackingTextures.size() * animationProgress));
		}

		sprite.setTexture(currentTexture);
		sprite.setPosition(player.body.getPosition().x+player.weaponOffset.x-sprite.getWidth()/2,
				player.body.getPosition().y+player.weaponOffset.y-handlePos.y);
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
