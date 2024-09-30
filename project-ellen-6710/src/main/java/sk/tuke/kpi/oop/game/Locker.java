package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Money;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Actor> {
    private boolean search;
    public Locker(){
        setAnimation(new Animation("sprites/locker.png",16,16));
        this.search=false;
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void useWith(Actor Actor) {
        if(!search){
            Actor.getScene().addActor(new Money(),this.getPosX()+20,this.getPosY());
            search=true;
        }
    }
}
