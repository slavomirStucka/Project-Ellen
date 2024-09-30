package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;

//import javax.swing.*;

public interface Usable <A extends Actor> {
    void useWith(A Actor);
    Class<A> getUsingActorClass();
}
