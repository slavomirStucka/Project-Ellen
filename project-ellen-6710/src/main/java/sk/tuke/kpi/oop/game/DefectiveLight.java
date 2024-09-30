package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

//import java.util.concurrent.TimeUnit;

public class DefectiveLight extends Light implements Repairable{
    private Disposable action;
    private boolean isRepaired;

    private Animation onAnimation=getOnAnimation();
    private Animation offAnimation=getOffAnimation();
    public DefectiveLight(){
        this.isRepaired=false;
    }
    public void defect(){
        double f = Math.random()/Math.nextDown(1.0);
        double x = 1.0*(1.0 - f) + 20.0*f;
        if(x>10&&x<12){
            super.toggle();
        }

    }
    public void cyclicAnimation(){
        Reactor reactor=new Reactor();
        if(reactor==null){return;}
        if(getAnimation()==onAnimation){
            setAnimation(offAnimation);
        }else{setAnimation(onAnimation);}
    }
    @Override
    public boolean repair() {
        if(this.isRepaired==true){
            return false;
        }
            action.dispose();
            setAnimation(onAnimation);
            this.action=new ActionSequence<>(
                new Invoke<>(()->this.isRepaired=true),
                new Wait<>(10),
                new Invoke<>(()->addedToScene(getScene())),
                new Invoke<>(()->this.isRepaired=false)
            ).scheduleFor(this);
            return true;
    }
    public boolean isRepaired(){
        return this.isRepaired;
    }
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        this.action=new Loop<> (new Invoke<>(this::defect)).scheduleFor(this);
    }
    public void breakLight() {
        this.action = new Loop<>(new Invoke<>(this::defect)).scheduleFor(this);
    }

}
