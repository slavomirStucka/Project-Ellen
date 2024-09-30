package sk.tuke.kpi.oop.game.actions;


import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;


public class Drop<A extends Keeper> extends AbstractAction<A> {
    private Keeper keeper;
    public Drop(){

    }
    @Override
    public void execute(float deltaTime) {
        if(!isDone()) {
            setDone(true);
            if (getActor() == null) {
                return;
            }
            keeper = getActor();
            if(keeper.getBackpack().peek()==null){
                return;
            }else{
                //keeper.getScene().addActor(keeper.getBackpack().peek(), keeper.getPosX()+ keeper.getWidth()/2, keeper.getPosY()+ keeper.getHeight()/2);
                keeper.getScene().addActor(keeper.getBackpack().peek(), keeper.getPosX(), keeper.getPosY());
                keeper.getBackpack().remove(keeper.getBackpack().peek());
            }
        }
    }
}
