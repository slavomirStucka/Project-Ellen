package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible{ //Usable<Repairable> {
    //private Animation normalAnimation;
    //private int remainingUses;

    public Hammer(){
        this(1);
    }
    public Hammer(int uses){
        super(uses);
        Animation normalAnimation = new Animation("sprites/hammer.png");
        setAnimation(normalAnimation);
    }
    @Override
    public void useWith(Repairable repairable){
        if(repairable==null){return;}
        else if(repairable.repair()){
            super.useWith(repairable);
        }
    }
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }

}





