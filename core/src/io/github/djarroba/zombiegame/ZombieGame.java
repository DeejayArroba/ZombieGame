package io.github.djarroba.zombiegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Box2D;
import io.github.djarroba.zombiegame.levels.LevelManager;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.screens.MainMenuScreen;

public class ZombieGame extends Game {

	public GameScreen gameScreen;
	public MainMenuScreen menuScreen;
	public AssetManager assets;
	public LevelManager levelManager;

	@Override
	public void create () {
		assets = new AssetManager();

		assets.load("textures/grass.png", Texture.class);
		assets.load("textures/player.png", Texture.class);
		assets.load("textures/weapons/pistol.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_0.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_1.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_2.png", Texture.class);
		assets.load("textures/weapons/pistol_firing_3.png", Texture.class);

		assets.load("sounds/pistol.wav", Sound.class);

		assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assets.load("maps/testmap.tmx", TiledMap.class);
		assets.load("maps/testmap2.tmx", TiledMap.class);

		assets.setLoader(Pixmap.class, new PixmapLoader(new InternalFileHandleResolver()));
		assets.load("textures/cursor.png", Texture.class);

		assets.finishLoading();

		levelManager = new LevelManager(this);

		Box2D.init();

		//gameScreen = new GameScreen(this);
		//setScreen(gameScreen);
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}
