package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
//import sk.tuke.kpi.oop.game.behaviours.Shooting;
import sk.tuke.kpi.oop.game.weapons.Firearm;

public class Alien extends AbstractActor implements Alive,Enemy, Movable{//Armed {
    private Health health;
    private Behaviour<? super Alien> behaviour;
    private Firearm firearm;
    public Alien(){
        this.health= new Health(100);
        setAnimation(new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health.onExhaustion(() -> getScene().removeActor(this));
    }

    public Alien(int health, Behaviour<? super Alien> behaviour){
        this.health= new Health(health,100);
        this.behaviour=behaviour;
        setAnimation(new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        this.health.onExhaustion(() -> getScene().removeActor(this));
    }


    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Health getHealth() {
        return health;
    }
    public void contactWithEnemy(Alive actor){
        if(!(actor instanceof Enemy)&&(this.intersects(actor))&&!health.isExhaustion()){
            actor.getHealth().drain(1);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Alive actor=getScene().getFirstActorByType(Alive.class);
        new Loop<>(new Invoke<>(()->contactWithEnemy(actor))).scheduleOn(scene);
        if(behaviour!=null){
            this.behaviour.setUp(this);
        }
    }

}
