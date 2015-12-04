package io.github.djarroba.zombiegame.screens;

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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.entities.Player;
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

		// Create the world
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		map = game.assets.get("maps/testmap.tmx", TiledMap.class);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1/Units.PPU);

		float spawnX = Float.parseFloat(map.getProperties().get("spawn_x", String.class));
		float spawnY = Float.parseFloat(map.getProperties().get("spawn_y", String.class));

		Vector2 playerStartPos = new Vector2(spawnX, spawnY);

		player = new Player(this, playerStartPos);

	}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		player.update();
		camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		player.draw();

		batch.end();

		debugRenderer.render(world, camera.combined);
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
