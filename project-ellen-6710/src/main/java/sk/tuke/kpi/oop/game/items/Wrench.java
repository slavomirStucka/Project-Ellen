package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;
//import sk.tuke.kpi.oop.game.Reactor;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible {
    //private Animation normalAnimation;

    public Wrench(){
        this(2);
    }
    public Wrench(int uses){
        super(uses);
        Animation normalAnimation = new Animation("sprites/wrench.png");
        setAnimation(normalAnimation);
    }
    @Override
    public void useWith(DefectiveLight defLight){
        if(defLight==null){return;}
        else if(defLight.repair()){
            super.useWith(defLight);
        }
    }
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }

}
