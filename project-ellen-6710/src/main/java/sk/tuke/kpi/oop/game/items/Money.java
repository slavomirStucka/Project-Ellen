package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Money extends AbstractActor implements Usable<Ripley>{

    public Money(){
        setAnimation(new Animation("sprites/money.png",16,16));
    }

    public void useWith(Ripley Actor) {
        if(Actor==null){return;}
        Actor.addMoney(100);
        (Objects.requireNonNull(getScene())).removeActor(this);
    }
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

}
