package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class RipleySearching implements Behaviour<Movable> {
    private Disposable disposable = null;
    private Move<Movable> move = null;
    public void searchRipley(Movable actor) {
        int alienX = actor.getPosX();
        int alienY = actor.getPosY();

        int ripleyX = actor.getScene().getFirstActorByType(Ripley.class).getPosX();
        int ripleyY = actor.getScene().getFirstActorByType(Ripley.class).getPosY();
        if (alienX != ripleyX) {
            if (alienX > ripleyX) {
                alienX--;
            } else {
                alienX++;
            }
        }
        if (alienY != ripleyY) {
            if (alienY > ripleyY) {
                alienY--;
            } else {
                alienY++;
            }
        }
        alienX = alienX - ripleyX;
        alienY = alienY - ripleyY;
        if (alienX > 0) {
            alienX = -1;
        } else if (alienX < 0) {
            alienX = 1;
        }

        if (alienY > 0) {
            alienY = -1;
        } else if (alienY < 0) {
            alienY = 1;
        }
        Direction direction = null;
        for (Direction value : Direction.values()) {
            if (alienX == value.getDx() && alienY == value.getDy()) {
                direction = value;
            }
        }
        if (move != null) {
            move.stop();
            disposable.dispose();
            move = null;
        }
        if (direction != null) {
            move = new Move<>(direction, Float.MAX_VALUE);
            disposable = move.scheduleFor(actor);
        }
    }

    @Override
    public void setUp (Movable actor){
        if (actor != null) {
            new Loop<>(new ActionSequence<Movable>(
                new Invoke<>(() -> {
                    searchRipley(actor);
                }),
                new Wait<>(0.9f)
            )).scheduleFor(actor);
        }
    }
}
