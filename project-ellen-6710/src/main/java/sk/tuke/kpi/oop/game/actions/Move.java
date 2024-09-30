package sk.tuke.kpi.oop.game.actions;


import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.gamelib.actions.Action;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.oop.game.weapons.Bullet;


public class Move<A extends Movable> implements Action<A> {
    private A actor;
    private float duration;
    private Direction direction;
    private boolean isDone;
    private int firstTime;

    public Move(Direction direction, float duration) {
        this.direction=direction;
        this.duration=duration;
        isDone=false;
        firstTime=0;
        // implementacia konstruktora akcie
    }
    public Move(Direction direction){
        this.direction=direction;
        isDone=false;
        firstTime=0;
    }

    @Nullable
    @Override
    public A getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable A movable) {
        this.actor = movable;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void reset() {
        actor.stoppedMoving();
        isDone=false;
        firstTime=0;
        duration=0;
    }
    public void setSettings(){
        if(direction == Direction.NORTH){
            actor.setPosition(actor.getPosX(), actor.getPosY()+actor.getSpeed());
            //System.out.println("N");
        }
        else if(direction == Direction.EAST){
            actor.setPosition(actor.getPosX()+actor.getSpeed(), actor.getPosY());
           // System.out.println("E");
        }
        else if(direction == Direction.SOUTH){
            actor.setPosition(actor.getPosX(), actor.getPosY()-actor.getSpeed());
            //System.out.println("S");
        }
        else if(direction == Direction.WEST){
            actor.setPosition(actor.getPosX()-actor.getSpeed(), actor.getPosY());
            //System.out.println("w");
        }
        else if(direction == Direction.NORTHWEST){
            actor.setPosition(actor.getPosX()-actor.getSpeed(), actor.getPosY()+actor.getSpeed());
            //System.out.println("NW");
        }
        else if(direction == Direction.NORTHEAST){
            actor.setPosition(actor.getPosX()+actor.getSpeed(), actor.getPosY()+actor.getSpeed());
            //System.out.println("NE");
        }
        else if(direction == Direction.SOUTHWEST){
            actor.setPosition(actor.getPosX()-actor.getSpeed(), actor.getPosY()-actor.getSpeed());
            //System.out.println("SW");
        }
        else if(direction == Direction.SOUTHEAST){
            actor.setPosition(actor.getPosX()+actor.getSpeed(), actor.getPosY()-actor.getSpeed());
            //System.out.println("SE");
        }
    }


    @Override
    public void execute(float deltaTime) {
        if(getActor()==null){return;}
        int x= actor.getPosX();
        int y=actor.getPosY();

        if(!isDone) {
            if (firstTime == 0) {
                actor.startedMoving(this.direction);
                firstTime += 1;
            }
            duration=duration-deltaTime;
            if (duration > 0) {
                setSettings();
                if(!(actor instanceof Bullet)&&actor.getScene().getMap().intersectsWithWall(actor)){
                    actor.collidedWithWall();
                    actor.setPosition(x,y);
                }
                if(actor.getScene().getMap().intersectsWithWall(actor)&&actor instanceof Bullet){
                    actor.collidedWithWall();
                }
            } else {
                actor.stoppedMoving();
                isDone = true;
            }
        }
    }
    public void stop(){
        if(actor==null){return;}
        actor.stoppedMoving();
        isDone=true;
    }
    public Direction getDirection(){
        return this.direction;
    }
}
