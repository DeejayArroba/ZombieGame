package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import io.github.djarroba.zombiegame.entities.Player;

public class Pistol extends Weapon {

	public Pistol(Player player) {
		super("Pistol",
				player,
				player.screen.game.assets.get("textures/weapons/pistol.png", Texture.class),
				player.screen.game.assets.get("sounds/pistol.wav", Sound.class));

		attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pistol.wav"));
		attackDelay = 0.1f;

		attackingTextures.add(new Texture("textures/weapons/pistol_firing_1.png"));
		attackingTextures.add(new Texture("textures/weapons/pistol_firing_2.png"));
		attackingTextures.add(new Texture("textures/weapons/pistol_firing_3.png"));

	}

	@Override
	public void draw() {
		super.draw();
	}

	@Override
	public void attack() {
		super.attack();
	}
}
