package com.jishd.fight.Tools;
//need to fade healthbars out when players die

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.Sprites.Classes.MercenaryModel;


public class HealthAndManaBarCreator {
    private Sprite healthBarFull, healthBarEmpty, manaBarFull, manaBarEmpty;
    //private Label healthLabel, manaLabel;
    private MercenaryModel mercenaryModel;

    public HealthAndManaBarCreator(MercenaryModel mercenaryModel) {
        this.mercenaryModel = mercenaryModel;
        healthBarFull = new Sprite(new TextureRegion(mercenaryModel.getPlayScreen().getAtlas().findRegion("HealthandManaBars"), 40, 0, 40, 10));
        healthBarEmpty = new Sprite(new TextureRegion(mercenaryModel.getPlayScreen().getAtlas().findRegion("HealthandManaBars"), 0, 0, 40, 10));
        manaBarFull = new Sprite(new TextureRegion(mercenaryModel.getPlayScreen().getAtlas().findRegion("HealthandManaBars"), 120, 0, 40, 10));
        manaBarEmpty = new Sprite(new TextureRegion(mercenaryModel.getPlayScreen().getAtlas().findRegion("HealthandManaBars"), 0, 0, 40, 10));


        //healthLabel = new Label(String.format("%5d", mercenaryModel.getCurrentHealth()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //manaLabel = new Label(String.format("%5d", mercenaryModel.getCurrentMana()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        //setting the position - need to figure out why it looks slightly off center?
        healthBarFull.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40 / FightGame.PPM, 6 / FightGame.PPM);
        healthBarEmpty.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40 / FightGame.PPM, 6 / FightGame.PPM);
        manaBarFull.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40 / FightGame.PPM, 6 / FightGame.PPM);
        manaBarEmpty.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40 / FightGame.PPM, 6 / FightGame.PPM);
        // healthLabel.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40 / FightGame.PPM, 10 / FightGame.PPM);
        // manaLabel.setBounds(mercenaryModel.getX() + mercenaryModel.getWidth() / 2, mercenaryModel.getY() + 20 / FightGame.PPM, 40/ FightGame.PPM, 10/ FightGame.PPM);
        healthBarFull.setOrigin(0, 0);
        update();
    }

    public void update() {
        healthBarFull.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - healthBarFull.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 11 / FightGame.PPM);
        healthBarEmpty.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - healthBarEmpty.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 11 / FightGame.PPM);
        manaBarFull.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - manaBarFull.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 4 / FightGame.PPM);
        manaBarEmpty.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - manaBarEmpty.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 4 / FightGame.PPM);
        // healthLabel.setText(String.format("%5d", mercenaryModel.getCurrentHealth()));
        //manaLabel.setText(String.format("%5d", mercenaryModel.getCurrentMana()));
        // healthLabel.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - healthBarEmpty.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 20 / FightGame.PPM);
        // manaLabel.setPosition(mercenaryModel.getX() + mercenaryModel.getWidth() / 2 - manaBarEmpty.getWidth() / 2, mercenaryModel.getY() + mercenaryModel.getHeight() + 9 / FightGame.PPM);
        healthBarFull.setScale((mercenaryModel.getCurrentHealth()) / (mercenaryModel.getMercenary().getHealthMult()), 1);
        //manaBarFull.setSize( (40) * (mercenaryModel.getHealth() / mercenaryModel.getMercenary().getHealthMult()) / FightGame.PPM, 5/ FightGame.PPM);

    }

    public void draw(Batch batch) {
        healthBarEmpty.draw(batch);
        healthBarFull.draw(batch);
        manaBarEmpty.draw(batch);
        manaBarFull.draw(batch);
        // healthLabel.draw(batch, 1.0f);
        //manaLabel.draw(batch, 1.0f);


    }

}
