package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;

public class EscapeRoom implements SceneListener {
    //private Ripley ripley;

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name.equals("ellen")){
                return new Ripley();
            }
            if(name.equals("energy")){
                return new Energy();
            }
            if(name.equals("ammo")){
                return new Ammo();
            }
            if(name.equals("alien")){
                //if(type.equals("running")){
                    //return new Alien(20, new RandomlyMoving());
                //}
                return new Alien(20,new RandomlyMoving());
            }
            if(name.equals("alien mother")){
                return new AlienMother(20,new RandomlyMoving());
            }
            if(name.equals("front door")){
                return new Door("front door", Door.Orientation.VERTICAL);
            }

            if(name.equals("back door")){
                return new Door("back door", Door.Orientation.HORIZONTAL);
            }

            if(name.equals("exit door")){
                return new Door("exit door", Door.Orientation.VERTICAL);
            }
            return null;
        }
    }
    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
       Ripley ripley = scene.getFirstActorByType(Ripley.class);
       // lekarnicka=scene.getFirstActorByType(Energy.class);
        MovableController movableController =new MovableController(ripley);
        Disposable movableC=scene.getInput().registerListener(movableController);
        KeeperController<Collectible> keeperController = new KeeperController<>(ripley);
        Disposable keeperC=scene.getInput().registerListener(keeperController);
        ShooterController shooterController=new ShooterController(ripley);
        Disposable shooterC=scene.getInput().registerListener(shooterController);
        Hammer hammer= new Hammer();
        scene.addActor(hammer,300,100);
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableC.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperC.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->shooterC.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ripley= scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }
}
