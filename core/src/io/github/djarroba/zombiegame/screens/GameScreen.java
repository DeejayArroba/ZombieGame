package io.github.djarroba.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FillViewport;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.entities.Player;

public class GameScreen implements Screen {

	public static float WORLD_WIDTH = 16f;
	public static float WORLD_HEIGHT = 9f;

	private ZombieGame game;

	OrthographicCamera camera;
	FillViewport viewport;

	SpriteBatch batch;
	Player player;



	public GameScreen(ZombieGame game) {
		this.game = game;

		camera = new OrthographicCamera();

		viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

		batch = new SpriteBatch();
		player = new Player();
	}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		player.rotate(30*delta);

		batch.begin();

		player.draw(batch);

		batch.end();
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
