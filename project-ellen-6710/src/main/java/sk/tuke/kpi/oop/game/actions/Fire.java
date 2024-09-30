package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Bullet;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire<A extends Armed> extends AbstractAction<A> {
   // private A actor;
    private Fireable bullet;
    public Fire(){
        bullet=new Bullet();
    }
    @Override
    public void execute(float deltaTime) {
        if ( getActor() == null) {
            setDone(true);
            return;
        }
        if (isDone()) {
            return;
        }
        bullet = getActor().getFirearm().fire();
        int pomX = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
        int pomY = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();
        if (bullet!=null) {
            Objects.requireNonNull(getActor().getScene()).addActor(bullet, getActor().getPosX() + 8 + pomX*24, getActor().getPosY() + 8 + pomY*24);
            bullet.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
            new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()),200).scheduleFor(bullet);
        }
        setDone(true);
    }

}
