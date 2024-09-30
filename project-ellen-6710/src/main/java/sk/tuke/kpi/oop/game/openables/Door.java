package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation openvAnimation;
    private Animation closevAnimation;
    private Animation openhAnimation;
    private Animation closehAnimation;
    private boolean isOpen;
    private Orientation orientation;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation{
        HORIZONTAL,
        VERTICAL
    }
    public Door(String name, Orientation orientation){
        super(name);
        this.orientation=orientation;
        openhAnimation =new Animation("sprites/hdoor.png",32,16,0.1f, Animation.PlayMode.ONCE);
        closehAnimation= new Animation("sprites/hdoor.png",32,16,0.1f, Animation.PlayMode.ONCE_REVERSED);
        openvAnimation =new Animation("sprites/vdoor.png",16,32,0.1f, Animation.PlayMode.ONCE);
        closevAnimation =new Animation("sprites/vdoor.png",16,32,0.1f, Animation.PlayMode.ONCE_REVERSED);
        if(orientation==Orientation.HORIZONTAL){
            setAnimation(closehAnimation);
        }
        if(orientation==Orientation.VERTICAL){
            setAnimation(closevAnimation);
        }
        this.isOpen=false;
    }

    @Override
    public void open() {
        if(isOpen==false){
            if(orientation==Orientation.HORIZONTAL){
                setAnimation(openhAnimation);
                openhAnimation.play();
                Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX() / 16+1, this.getPosY() / 16 ).setType(MapTile.Type.CLEAR);
                getScene().getMessageBus().publish(DOOR_OPENED, this);
                isOpen = true;
            }
            if(orientation==Orientation.VERTICAL) {
                setAnimation(openvAnimation);
                openvAnimation.play();
                Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
                getScene().getMessageBus().publish(DOOR_OPENED, this);
                isOpen = true;
            }
        }
    }

    @Override
    public void close() {
        if(isOpen==true){
            if(orientation==Orientation.HORIZONTAL){
                closehAnimation.play();
                setAnimation(closehAnimation);
                //closehAnimation.play();
                Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX() / 16+1, this.getPosY() / 16).setType(MapTile.Type.WALL);
                getScene().getMessageBus().publish(DOOR_CLOSED, this);
                isOpen = false;
            }
            if(orientation==Orientation.VERTICAL) {
                closevAnimation.play();
                setAnimation(closevAnimation);
                //closevAnimation.play();
                Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
                getScene().getMessageBus().publish(DOOR_CLOSED, this);
                isOpen = false;
            }
        }
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void useWith(Actor Actor) {
        if(isOpen()){
            this.close();
        }else if(!isOpen()){
            this.open();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(orientation==Orientation.HORIZONTAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16+1, this.getPosY() / 16 ).setType(MapTile.Type.WALL);
        }
        if(orientation==Orientation.VERTICAL) {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
    }
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

}

