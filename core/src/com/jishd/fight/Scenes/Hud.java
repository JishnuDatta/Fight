package com.jishd.fight.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jishd.fight.FightGame;
import com.jishd.fight.Sprites.Classes.CharacterModel;

import java.util.ArrayList;

/**
 * Created by Jishnu on 12/17/2015.
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private ArrayList<CharacterModel> models;

    private ArrayList<Label> players;
    private ArrayList<Label> healths;
    private ArrayList<Label> manas;

    public Hud(SpriteBatch sb, ArrayList<CharacterModel> cm) {

        viewport = new FitViewport(FightGame.V_WIDTH, FightGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        models = cm;
        players = new ArrayList<Label>();
        healths = new ArrayList<Label>();
        manas = new ArrayList<Label>();


        for (CharacterModel model : cm) {
            players.add(new Label(model.getCharacter().toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
            healths.add(new Label(String.format("%05d", model.getCurrentHealth()), new Label.LabelStyle(new BitmapFont(), Color.RED)));
            manas.add(new Label(String.format("%05d", model.getCurrentMana()), new Label.LabelStyle(new BitmapFont(), Color.CYAN)));
        }

        for (Label label : players) {
            table.add(label).expandX().padTop(15);
        }
        table.row();
        for (Label label : healths) {
            table.add(label).expandX();
        }
        table.row();
        for (Label label : manas) {
            table.add(label).expandX();

        }
        stage.addActor(table);
    }

    public void update() {
        for (int i = 0; i < healths.size(); i++) {
            healths.get(i).setText(String.format("%5d", models.get(i).getCurrentHealth()));
        }
        for (int i = 0; i < healths.size(); i++) {
            manas.get(i).setText(String.format("%5d", models.get(i).getCurrentHealth()));
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}