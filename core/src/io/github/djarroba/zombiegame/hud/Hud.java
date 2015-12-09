package io.github.djarroba.zombiegame.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.djarroba.zombiegame.screens.GameScreen;

public class Hud {

	GameScreen screen;

	public FitViewport viewport;

	public Stage stage;
	Skin skin;

	Table table;
	Label staminaLabel;
	ProgressBar stamina;

	public Hud(GameScreen screen) {
		this.screen = screen;


		viewport = new FitViewport(16, 9, screen.camera);

		stage = new Stage();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		table = new Table();
		table.top().left();
		table.setFillParent(true);

		staminaLabel = new Label("Stamina", skin);
		staminaLabel.setFontScale(0.5f);

		table.add(staminaLabel).pad(10);

		stamina = new ProgressBar(0, 100, 1, false, skin);

		table.add(stamina).pad(10);

		stage.addActor(table);
	}

	public void update(float delta) {

		stamina.setValue(screen.player.stamina);

		viewport.apply();
		stage.act(delta);
		stage.draw();
	}

}
