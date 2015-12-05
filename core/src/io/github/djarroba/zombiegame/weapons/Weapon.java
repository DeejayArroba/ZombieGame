package io.github.djarroba.zombiegame.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.components.SpriteComponent;
import io.github.djarroba.zombiegame.components.WeaponComponent;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.units.Units;

public abstract class Weapon extends Entity {

	public Player player;
	ZombieGame game;

	public SpriteComponent spriteComponent;
	WeaponComponent weaponComponent;

	public Weapon(String name, Player player, Texture weaponTexture, Sound attackSound) {
		this.player = player;
		this.game = player.screen.game;

		Sprite sprite = new Sprite(weaponTexture, weaponTexture.getWidth(), weaponTexture.getHeight());
		sprite.setSize(sprite.getWidth() / Units.PPU, sprite.getHeight() / Units.PPU);
		sprite.setOrigin(sprite.getWidth()/2 - player.spriteComponent.sprite.getWidth(), sprite.getHeight()/2);
		spriteComponent = new SpriteComponent(sprite);
		add(spriteComponent);

		weaponComponent = new WeaponComponent(name, weaponTexture, attackSound);
		add(weaponComponent);
	}

	public boolean canAttack() {
		return weaponComponent.timeFromLastAttack >= weaponComponent.attackDelay;
	}

	public void attack() {
		weaponComponent.timeFromLastAttack = 0;
		weaponComponent.justAttacked = true;
		weaponComponent.attackSound.play();
	}

}
