package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Gun;
import sk.tuke.kpi.oop.game.weapons.LaserGun;

public class EnergyWawe extends AbstractActor implements Switchable {
    private boolean isOn;
    private Animation animation;
    public EnergyWawe(){
        isOn=true;
        animation=new Animation("sprites/energy_wave.png",16,16);
        setAnimation(animation);
    }

    @Override
    public void turnOff() {
            this.isOn=false;
            animation.stop();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public void contactWithEnemy(Alive actor){
        if(actor instanceof Ripley &&this.intersects(actor)&&this.isOn){
            int ammo= ((Ripley) actor).getFirearm().getAmmo();
            actor.getHealth().drain(1);
            new ActionSequence<Ripley>(
                new Invoke<>(() -> ((Ripley)actor).setSpeed(1)),
                new Invoke<>(()->((Ripley)actor).setFirearm(new LaserGun(50, 100))),
                new Wait<>(20),
                new Invoke<>(() -> ((Ripley)actor).setSpeed(3)),
                new Invoke<>(()->((Ripley)actor).setFirearm(new Gun(ammo,100)))
            ).scheduleFor((Ripley)actor);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Alive actor=getScene().getFirstActorByType(Alive.class);
        new Loop<>(new Invoke<>(()->contactWithEnemy(actor))).scheduleOn(scene);
    }

    @Override
    public void turnOn() {
            isOn=true;
            setAnimation(animation);
    }
}
