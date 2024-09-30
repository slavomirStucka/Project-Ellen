package sk.tuke.kpi.oop.game.characters;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;
import sk.tuke.kpi.oop.game.Movable;

import java.util.List;
import java.util.Objects;
public class ArmedAlien extends AbstractActor implements Movable, Alive, Enemy, Armed {
    private Health health;
    private Behaviour <? super ArmedAlien> spravanie;

    private Firearm firearm;

    public ArmedAlien() {
        setAnimation(new Animation("sprites/lurker_alien.png", 32, 32, 0.1f));
        health = new Health(100, 100);
        health.onExhaustion(() -> getScene().removeActor(this));
        firearm = new Gun(50, 100);
    }

    public ArmedAlien(int healthValue, Behaviour <? super ArmedAlien> behaviour) {
        setAnimation(new Animation("sprites/lurker_alien.png", 32, 32, 0.1f));
        health = new Health(healthValue, 100);
        spravanie = behaviour;
        this.health.onExhaustion(() -> getScene().removeActor(this));
        firearm = new Gun(8000, 10000);
    }


    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (spravanie != null) {
            spravanie.setUp(this);
        }
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::contactWithEnemy),
                new Wait<>(0.3f)
            )).scheduleFor(this);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> new Fire<>().scheduleFor(this)),//this::strielaj),
                new Wait<>(3)
            )).scheduleFor(this);
    }


    public void contactWithEnemy(Alive actor){
        //if(!(actor instanceof Enemy)&&(this.intersects(actor))&&!health.isExhaustion()){
        Ripley aliveActor=getScene().getFirstActorByType(Ripley.class);
        if (aliveActor instanceof Alive && !(aliveActor instanceof Enemy) && this.intersects(aliveActor)){
                actor.getHealth().drain(1);
        }
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.firearm = weapon;
    }

    private void strielaj(){
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(
                    () -> new Fire<>().scheduleFor(this)),
                new Wait<>(2)
            )
        ).scheduleOn(Objects.requireNonNull(getScene()));
    }


}

