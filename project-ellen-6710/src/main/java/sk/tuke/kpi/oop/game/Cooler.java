package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Animation normalAnimation;
    private Reactor reactor;
    private int stav;

    public Cooler(Reactor reactor){
        this.reactor=reactor;
        this.stav=0;
        this.normalAnimation =new Animation("sprites/fan.png",32,32,0.2f, Animation.PlayMode.LOOP_PINGPONG);

        if (stav==0) {
            setAnimation(normalAnimation);
            this.normalAnimation.pause();
        }else{
            setAnimation(normalAnimation);
            this.normalAnimation.play();
        }
    }
    public Reactor getReactor() {
        return reactor;
    }

    public void coolReactor(){
        if(this.stav==1&&reactor==null){
            return;
        }
        if (this.stav==1){
            reactor.decreaseTemperature(1);
        }
    }
    @Override
    public void turnOff(){
        this.stav=0;
        //this.normalAnimation =new Animation("sprites/fan.png",32,32,0f);
        setAnimation(normalAnimation);
        normalAnimation.pause();
    }
    @Override
    public void turnOn(){
        this.stav=1;
        setAnimation(normalAnimation);
        this.normalAnimation.play();
    }
    @Override
    public boolean isOn(){
        if(this.stav==0){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<> (new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
