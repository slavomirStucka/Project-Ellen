package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;


public class MissionImpossible implements SceneListener {
    private Ripley ripley;
    private Energy lekarnicka;
    //private Disposable a;
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);
        lekarnicka=scene.getFirstActorByType(Energy.class);
        MovableController movableController =new MovableController(ripley);
        Disposable movableC=scene.getInput().registerListener(movableController);
        KeeperController<Collectible> keeperController = new KeeperController<>(ripley);
        Disposable keeperC=scene.getInput().registerListener(keeperController);
        new When<>(
            () -> ripley.intersects(lekarnicka),
            new Invoke<>(() -> lekarnicka.useWith(ripley))
        ).scheduleFor(ripley);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley)->ripley.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableC.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperC.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.stopDecreasing());
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley= scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }
//    public void loopDecreasing(){
//        new Loop<>(
//            new Invoke<>((Ripley)->ripley.decreaseEnergy()));
//    }

    public static class Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name.equals("ellen")){
                return new Ripley();
            }
            if(name.equals("energy")){
                return new Energy();
            }
            //if(name.equals("door")){
                //return new LockedDoor();
            //}
            if(name.equals("access card")){
                return new AccessCard();
            }
            if(name.equals("ventilator")){
                return new Ventilator();
            }
            if(name.equals("locker")){
                return new Locker();
            }
            return null;
        }
    }
}
