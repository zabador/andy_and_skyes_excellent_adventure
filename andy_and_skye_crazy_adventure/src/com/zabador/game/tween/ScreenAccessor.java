package com.zabador.game.tween;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.zabador.game.screen.Play;

import aurelienribon.tweenengine.TweenAccessor;

public class ScreenAccessor implements TweenAccessor<Play> {

    public static final int ALPHA = 0;

    @Override
    public int getValues(Play target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case ALPHA:
                returnValues[0] = target.getSpriteBatch().getColor().a;
                return 1; // return how many return values were set
            default:
                assert false;
                return -1; // for easy debugging if something goes wrong
        }
    }

    @Override
    public void setValues(Play target, int tweenType, float[] newValues) {
        switch(tweenType) {
            case ALPHA:
                target.getSpriteBatch().setColor(target.getSpriteBatch().getColor().r, target.getSpriteBatch().getColor().g, target.getSpriteBatch().getColor().b, newValues[0]);
                break;
            default:
                assert false;

        }

    }
    
}
