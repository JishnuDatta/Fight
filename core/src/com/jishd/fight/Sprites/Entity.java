package com.jishd.fight.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

//Used for creating player entities
    public Entity(PlayScreen playScreen, float spawnPointX, float spawnPointY, TextureRegion textureRegion) {
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.completeEntityRegion = textureRegion;
        deleteEntity = false;
        currentRegion = getTextureRegion(textureRegion);
        setRegion(currentRegion);
        createEntityBody(spawnPointX, spawnPointY);
        setSize(currentRegion.getRegionWidth() / FightGame.PPM, currentRegion.getRegionHeight() / FightGame.PPM);
        addEntity();
    }

    public abstract void createEntityBody(float spawnPointX, float spawnPointY);

    public abstract void update(float dt);

    public abstract TextureRegion getTextureRegion(TextureRegion textureRegion);

    public void addEntity() {
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

    public Vector2 degreesToCoordinates(float degrees){
        return new Vector2(0,0);
    }
}
