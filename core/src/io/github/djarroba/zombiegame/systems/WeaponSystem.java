package io.github.djarroba.zombiegame.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.djarroba.zombiegame.components.WeaponComponent;
import io.github.djarroba.zombiegame.weapons.Weapon;

public class WeaponSystem extends EntitySystem {

	ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(WeaponComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for(Entity entity : entities) {
			Weapon weapon = (Weapon) entity;
			WeaponComponent weaponComponent = entity.getComponent(WeaponComponent.class);

			Texture currentTexture;

			weaponComponent.timeFromLastAttack += Gdx.graphics.getDeltaTime();

			if(Gdx.input.justTouched()) {
				if(weapon.canAttack()) {
					weapon.attack();
				}
			} else {
				if(weaponComponent.justAttacked) weaponComponent.justAttacked = false;
			}

			if(weapon.canAttack()) {
				currentTexture = weaponComponent.weaponTexture;
			} else {
				// Animation
				float animationProgress = (weaponComponent.timeFromLastAttack / weaponComponent.attackDelay) + 0.2f;
				currentTexture = weaponComponent.attackingTextures.get((int)  ((weaponComponent.attackingTextures.size()-1) * Math.min(1, animationProgress)));
			}

			weapon.spriteComponent.sprite.setTexture(currentTexture);
			weapon.spriteComponent.sprite.setPosition(weapon.player.transformComponent.getX()+1-weapon.spriteComponent.sprite.getWidth()/2,
					weapon.player.transformComponent.getY()-weapon.spriteComponent.sprite.getHeight()/2);
			weapon.spriteComponent.sprite.setRotation(weapon.player.transformComponent.getRotation());
		}
	}
}
