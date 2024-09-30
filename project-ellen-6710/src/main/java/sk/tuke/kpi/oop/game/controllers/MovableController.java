package sk.tuke.kpi.oop.game.controllers;

//import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Move<Movable> move = new Move<>(Direction.NONE);
    private Movable movable;
    private Set<Direction> setKeys;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)

    );

    public MovableController(Movable movable){
        this.movable=movable;
        this.setKeys= new HashSet<>();
    }

    @Override
    public void keyReleased(Input.Key key) {
        KeyboardListener.super.keyReleased(key);
        if(keyDirectionMap.containsKey(key)==false){
            return;
        }
        if (move != null) move.stop();
        setKeys.remove(keyDirectionMap.get(key));
        if (setKeys.isEmpty()){
            move = new Move<>(Direction.NONE);
            return;
        }
        Direction direction = move.getDirection();
        move = new Move<>(direction.deleteDirection(keyDirectionMap.get(key)), Float.MAX_VALUE);
        move.scheduleFor(movable);

    }

    @Override
    public void keyPressed(Input.Key key) {
        KeyboardListener.super.keyPressed(key);
        if(keyDirectionMap.containsKey(key)==false){
            return;
        }

        if(move.getDirection()!=Direction.NONE) move.stop();
        Direction newdirection=move.getDirection();
        newdirection = newdirection.combine(keyDirectionMap.get(key));
        setKeys.add(keyDirectionMap.get(key));
        move=new Move<>(newdirection,Float.MAX_VALUE);
        move.scheduleFor(movable);
    }

}
