package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Bullet;

public class WallSwitch extends AbstractActor implements Movable {
    public WallSwitch(){
        setAnimation(new Animation("sprites/wall_switch.png",16,16));
    }
    public void posun() {
        Ripley hrac=getScene().getLastActorByType(Ripley.class);

        if(intersects(hrac)&&!getScene().getMap().intersectsWithWall(this)){
            this.setPosition(this.getPosX(),this.getPosY()+1);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(()->posun())).scheduleOn(scene);
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void collidedWithWall() {
        Movable.super.collidedWithWall();
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
    }
}
