package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.AlienEgg;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.items.Barrel;


public class Bullet extends AbstractActor implements Fireable{
    private int damage;
    public Bullet(){
        this.damage=10;
        setAnimation(new Animation("sprites/bullet.png",16,16));
    }

    @Override
    public int getSpeed() {
        return 4;
    }
    public void setDamage(int newDamage){
        damage=newDamage;
    }

    @Override
    public void startedMoving(Direction direction) {
        Fireable.super.startedMoving(direction);
    }
    public void intersectsWithAlive(){
        for (Actor actor : getScene().getActors()) {
            if (this.intersects(actor) && (actor instanceof Alive)) {
                ((Alive) actor).getHealth().drain(damage);
                collidedWithWall();
            }
            else if(this.intersects(actor)&&actor instanceof Barrel){
                ((Barrel) actor).getHealth().drain(damage);
                collidedWithWall();
            }
            else if(this.intersects(actor)&&actor instanceof AlienEgg){
                ((AlienEgg) actor).getHealth().drain(damage);
                collidedWithWall();
            }
        }

//        for (Actor actor : this.getScene()) {
//            if (this.intersects(actor) && actor instanceof Alive && !(actor instanceof Ripley)) {
//                ((Alive)actor).getHealth().drain(10);
//                getScene().removeActor(this);
//            }
//        }

    }
    @Override
    public void collidedWithWall() {
        Fireable.super.collidedWithWall();
        getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::intersectsWithAlive)).scheduleFor(this);
    }
}
