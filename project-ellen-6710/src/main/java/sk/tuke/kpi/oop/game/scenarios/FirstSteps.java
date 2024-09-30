package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;

import sk.tuke.kpi.gamelib.actions.When;
//import sk.tuke.kpi.gamelib.framework.actions.Loop;
//import sk.tuke.kpi.oop.game.ChainBomb;
//import sk.tuke.kpi.oop.game.Direction;
//import sk.tuke.kpi.oop.game.Keeper;
//import sk.tuke.kpi.oop.game.Movable;
//import sk.tuke.kpi.oop.game.actions.Drop;
//import sk.tuke.kpi.oop.game.actions.Move;
//import sk.tuke.kpi.oop.game.actions.Take;
//import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

//import java.util.List;

public class FirstSteps implements SceneListener {
    private Ripley ripley;
    private Energy lekarnicka;
    private Ammo ammo;

  @Override
   public void sceneInitialized(@NotNull Scene scene) {
      ripley=new Ripley();
      scene.addActor(ripley, 10, 10);
      MovableController movableController = new MovableController(ripley);
      scene.getInput().registerListener(movableController);

      lekarnicka= new Energy();
      scene.addActor(lekarnicka,-100,50);

      ammo= new Ammo();
      scene.addActor(ammo,100,20);
      //Direction smer=Direction.NORTH;
      //scene.scheduleAction(new Move<Ripley>(smer,20));

      new When<>(
          () -> ripley.intersects(lekarnicka),
          new Invoke<>(() -> lekarnicka.useWith(ripley))
      ).scheduleFor(ripley);

      new When<>(
          () -> ripley.intersects(ammo),
          new Invoke<>(() -> ammo.useWith(ripley))
      ).scheduleFor(ripley);


      Hammer hammer= new Hammer();
      scene.addActor(hammer,-130,50);
      new When<>(
          () -> ripley.intersects(ammo),
          new Invoke<>(() -> ammo.useWith(ripley))
      ).scheduleFor(ripley);
      FireExtinguisher fireExtinguisher= new FireExtinguisher();
      ripley.getBackpack().add(fireExtinguisher);
      Wrench wrench = new Wrench();
      ripley.getBackpack().add(wrench);
      KeeperController<Collectible> keeperController = new KeeperController<>(ripley);
      scene.getInput().registerListener(keeperController);
     // scene.getGame().getInput().onKeyPressed(Input.Key.ENTER, () -> new Drop<>().scheduleOn(scene));
      //scene.getGame().pushActorContainer(ripley.getBackpack());
      ripley.getBackpack().shift();
  }


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        Ripley ellen= scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
//        scene.getGame().pushActorContainer(ripley.getBackpack());
//        scene.getGame().getOverlay().drawText("ENERGY: "+ ripley.getEnergy(),120,(scene.getGame().getWindowSetup().getHeight()) - GameApplication.STATUS_LINE_OFFSET);
//        scene.getGame().getOverlay().drawText("AMMO: "+ ripley.getAmmo(),260,(scene.getGame().getWindowSetup().getHeight()) - GameApplication.STATUS_LINE_OFFSET);
    }
}
