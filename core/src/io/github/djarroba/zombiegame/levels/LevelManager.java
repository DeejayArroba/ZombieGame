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
	}

	public void add(Level level) {
		levels.add(level);
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}
}
