package io.github.djarroba.zombiegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class WeaponComponent implements Component {

	public String name;

	public float attackDelay;
	public ArrayList<Texture> attackingTextures;
	public Texture weaponTexture;
	public Sound attackSound;

	public float timeFromLastAttack = 0;
	public boolean justAttacked = false;

	public WeaponComponent(String name, Texture weaponTexture, Sound attackSound) {
		this.name = name;
		this.weaponTexture = weaponTexture;
		this.attackSound = attackSound;
		attackingTextures = new ArrayList<Texture>();
	}

}
