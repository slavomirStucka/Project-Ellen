package sk.tuke.kpi.oop.game.controllers;

//import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.oop.game.Helicopter;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.characters.Health;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Money;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.Objects;

public class KeeperController<A extends Actor> implements KeyboardListener {
    private Keeper actor;
    public KeeperController(Keeper actor){
        this.actor=actor;
    }


    @Override
    public void keyPressed(Input.Key key) {
        KeyboardListener.super.keyPressed(key);
        if(key==Input.Key.ENTER){
            new Take<>().scheduleFor(this.actor);
        }
        if(key==Input.Key.H&&actor.getScene().getFirstActorByType(Ripley.class).getMoney()>=1000){
            Helicopter helicopter=new Helicopter();
            actor.getScene().addActor(helicopter,actor.getPosX()+500,actor.getPosY()-200);
        }
        if(key==Input.Key.BACKSPACE){
            new Drop<>().scheduleFor(this.actor);
        }
        if(key==Input.Key.S){
            new Shift<>().scheduleFor(this.actor);
        }
        Actor actorItem = actor.getScene().getActors().stream().filter(actor::intersects).filter(Usable.class::isInstance).findFirst().orElse(null);
        if(key == Input.Key.U && actorItem != null&&actorItem!=actor.getScene().getFirstActorByType(LockedDoor.class)){
                new Use<>((Usable<?>) actorItem ).scheduleForIntersectingWith(actor);
        }
        if(key == Input.Key.U && actorItem != null&&actorItem==actor.getScene().getFirstActorByType(LockedDoor.class)){
            int windowHeight = Objects.requireNonNull(actor.getScene()).getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight / 2;
            int windowWidth = Objects.requireNonNull(actor.getScene()).getGame().getWindowSetup().getWidth();
            int xTextPos = windowWidth / 2;
            actor.getScene().getGame().getOverlay().drawText("YOU NEED ACCESS CARD!",xTextPos,yTextPos);
        }
        if(key==Input.Key.B&&actor.getBackpack().peek() instanceof Usable){
            //if (actor.getBackpack().peek() instanceof Usable) {
                Use<?> use=new Use<>((Usable<?>)actor.getBackpack().peek());
                use.scheduleForIntersectingWith(actor);
                actor.getBackpack().remove(actor.getBackpack().peek());
        }

    }
}
