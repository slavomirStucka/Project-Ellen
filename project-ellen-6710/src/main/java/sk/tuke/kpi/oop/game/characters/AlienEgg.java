package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
//import sk.tuke.kpi.oop.game.behaviours.FollowingRipley;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
//import sk.tuke.kpi.oop.game.behaviours.RipleySearching;
import sk.tuke.kpi.oop.game.behaviours.RipleySearching;
//import sk.tuke.kpi.oop.game.behaviours.Shooting;
import sk.tuke.kpi.oop.game.items.Money;

public class AlienEgg extends AbstractActor {
    private Health health;
    public AlienEgg(){
        this.health=new Health(100);
        setAnimation(new Animation("sprites/alien_egg.png",32,32));
        this.health.onExhaustion(() -> getScene().removeActor(this));
    }

    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
            new Loop<>(
                new ActionSequence<>(
            new Invoke<>(()->getScene().addActor(new ArmedAlien(30,new RipleySearching()),this.getPosX(),this.getPosY())),
            new Wait<>(5)
            )).scheduleFor(this);
    }
}
