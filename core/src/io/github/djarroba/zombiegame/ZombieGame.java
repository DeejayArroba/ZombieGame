package io.github.djarroba.zombiegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import io.github.djarroba.zombiegame.screens.GameScreen;

public class ZombieGame extends Game {

	public GameScreen gameScreen;
	public AssetManager assets;

	@Override
	public void create () {
		assets = new AssetManager();

		assets.load("textures/cursor.png", Texture.class);
		assets.load("textures/grass.png", Texture.class);
		assets.load("textures/test.png", Texture.class);
		assets.load("textures/weapons/pistol.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_0.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_1.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_2.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_3.png", Texture.class);

		assets.load("sounds/pistol.wav", Sound.class);

		assets.finishLoading();

		Box2D.init();

		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}
