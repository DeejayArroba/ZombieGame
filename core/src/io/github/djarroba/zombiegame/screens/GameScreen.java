package io.github.djarroba.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.entities.EntityManager;
import io.github.djarroba.zombiegame.entities.Player;
import io.github.djarroba.zombiegame.levels.Level;
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

	OrthogonalTiledMapRenderer mapRenderer;

	Level level;

	public EntityManager entityManager;

	public GameScreen(ZombieGame game, Level level) {
		this.game = game;
		this.level = level;

		Gdx.input.setInputProcessor(null);

		// Set the cursor
		Texture cursorTexture = game.assets.get("textures/cursor.png", Texture.class);
		cursorTexture.getTextureData().prepare();
		Pixmap cursorPixmap = cursorTexture.getTextureData().consumePixmap();
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorPixmap, cursorPixmap.getWidth()/2, cursorPixmap.getHeight()/2));

		camera = new OrthographicCamera();

		viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

		entityManager = new EntityManager();

		// Create the world
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		level.load(world);
		mapRenderer = new OrthogonalTiledMapRenderer(level.getMap(), 1/Units.PPU);

		Vector2 playerStartPos = new Vector2(level.getSpawnPosition().x, level.getSpawnPosition().y);

		player = new Player(this, playerStartPos);
		entityManager.add(player);
	}

	@Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		entityManager.update(delta);

		camera.position.set(Math.round(player.body.getPosition().x*1000)/1000f, Math.round(player.body.getPosition().y*1000)/1000f, 0);
		camera.update();

		mapRenderer.setView(camera);
		// Render back layer
		mapRenderer.render(new int[]{0});
		// Render mid layer
		mapRenderer.render(new int[]{1});

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		entityManager.drawUpdate(delta, batch);

		batch.end();

		// Render foreground layer
		mapRenderer.render(new int[]{2});
		entityManager.lateUpdate(delta);

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
