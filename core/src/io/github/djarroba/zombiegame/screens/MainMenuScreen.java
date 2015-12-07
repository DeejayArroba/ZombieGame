package io.github.djarroba.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.djarroba.zombiegame.ZombieGame;
import io.github.djarroba.zombiegame.levels.Level;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {

	OrthographicCamera camera;
	Viewport viewport;

	Stage stage;
	Skin skin;
	Table baseTable;

	public MainMenuScreen(final ZombieGame game) {
		camera = new OrthographicCamera();

		viewport = new ScreenViewport(camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

		stage = new Stage(viewport);
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		baseTable = new Table();
		baseTable.setFillParent(true);

		stage.addActor(baseTable);

		Label label = new Label("Select map", skin);
		baseTable.add(label).center().height(75).row();

		final List list = new List(skin);
		list.setItems(game.levelManager.getLevels().toArray());
		ScrollPane scrollPane = new ScrollPane(list, skin);
		baseTable.add(scrollPane).expand().fill().row();

		TextButton textButton = new TextButton("Play", skin);
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game, (Level) list.getSelected()));
			}
		});
		baseTable.add(textButton).height(100).fillX();

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
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
