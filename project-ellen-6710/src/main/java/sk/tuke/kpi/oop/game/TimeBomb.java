package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private Animation activatedAnimation;
    private Animation explodedAnimation;
    private boolean isActivated;

    private float time;

    public TimeBomb(float seconds){
        this.time=seconds;
        isActivated=false;
        Animation normalAnimation=new Animation("sprites/bomb.png");
        activatedAnimation=new Animation("sprites/bomb_activated.png",16,16,0.2f);
        explodedAnimation=new Animation("sprites/small_explosion.png",64,32,0.1f, Animation.PlayMode.ONCE);
        setAnimation(normalAnimation);
    }
    public float getTime(){
        return time;
    }
    public void activate(){
            isActivated =true;
            setAnimation(activatedAnimation);
            new ActionSequence<>(
                new Wait<>(this.time),
                new Invoke<>(this::vybuch)
            ).scheduleFor(this);
    }
    public boolean isActivated(){
        return isActivated;
    }
    public void vybuch(){
        setAnimation(explodedAnimation);
        new ActionSequence<>(new Invoke<>(this::remove)).scheduleFor(this);
    }
    private void remove(){
        getScene().removeActor(this);
    }
}



