package sk.tuke.kpi.oop.game.items;

//import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {
    //private int remainingUses;
    public FireExtinguisher(){
        super(1);
        Animation normalAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(normalAnimation);
    }

    @Override
    public void useWith(Reactor reactor) {
        if(reactor==null){ return;}
        else if(reactor.extinguish()) {
            super.useWith(reactor);

        }
    }
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
