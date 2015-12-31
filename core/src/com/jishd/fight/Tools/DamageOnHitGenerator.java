package com.jishd.fight.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.jishd.fight.FightGame;
import com.jishd.fight.Sprites.Classes.MercenaryModel;

public class DamageOnHitGenerator {
    private MercenaryModel mercenaryModel;
    private float x, y, lifeTimer, damage;
    private boolean setToRemove;
    private boolean removed;
    private BitmapFont font;
    Vector2 generateRandomMomentum;

    public DamageOnHitGenerator(MercenaryModel mercenaryModel, float damage){
        this.mercenaryModel = mercenaryModel;
        this.damage = damage;
        this.x = mercenaryModel.getX() * FightGame.PPM - (FightGame.V_WIDTH / 2) + (mercenaryModel.getWidth() * FightGame.PPM / 2);
        this.y = mercenaryModel.getY() * FightGame.PPM - (FightGame.V_HEIGHT / 2) + mercenaryModel.getHeight() * FightGame.PPM;
        lifeTimer = 1.2f;
        setToRemove = false;
        generateRandomMomentum = new Vector2((((float) Math.random() - 0.5f) * 150),(float) (50f + (Math.random() * 70f))) ;
        font = new BitmapFont(Gdx.files.internal("DamageFont.fnt"),Gdx.files.internal("DamageFont_0.tga"),false);
    }

    public void update(float dt){
        x += generateRandomMomentum.x * dt;
        y += generateRandomMomentum.y * dt;
        generateRandomMomentum.y -= 200 * dt;
        lifeTimer -= dt;
        if(lifeTimer <= 0 ){
            setToRemove = true;
        }
    }

    public float getLifeTimer(){
        return lifeTimer;
    }

    public void draw(Batch batch){
        if(!setToRemove) {
            font.setColor(Color.RED);
            int drawDamage = (int) damage;
            font.draw(batch, "" + drawDamage, x, y);
        }
    }
}
