package sk.tuke.kpi.oop.game.behaviours;


import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {
    public RandomlyMoving(){

    }
    public void randMove(Movable actor){
        int random=(int)(Math.random()*(4));
        //System.out.println(random);
        if(random==1){
            actor.getAnimation().setRotation(0);
            new Move<>(Direction.NORTH,3).scheduleFor(actor);
        }
        if(random==2){
            actor.getAnimation().setRotation(270);
            new Move<>(Direction.EAST,3).scheduleFor(actor);
        }
        if(random==3){
            actor.getAnimation().setRotation(180);
            new Move<>(Direction.SOUTH,3).scheduleFor(actor);
        }
        if(random==0){
            actor.getAnimation().setRotation(90);
            new Move<>(Direction.WEST,3).scheduleFor(actor);
        }
    }
    @Override
    public void setUp(Movable actor) {
        if(actor!=null) {
            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::randMove),
                    new Wait<>(2)
                )).scheduleFor(actor);
        }
    }
}
