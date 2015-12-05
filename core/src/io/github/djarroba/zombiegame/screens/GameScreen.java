package io.github.djarroba.zombiegame.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.components.SpriteComponent;
import io.github.djarroba.zombiegame.components.TransformComponent;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.systems.PlayerSystem;
import io.github.djarroba.zombiegame.systems.PositionSystem;
import io.github.djarroba.zombiegame.systems.WeaponSystem;
import io.github.djarroba.zombiegame.units.Units;

public class GameScreen implements Screen {

	public static float WORLD_WIDTH = 16f;
	public static float WORLD_HEIGHT = 9f;

	public ZombieGame game;

	public OrthographicCamera camera;
	FillViewport viewport;

	public SpriteBatch batch;
	Player player;

	public World world;
	Box2DDebugRenderer debugRenderer;

	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;

	Body worldBody;

	public Engine engine;

	public GameScreen(ZombieGame game) {
		this.game = game;

		// Set the cursor
		Texture cursorTexture = game.assets.get("textures/cursor.png", Texture.class);
		cursorTexture.getTextureData().prepare();
		Pixmap cursorPixmap = cursorTexture.getTextureData().consumePixmap();
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, cursorPixmap.getWidth()/2, cursorPixmap.getHeight()/2));

		camera = new OrthographicCamera();

		viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

		engine = new Engine();

		// Create the world
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		loadMap(game.assets.get("maps/testmap.tmx", TiledMap.class));
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1/Units.PPU);

		float spawnX = Float.parseFloat(map.getProperties().get("spawn_x", String.class));
		float spawnY = Float.parseFloat(map.getProperties().get("spawn_y", String.class));

		Vector2 playerStartPos = new Vector2(spawnX, spawnY);

		player = new Player(this, playerStartPos);
		engine.addEntity(player);

		engine.addSystem(new PositionSystem());
		engine.addSystem(new PlayerSystem());
		engine.addSystem(new WeaponSystem());
	}

	private void loadMap(TiledMap map) {
		this.map = map;
		// Everything here is in world units
		int mapWidth = map.getProperties().get("width", Integer.class);
		int mapHeight = map.getProperties().get("height", Integer.class);
		int tileWidth = map.getProperties().get("tilewidth", Integer.class);
		int tileHeight = map.getProperties().get("tileheight", Integer.class);

		BodyDef worldBodyDef = new BodyDef();
		worldBodyDef.type = BodyDef.BodyType.StaticBody;
		worldBodyDef.position.set(0, 0);

		worldBody = world.createBody(worldBodyDef);

		for (MapObject mapObject : map.getLayers().get("Collision").getObjects()) {
			// Everything here is in world units
			float objectX = mapObject.getProperties().get("x", Float.class) / tileWidth;
			float objectY = mapObject.getProperties().get("y", Float.class) / tileWidth;
			float objectWidth = mapObject.getProperties().get("width", Float.class) / tileWidth;
			float objectHeight = mapObject.getProperties().get("height", Float.class) / tileWidth;

			FixtureDef fixtureDef = new FixtureDef();

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(objectWidth/2, objectHeight/2, new Vector2(objectX + objectWidth/2, objectY + objectHeight/2), 0);

			fixtureDef.shape = shape;

			worldBody.createFixture(fixtureDef);

			shape.dispose();
		}
	}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		engine.update(delta);

		TransformComponent playerTransform = player.transformComponent;
		camera.position.set(Math.round(playerTransform.getX()*1000)/1000f, Math.round(playerTransform.getY()*1000)/1000f, 0);
		camera.update();

		mapRenderer.setView(camera);
		// Render background layer
		mapRenderer.render(new int[]{0});

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		for(Entity entity : engine.getEntitiesFor(Family.all(SpriteComponent.class).get())) {
			SpriteComponent spriteComponent = entity.getComponent(SpriteComponent.class);
			spriteComponent.sprite.draw(batch);
		}

		batch.end();

		// Render foreground layer
		mapRenderer.render(new int[]{1});

		//debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
		viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
		batch.dispose();
		world.dispose();
    }
}
