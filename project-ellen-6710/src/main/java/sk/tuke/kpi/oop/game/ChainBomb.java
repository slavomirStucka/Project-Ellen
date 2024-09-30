package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
//import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb{

    public ChainBomb(float time){
        super(time);
    }

    @Override
    public void vybuch() {
        super.vybuch();

        Ellipse2D.Float Okruh=new Ellipse2D.Float(getPosX()-50,getPosY()-50,102,102);
        List<Actor> actorslist = getScene().getActors();
        for (Actor actor: actorslist) {
            if (actor instanceof ChainBomb&&!((ChainBomb) actor).isActivated()&&Okruh.intersects(actor.getPosX()-actor.getWidth()/2, actor.getPosY()-actor.getHeight()/2, actor.getWidth(),actor.getHeight())) {
                //if (Okruh.intersects(actor.getPosX()-actor.getWidth()/2, actor.getPosY()-actor.getHeight()/2, actor.getWidth(),actor.getHeight())){
                    ((ChainBomb) actor).activate();
                //}
            }

        }
    }

}


