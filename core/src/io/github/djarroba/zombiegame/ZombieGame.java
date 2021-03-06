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
import io.github.djarroba.zombiegame.levels.Level;
import io.github.djarroba.zombiegame.levels.LevelManager;
import io.github.djarroba.zombiegame.screens.GameScreen;
import io.github.djarroba.zombiegame.screens.MainMenuScreen;

import java.io.File;

public class ZombieGame extends Game {

	public GameScreen gameScreen;
	public MainMenuScreen menuScreen;
	public AssetManager assets;
	public LevelManager levelManager;

	@Override
	public void create () {
		levelManager = new LevelManager(this);
		File levelsFolder = new File("maps/");
		assets = new AssetManager();

		assets.load("textures/grass.png", Texture.class);
		assets.load("textures/player.png", Texture.class);
		assets.load("textures/weapons/pistol/pistol.png", Texture.class);
		assets.load("textures/weapons/pistol/pistol_firing_0.png", Texture.class);
		assets.load("textures/weapons/pistol/pistol_firing_1.png", Texture.class);
		assets.load("textures/weapons/pistol/pistol_firing_2.png", Texture.class);

		assets.load("sounds/pistol.wav", Sound.class);

		assets.setLoader(Pixmap.class, new PixmapLoader(new InternalFileHandleResolver()));
		assets.load("textures/cursor.png", Texture.class);

		assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		// Load the level assets
		for(File file : levelsFolder.listFiles()) {
			if(file.getName().endsWith(".tmx")) {
				assets.load(levelsFolder.getName() + "/" + file.getName(), TiledMap.class);
			}
		}

		assets.finishLoading();

		// Load the actual levels into the LevelManager
		for(File file : levelsFolder.listFiles()) {
			if(file.getName().endsWith(".tmx")) {
				levelManager.add(new Level(assets.get(levelsFolder.getName() + "/" + file.getName(), TiledMap.class)));
			}
		}

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
