package io.github.djarroba.zombiegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {

	public Player() {
		super(new Texture("test.png"), 16, 16);
		setSize(1, 1);
		setOrigin(getWidth()/2, getHeight()/2);

	}

}
