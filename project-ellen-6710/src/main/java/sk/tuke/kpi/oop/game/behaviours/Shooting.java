//package sk.tuke.kpi.oop.game.behaviours;
//
//import sk.tuke.kpi.gamelib.actions.ActionSequence;
//import sk.tuke.kpi.gamelib.actions.Invoke;
//import sk.tuke.kpi.gamelib.actions.Wait;
//import sk.tuke.kpi.gamelib.framework.actions.Loop;
//import sk.tuke.kpi.oop.game.Direction;
//import sk.tuke.kpi.oop.game.Movable;
//import sk.tuke.kpi.oop.game.actions.Fire;
//import sk.tuke.kpi.oop.game.actions.Move;
//import sk.tuke.kpi.oop.game.characters.Armed;
//import sk.tuke.kpi.oop.game.characters.Ripley;
//import sk.tuke.kpi.oop.game.weapons.Fireable;
//
//public class Shooting implements Behaviour<Armed> {
//    public Shooting(){
//
//    }
//    public void shooting(Armed actor){
//        Ripley hrac = actor.getScene().getLastActorByType(Ripley.class);
//        Direction direction=Direction.NONE;
//        float rotation=0;
//        if (actor.getPosY() != hrac.getPosY()) {
//            if (actor.getPosY() < hrac.getPosY()) {
//                direction=Direction.NORTH;
//                actor.getAnimation().setRotation(0);
//                new Fire<>().scheduleFor(actor);
//            }
//            if (actor.getPosY() > hrac.getPosY()) {
//                direction=Direction.SOUTH;
//                actor.getAnimation().setRotation(180);
//                new Fire<>().scheduleFor(actor);
//            }
//        }
//        if (actor.getPosX() != hrac.getPosX()) {
//            if (actor.getPosX() < hrac.getPosX()) {
//                direction=Direction.EAST;
//                actor.getAnimation().setRotation(270);
//                new Fire<>().scheduleFor(actor);
//            }
//            if (actor.getPosX() > hrac.getPosX()) {
//                direction=Direction.WEST;
//                actor.getAnimation().setRotation(90);
//                new Fire<>().scheduleFor(actor);
//            }
//        }
//        if(actor.intersects(hrac)){
//            hrac.getHealth().drain(1);
//        }
//
//    }
//
//
//    @Override
//    public void setUp(Armed actor) {
//        if(actor!=null) {
//            new Loop<>(
//                new ActionSequence<>(
//                    new Invoke<>(this::shooting),
//                    new Wait<>(2)
//                )).scheduleFor(actor);
//        }
//    }
//}
