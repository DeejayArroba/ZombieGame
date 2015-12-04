package io.github.djarroba.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
	Sprite background;

	public World world;
	Box2DDebugRenderer debugRenderer;

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
		player = new Player(this);
		background = new Sprite(game.assets.get("textures/grass.png", Texture.class), 640, 400);
		background.setSize(background.getWidth() / Units.PPU, background.getHeight() / Units.PPU);
		background.setPosition(-WORLD_WIDTH/2, -WORLD_HEIGHT/2);
	}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(1/60f, 6, 2);

		camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		background.draw(batch);

		player.update();

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

    }
}
