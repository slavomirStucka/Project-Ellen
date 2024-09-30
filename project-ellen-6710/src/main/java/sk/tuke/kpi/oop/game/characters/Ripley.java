package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;



import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.items.Backpack;

import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper,Alive,Armed {
    private Animation playerAnim;
    private Animation diedAnim;
   // private int energy;
    private Health health;
    //private Disposable disposable=null;
    private int ammo;
    private int speed;
    private Firearm firearm;
    private Backpack backpack;
    private int money;
   // private Scene scene;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    public static final Topic<Ripley> RIPLEY_SEARCHED = Topic.create("ripley searched", Ripley.class);
    public Ripley(){
        super("Helen");
        playerAnim =new Animation("sprites/player.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        diedAnim=new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE);
        setAnimation(playerAnim);
        speed=3;
        money=0;
        health=new Health(100,100);
        //energy=60;
        //ammo=460;
        setFirearm(new Gun(50,100));
        backpack=new Backpack("Ripley's backpack",3);
    }
    public int getMoney(){
        return this.money;
    }
    public void addMoney(int newMoney){
        this.money+=newMoney;
    }
    @Override
    public Health getHealth() {
        return this.health;
    }

    public int getAmmo(){
        return this.ammo;
    }
    public void setAmmo(){
        if(this.getAmmo()+50<500){
            this.ammo=this.getAmmo()+50;
        }else{
            this.ammo=500;
        }

    }
//    public int getEnergy(){
//        return this.energy;
//    }
//    public void setEnergy(int newEnergy){
//        this.energy=newEnergy;
//    }

    @Override
    public Firearm getFirearm() {
        return this.firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.firearm=weapon;
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int newSpeed){
        speed=newSpeed;
    }

    @Override
    public void startedMoving(Direction direction) {
        //Movable.super.startedMoving(direction);
        float rotation;
        if(direction.getAngle()==-1){
            rotation= direction.getAngleSecond();
        }else{
            rotation=direction.getAngle();
        }
        playerAnim.setRotation(rotation);
        playerAnim.play();
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    @Override
    public void stoppedMoving() {
       // Movable.super.stoppedMoving();
        playerAnim.stop();
    }
    public void diedRipley(){
        setAnimation(diedAnim);
        this.getScene().getMessageBus().publish(RIPLEY_DIED, this);
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight / 2;
        int windowWidth = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth / 2;
        getScene().getGame().getOverlay().drawText("YOU LOOSE!", xTextPos, yTextPos);

    }
    public void searchedRipley(){
        Reactor teleport=getScene().getFirstActorByType(Reactor.class);
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight / 2;
        int windowWidth = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth / 2;
        if (this.intersects(teleport)) {
            this.getScene().getMessageBus().publish(RIPLEY_SEARCHED, this);
            getScene().getGame().getOverlay().drawText("YOU WIN!", xTextPos, yTextPos);
        }else{
            getScene().getGame().getOverlay().drawText("GO TO HELIPORT!", xTextPos, yTextPos);
        }

    }
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        //new Loop<>(new Invoke<>(() -> diedRipley())).scheduleFor(this);
        this.health.onExhaustion(() -> diedRipley());
    }
    public void showRipleyState() {
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().pushActorContainer(this.getBackpack());
        getScene().getGame().getOverlay().drawText("Energy " + this.getHealth().getValue(), 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Ammo " + this.getFirearm().getAmmo(), 250, yTextPos);
        getScene().getGame().getOverlay().drawText("Money " + this.getMoney()+"/1000", 380, yTextPos);
    }
    public void decreaseEnergy() {

        if (this.getHealth().getValue() <= 0) {
            //this.stoppedMoving();
            this.setAnimation(diedAnim);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
           // new Invoke<>(()->diedRipley());
        }
        else {
            //disposable =
             new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> { if (this.getHealth().getValue() <= 0) {
                        //this.stoppedMoving();
                        this.setAnimation(diedAnim);
                        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
                        //new Invoke<>(()->diedRipley());
                        return;}
                    else {this.getHealth().drain(1);}
                    }),
                    new Wait<>(1)
                )
            ).scheduleFor(this);
        }
    }
    public void stopDecreasing(){
        if (this.getHealth().getValue() <= 0) {
            //this.stoppedMoving();
            this.setAnimation(diedAnim);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
            //new Invoke<>(()->diedRipley());
        }
        else {
            //disposable =
                new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> { if (this.getHealth().getValue() <= 0) {
                        //this.stoppedMoving();
                        this.setAnimation(diedAnim);
                        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
                        //new Invoke<>(()->diedRipley());
                        return;}
                    else {this.getHealth().drain(-1);}
                    }),
                    new Wait<>(1)
                )
            ).scheduleFor(this);
        }
    }
}
