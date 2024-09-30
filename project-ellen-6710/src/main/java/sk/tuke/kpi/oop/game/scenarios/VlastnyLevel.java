package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
//import sk.tuke.kpi.oop.game.behaviours.RipleySearching;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienEgg;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class VlastnyLevel implements  SceneListener{
        public EnergyWawe energyWawe;

        public static class Factory implements ActorFactory {
            public EnergyWawe energyWawe;

            @Override
            public @Nullable Actor create(@Nullable String type, @Nullable String name) {
                if(name.equals("ripley")){
                    return new Ripley();
                }
                if(name.equals("energy")){
                    return new Energy();
                }
                if(name.equals("ammo")){
                    return new Ammo();
                }
                if(name.equals("locker")){
                    return new Locker();
                }
                if(name.equals("key")){
                    return new AccessCard();
                }
                if(name.equals("vdoor")){
                    return new Door("vdoor", Door.Orientation.VERTICAL);
                }
                if(name.equals("locked door")){
                    return new LockedDoor("locked door", Door.Orientation.VERTICAL);
                }

                if(name.equals("hdoor")){
                    return new Door("hdoor", Door.Orientation.HORIZONTAL);
                }

                if(name.equals("reactor")){
                    return new Reactor();
                }
                if(name.equals("dead body")){
                    return new DeadBody();
                }
                if(name.equals("energy wave")){
                    energyWawe=new EnergyWawe();
                    return energyWawe;
                }
                if(name.equals("switch")) {
                    return new PowerSwitch(energyWawe);
                }
                if(name.equals("wall switch")) {
                    return new WallSwitch();
                }
                if(name.equals("barrel")) {
                    return new Barrel();
                }
                if(name.equals("money")) {
                    return new Money();
                }
                if(name.equals("teleport")) {
                    return new Teleport();
                }
                if(name.equals("alien egg")) {
                    return new AlienEgg();
                }
                if(name.equals("alien")) {
                    return new Alien(50,new RandomlyMoving());
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
            scene.follow(ripley);
            MovableController movableController =new MovableController(ripley);
            Disposable movableC=scene.getInput().registerListener(movableController);
            KeeperController<Collectible> keeperController = new KeeperController<>(ripley);
            Disposable keeperC=scene.getInput().registerListener(keeperController);
            ShooterController shooterController=new ShooterController(ripley);
            Disposable shooterC=scene.getInput().registerListener(shooterController);
            scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableC.dispose());
            scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperC.dispose());
            scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->shooterC.dispose());

            scene.getMessageBus().subscribe(Ripley.RIPLEY_SEARCHED, (Ripley)->movableC.dispose());
            scene.getMessageBus().subscribe(Ripley.RIPLEY_SEARCHED, (Ripley)->keeperC.dispose());
            scene.getMessageBus().subscribe(Ripley.RIPLEY_SEARCHED, (Ripley)->shooterC.dispose());
        }

        @Override
        public void sceneUpdating(@NotNull Scene scene) {
            SceneListener.super.sceneUpdating(scene);
            Ripley ripley= scene.getFirstActorByType(Ripley.class);
            assert ripley != null;
            ripley.showRipleyState();
        }


}
