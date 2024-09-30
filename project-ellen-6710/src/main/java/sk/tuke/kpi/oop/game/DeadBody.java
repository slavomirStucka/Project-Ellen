package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.Money;
import sk.tuke.kpi.oop.game.items.Usable;

public class DeadBody  extends AbstractActor implements Usable<Actor> {
    private boolean robbed1;
    private boolean robbed2;
    public DeadBody(){
        setAnimation(new Animation("sprites/body.png",64,48));
        this.robbed1=false;
        this.robbed2=false;
    }

    @Override
    public void useWith(Actor Actor) {
        if(!robbed1){
            Actor.getScene().addActor(new Ammo(),this.getPosX(),this.getPosY()+50);
            Actor.getScene().addActor(new Money(),this.getPosX()-10,this.getPosY()+50);
            this.robbed1=true;
        }
        else if(!robbed2){
            Actor.getScene().addActor(new Energy(),this.getPosX()+20,this.getPosY()+50);
            this.robbed2=true;
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
