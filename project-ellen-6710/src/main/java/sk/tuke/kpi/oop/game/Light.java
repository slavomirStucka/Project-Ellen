package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable,EnergyConsumer{
    private Animation onAnimation;
    private Animation offAnimation;
    private boolean zapinac;
    private boolean powered;
    public Light(){
        zapinac=false;
        powered=false;
        onAnimation = new Animation("sprites/light_on.png", 16, 16);
        offAnimation = new Animation("sprites/light_off.png", 16, 16);
        updateAnimation();
    }

    public void updateAnimation(){
        if(zapinac==true&& powered ==true) {
            setAnimation(onAnimation);
        }
        else  {
            setAnimation(offAnimation);
        }
    }

    public Animation getOffAnimation() {
        return this.offAnimation;
    }

    public Animation getOnAnimation() {
        return this.onAnimation;
    }

    public void toggle(){
        zapinac = !zapinac;
        updateAnimation();
    }
    @Override
        public void turnOn() {
            if (zapinac == false) {
                zapinac = true;
                updateAnimation();
            }
    }

    @Override
    public void turnOff() {
        if (zapinac == true) {
            zapinac = false;
            updateAnimation();
        }
    }

    @Override
    public boolean isOn() {
        if(zapinac==true){return true;}
        else{return false;}
    }
    @Override
    public void setPowered(boolean powered) {
        this.powered = powered;
        updateAnimation();
    }

}
