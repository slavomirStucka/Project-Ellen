package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Health;

public class Barrel extends AbstractActor {
    private Health health;
    private boolean vybuchnuty;

    public Barrel(){
        health=new Health(50);
        setAnimation(new Animation("sprites/barrel.png",16,16));
        this.health.onExhaustion(() -> getScene().removeActor(this));
    }

    public Health getHealth() {
        return health;
    }

    public void vybuch(){

        if(!vybuchnuty&&this.health.getValue()==0){
            getScene().addActor(new Ammo(), this.getPosX(),this.getPosY());
            getScene().addActor(new Ammo(), this.getPosX()+2,this.getPosY()+2);
            getScene().addActor(new Money(),this.getPosX()+5,this.getPosY());
            vybuchnuty=true;
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::vybuch)
        ).scheduleOn(scene);
    }




}
