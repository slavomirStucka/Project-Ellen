package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Rectangle2D;

public class Teleport extends AbstractActor {
    private boolean teleported ;
    private Teleport nextTeleport;
    private Player player;



    public Teleport(){
        //super(name);
        Animation animation=new Animation("sprites/lift.png");
        setAnimation(animation);
        this.teleported = false;
    }
//    public Teleport(Teleport desTeleport){
//        this.nextTeleport = desTeleport;
//        Animation animation=new Animation("sprites/lift.png");
//        setAnimation(animation);
//        this.teleported = false;
//    }


    public void setDestination(Teleport destinationTeleport){
        if(destinationTeleport == this){
            return;
        }
        this.nextTeleport = destinationTeleport;
    }

    public void teleportPlayer(Player player) {
        player.setPosition(this.getPosX() + (this.getWidth() / 2) - player.getWidth() / 2, this.getPosY() + (this.getHeight() / 2) - player.getHeight() / 2);
    }
    public Teleport getDestination(){
        return this.nextTeleport;
    }

    public void prenesenie(){

        Rectangle2D.Float teleport3 = new Rectangle2D.Float(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
        Rectangle2D.Float player1 = new Rectangle2D.Float(this.player.getPosX(), this.player.getPosY(), this.player.getWidth(), this.player.getHeight());
        if(!teleport3.intersects(player1)){
            getScene().cancelActions(this);
            this.teleported = false;
            new Loop<>(new Invoke<>(this::checkplayer)).scheduleFor(this);
        }

    }

    public void checkplayer(){
        if(this.nextTeleport == null || this.teleported) return;
        Rectangle2D.Float teleport3 = new Rectangle2D.Float(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
        int playerX=this.player.getPosX()+this.player.getWidth()/2;
        int playerY=this.player.getPosY()+this.player.getHeight()/2;
        Rectangle2D.Float player1 = new Rectangle2D.Float(playerX, playerY, 1, 1);
        if (teleport3.intersects(player1) == true) {
            this.nextTeleport.teleportPlayer(player);
            this.nextTeleport.teleported = true;
            new Loop<>(new Invoke<>( this.nextTeleport::prenesenie)).scheduleFor(this.nextTeleport);
            if( this.nextTeleport.nextTeleport != this ) this.teleported = false;
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        player =  (Player) getScene().getFirstActorByName("Player");
        new Loop<>(new Invoke<>(() -> checkplayer())
        ).scheduleFor(this);
    }
}


