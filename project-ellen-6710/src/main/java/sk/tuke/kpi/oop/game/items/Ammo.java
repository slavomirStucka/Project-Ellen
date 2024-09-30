package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;


import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Armed> {
    public Ammo(){
        Animation animation=new Animation("sprites/ammo.png",16,16);
        setAnimation(animation);
    }

    @Override
    public void useWith(Armed Actor) {
        if(Actor==null){return;}
        //if(Actor.getFirearm().getAmmo()<500){
            Actor.getFirearm().reload(100);
            (Objects.requireNonNull(getScene())).removeActor(this);
        //}
        //return;
    }
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
