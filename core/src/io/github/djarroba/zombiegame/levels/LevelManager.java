package io.github.djarroba.zombiegame.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.djarroba.zombiegame.ZombieGame;

import java.util.ArrayList;

public class LevelManager {

	ArrayList<Level> levels;
	ZombieGame game;

	public LevelManager(ZombieGame game) {
		this.game = game;
		levels = new ArrayList<Level>();

		loadLevels();
	}

	private void add(Level level) {
		levels.add(level);
	}

	private void loadLevels() {
		add(new Level("Test level", game.assets.get("maps/testmap.tmx", TiledMap.class)));
		add(new Level("Test level 2", game.assets.get("maps/testmap2.tmx", TiledMap.class)));
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}
}
