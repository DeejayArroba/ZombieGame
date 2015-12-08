package io.github.djarroba.zombiegame.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.units.Units;

public class Pistol extends Weapon {

	public Pistol(Player player) {
		super("Pistol",
				player,
				player.screen.game.assets.get("textures/weapons/pistol/pistol.png", Texture.class),
				player.screen.game.assets.get("sounds/pistol.wav", Sound.class),
				new Vector2(0, 8/Units.PPU));

		attackDelay = 0.25f;

		attackingTextures.add(player.screen.game.assets.get("textures/weapons/pistol/pistol_firing_0.png", Texture.class));
		attackingTextures.add(player.screen.game.assets.get("textures/weapons/pistol/pistol_firing_1.png", Texture.class));
		attackingTextures.add(player.screen.game.assets.get("textures/weapons/pistol/pistol_firing_2.png", Texture.class));

	}

	@Override
	public void attack() {
		super.attack();
	}

}
