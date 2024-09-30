package sk.tuke.kpi.oop.game;

//import java.lang.Math;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
//import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
//import sk.tuke.kpi.oop.game.tools.Hammer;

public class Reactor extends AbstractActor implements Switchable,Repairable{
    private Set<EnergyConsumer> devices;
    private boolean exploded;
    private int temperature;
    private int damage;
    private int highTemperature;
    private boolean stav;
    private Animation normalAnimation;
    private double frame=0.1;
    private Animation destroyedAnimation;
    private Animation hotAnimation;
    private Animation offAnimation;
    private Animation extinguished;

    // private Animation hotAnimation=new Animation("sprites/reactor_hot.png", 80, 80, (float)frame, Animation.PlayMode.LOOP_PINGPONG);

    // private Animation onAnimation=new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    //setAnimation(normalAnimation);

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        devices = new HashSet<>();
        this.stav=false;
        this.exploded=false;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.destroyedAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80,(float)frame, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png", 80, 80);
        this.extinguished =  new Animation("sprites/reactor_extinguished.png", 80, 80);
        updateAnimation();


    }
    public Reactor getReactor(){
        return this;
    }
    public int getTemperature(){
        return temperature;
    }

    public int getDamage() {
        return damage;
    }
    public void increaseTemperature(int increment) {
        if(stav==false||increment<0){ return;}
        if (temperature + increment >= 6000) {
            temperature +=increment;
            damage = 100;
            this.stav=false;
            updateAnimation();
            return;
        }
        else if (temperature < 6000) {
            if(temperature>2000) {
                this.damage = (highTemperature - 2000) / 40;
            }
            increaseTemperature1(increment);
        }
        if (this.temperature >= 6000) {
            this.damage = 100;
            // this.temperature = 6000;
            this.stav=false;
        }
        updateAnimation();
    }
    public void increaseTemperature1(int increment){
        float zaokruhlene=increment;
        if(damage<=33){
            this.temperature+=increment;
        }
        if (damage > 33 && damage <= 66) {
            this.temperature += Math.ceil(zaokruhlene*1.5);
        } else if (damage > 66) {
            this.temperature += increment * 2;
        }
        if (this.temperature >= this.highTemperature) {
            this.highTemperature = this.temperature;
        }
        if (this.temperature >= 6000) {
            this.damage = 100;
            updateAnimation();
        }

    }
    public void decreaseTemperature(int decrement) {
        if(stav==true) {
            if (decrement < 0) {
                temperature = temperature*1;
            } else {
                if (damage == 100) {
                    temperature = 6000;
                } else if (damage >= 50 && damage < 100) {
                    temperature = temperature - (decrement / 2);
                } else {
                    temperature = temperature - decrement;
                }
                if (temperature <= 4000) {
                    updateAnimation();
                }
            }
            if(temperature<=0){
                temperature=0;
            }
        }
    }

    private void updateAnimation() {
        if(stav==false){
            if(temperature!=4000&&temperature!=6000){
                setAnimation(offAnimation);
            }
            if(temperature>=6000){
                setAnimation(destroyedAnimation);
                this.exploded=true;
                if(devices!=null) {
                    devices.forEach(energyConsumer -> energyConsumer.setPowered(false));
                }
            }
        }
        else {
            if (temperature > 4000 && temperature < 6000) {
                updateFrameHot(hotAnimation);
                setAnimation(hotAnimation);
            } else if (temperature <= 4000) {
                setAnimation(normalAnimation);
            }
        }
    }
    public void updateFrameHot(Animation animacia){
        if(temperature>4000&&temperature<4400) {
            frame = 0.1;
            animacia.setFrameDuration((float)frame);
        }
        else if(temperature>=4400&&temperature<4800){
            frame=0.08;
            animacia.setFrameDuration((float)frame);   }
        else if(temperature>=4800&&temperature<5200){
            frame=0.06;
            animacia.setFrameDuration((float)frame);  }
        else if(temperature>=5200&&temperature<5600){
            frame=0.04;
            animacia.setFrameDuration((float)frame);   }
        else
        {frame = 0.02;
            animacia.setFrameDuration((float)frame);   }
    }

    public boolean repair() {
        if(this.damage==0||this.damage==100){
            return false;
        }else {
            this.damage = this.damage - 50;
            if (this.damage < 0) {
                this.damage = 0;
            }
            this.temperature = Math.min(2000 + (40 * damage), this.temperature);
            updateAnimation();
            return true;
        }
    }

    public boolean extinguish() {
        if (damage==100) {
            temperature = 4000;
            setAnimation(extinguished);
            this.stav=false;
            return true;
        }else{return false;}
    }
    public void turnOff(){
        stav=false;
        updateAnimation();
        devices.forEach(energyConsumer -> energyConsumer.setPowered(false));
    }
    public void turnOn(){
        if(!this.exploded) {
            stav = true;
            updateAnimation();
            devices.forEach(energyConsumer -> energyConsumer.setPowered(true));
        }
    }
    public boolean isOn(){
        if(stav==true){
            return true;
        }
        else {
            return false;
        }
    }
    public void addDevice(EnergyConsumer consumer){
        if (isOn()&&damage<100){
            consumer.setPowered(true);
        }
        this.devices.add(consumer);
    }
    public void removeDevice(EnergyConsumer consumer){
        if (isOn()&&damage<100){
            consumer.setPowered(false);
        }else if(!isOn()||damage==100){
            consumer.setPowered(false);
        }
        this.devices.remove(consumer);
    }
    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

}




