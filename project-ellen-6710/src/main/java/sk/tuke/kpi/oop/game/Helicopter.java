package sk.tuke.kpi.oop.game;

//import org.jetbrains.annotations.NotNull;
//import sk.tuke.kpi.gamelib.Scene;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Helicopter extends AbstractActor {
    private int newPosX=0, newPosY=0;
    private Animation normalAnimation=new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP_PINGPONG);
    public Helicopter() {
        setAnimation(normalAnimation);
    }
    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::searchPlayer)).scheduleFor(this);
    }
    private void searchPlayer() {
        Ripley hrac = getScene().getLastActorByType(Ripley.class);
        if (this.getPosY() != hrac.getPosY()) {
            if (this.getPosY() < hrac.getPosY()) {
                newPosY = this.getPosY() + 1;
                this.normalAnimation.setRotation(0);
            }
            if (this.getPosY() > hrac.getPosY()) {
                newPosY = this.getPosY() - 1;
                this.normalAnimation.setRotation(180);
            }
        }
        if (this.getPosX() != hrac.getPosX()) {
            if (this.getPosX() < hrac.getPosX()) {
                newPosX = this.getPosX() + 1;
                this.normalAnimation.setRotation(270);
            }
            if (this.getPosX() > hrac.getPosX()) {
                newPosX = this.getPosX() - 1;
                this.normalAnimation.setRotation(90);
            }
        }
        if(newPosY==this.getPosY()+1&&newPosX==this.getPosX()+1)this.normalAnimation.setRotation(315);
        else if(newPosY==this.getPosY()+1&&newPosX==this.getPosX()-1)this.normalAnimation.setRotation(45);
        else if(newPosY==this.getPosY()-1&&newPosX==this.getPosX()+1)this.normalAnimation.setRotation(225);
        else if(newPosY==this.getPosY()-1&&newPosX==this.getPosX()-1)this.normalAnimation.setRotation(135);
        setPosition(newPosX, newPosY);
        //if(intersects(hrac)){
        //  hrac.setEnergy(hrac.getEnergy()-1);
        //}
        if(intersects(hrac)){
             hrac.searchedRipley();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(()->searchPlayer())).scheduleOn(scene);
    }
}
