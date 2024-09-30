package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;


import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {
    private Animation animation;
    private boolean isRepaired;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);
    public Ventilator(){
        animation=new Animation("sprites/ventilator.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.stop();
        this.isRepaired=false;
    }

    @Override
    public boolean repair() {
        if(!this.isRepaired){
            Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED, this);
            this.isRepaired=true;
            this.animation.play();
            return true;
        }
        return false;
    }
}
