package com.jishd.fight.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.jishd.fight.Screens.PlayScreen;

//Disposable???
public abstract class Entity extends Sprite{
    private PlayScreen playScreen;
    private Body entityBody;

    private boolean deleteEntity;

    public Entity(PlayScreen playScreen, int positionX, int positionY ){
        this.playScreen = playScreen;


        createEntityBody();
        setRegion(getTextureRegion());
        setBounds(positionX, positionY, getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight());
        playScreen.addEntity(this);
    }

    public abstract void createEntityBody();
    public abstract void update(float dt) ;

    public abstract TextureRegion getTextureRegion();

    public void deleteEntity(){
        deleteEntity = true;
    }

    public Body getEntityBody() {
        return entityBody;
    }
}
