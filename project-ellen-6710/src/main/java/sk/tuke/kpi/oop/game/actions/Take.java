package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;

public class Take<A extends Keeper> extends AbstractAction<A> {
    private Keeper keeper;
    public Take(){

    }

    @Override
    public void execute(float deltaTime) {
       if(!isDone()){
           setDone(true);
           if(getActor()==null){return;}
           keeper=(Keeper)getActor();
           List<Actor> playerList=keeper.getScene().getActors();
           for(Actor i: playerList){
               if(i.intersects(keeper)&&i instanceof Collectible){
                   try{
                       keeper.getBackpack().add((Collectible)i);
                   }catch (Exception ex){
                       keeper.getScene().getGame().getOverlay().drawText(ex.getMessage(),500 ,500).showFor(2);
                       return;
                   }
                   keeper.getScene().removeActor(i);
                   break;
               }
           }
       }
    }
}
