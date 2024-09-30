package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift<A extends Keeper> extends AbstractAction<A> {
    private Keeper keeper;

    @Override
    public void execute(float deltaTime) {
        if(!isDone()){
            setDone(true);
            if (getActor()==null){return;}
            keeper=getActor();
            keeper.getBackpack().shift();
        }
    }
}
