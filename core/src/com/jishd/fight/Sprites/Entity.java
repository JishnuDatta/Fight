package com.jishd.fight.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jishd.fight.FightGame;
import com.jishd.fight.Screens.PlayScreen;

//Disposable???
public abstract class Entity extends Sprite {
    protected PlayScreen playScreen;
    protected Body entityBody;
    protected World world;
    public boolean deleteEntity;
    protected TextureRegion completeEntityRegion;
    protected TextureRegion currentRegion;


    public Entity(PlayScreen playScreen, int positionX, int positionY, TextureRegion textureRegion) {
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.completeEntityRegion = textureRegion;
        deleteEntity = false;
        currentRegion = getTextureRegion(textureRegion);
        setRegion(currentRegion);
        createEntityBody(positionX, positionY);
        setSize(currentRegion.getRegionWidth() / FightGame.PPM, currentRegion.getRegionHeight() / FightGame.PPM);
        addEntity(playScreen);
    }

    public abstract void createEntityBody(int positionX, int positionY);

    public void update(float dt) {
    }

    public abstract TextureRegion getTextureRegion(TextureRegion textureRegion);

    public void addEntity(PlayScreen playScreen) {
        playScreen.getEntities().add(this);
    }

    public void setDeleteEntity() {
        deleteEntity = true;
    }

    public Body getEntityBody() {
        return entityBody;
    }

    protected TextureRegion getRegion() {
        return currentRegion;
    }
}
