package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class AlienMother extends Alien{
    //private Behaviour<? super Alien> behaviour;

    public AlienMother(int health, Behaviour<? super Alien> behaviour){
        super(health,behaviour);
        setAnimation(new Animation("sprites/mother.png",112,162,0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }
}
