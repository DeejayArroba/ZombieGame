package io.github.djarroba.zombiegame.levels;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Level {

	Body body;
	TiledMap map;
	Vector2 spawnPosition;
	String name;

	public Level(String name, TiledMap map) {
		this.name = name;
		this.map = map;
		this.spawnPosition = new Vector2(Float.parseFloat(map.getProperties().get("spawn_x", String.class)),
				Float.parseFloat(map.getProperties().get("spawn_y", String.class)));
	}

	public void load(World world) {
		int mapWidth = map.getProperties().get("width", Integer.class);
		int mapHeight = map.getProperties().get("height", Integer.class);
		int tileWidth = map.getProperties().get("tilewidth", Integer.class);
		int tileHeight = map.getProperties().get("tileheight", Integer.class);

		BodyDef worldBodyDef = new BodyDef();
		worldBodyDef.type = BodyDef.BodyType.StaticBody;
		worldBodyDef.position.set(0, 0);

		body = world.createBody(worldBodyDef);

		for (MapObject mapObject : map.getLayers().get("Collision").getObjects()) {
			if(mapObject instanceof RectangleMapObject) {
				// Everything here is in world units
				float objectX = mapObject.getProperties().get("x", Float.class) / tileWidth;
				float objectY = mapObject.getProperties().get("y", Float.class) / tileWidth;
				float objectWidth = mapObject.getProperties().get("width", Float.class) / tileWidth;
				float objectHeight = mapObject.getProperties().get("height", Float.class) / tileWidth;

				FixtureDef fixtureDef = new FixtureDef();

				PolygonShape shape = new PolygonShape();
				shape.setAsBox(objectWidth/2, objectHeight/2, new Vector2(objectX + objectWidth/2, objectY + objectHeight/2), 0);

				fixtureDef.shape = shape;

				body.createFixture(fixtureDef);

				shape.dispose();
			}
		}
	}

	public TiledMap getMap() {
		return map;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}

	@Override
	public String toString() {
		return name;
	}
}
