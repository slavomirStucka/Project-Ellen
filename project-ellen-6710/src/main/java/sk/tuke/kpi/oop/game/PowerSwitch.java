package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.items.Money;

public class PowerSwitch extends AbstractActor {
    private boolean added;
    private Switchable switchable;
    //Reactor reactor = new Reactor();

    public PowerSwitch(Switchable switchable) {
        this.switchable = switchable;
        Animation normalAnimation = new Animation("sprites/switch.png");
        setAnimation(normalAnimation);
        this.added=false;
    }

    public Switchable getDevice() {
        return this.switchable;
    }

    public void switchOn() {
        if (switchable == null) {
            return;
        }
        else {
            this.switchable.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }

    public void switchOff() {
        if (switchable == null) {
            return;
        }
        else {
            this.switchable.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }
    public void contactWithWallSwitch(Actor actor){
        actor=getScene().getFirstActorByType(WallSwitch.class);
        if(this.intersects(actor)&&actor instanceof WallSwitch&&!this.added){
            this.switchOff();
            getScene().addActor(new Money(),this.getPosX(),this.getPosY()-115);
            this.added=true;
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        WallSwitch wallSwitch=scene.getFirstActorByType(WallSwitch.class);
        new Loop<>(new Invoke<>(()->contactWithWallSwitch(wallSwitch))).scheduleFor(this);

    }
}

