package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {

    public SmartCooler(Reactor reactor) {
        super(reactor);
        //this.turnOff();
    }

    @Override
    public void coolReactor() {
        Reactor reactor= getReactor();
        if(reactor==null){return;}
        int temperature=reactor.getTemperature();
        if(temperature>2500&&!isOn()) {
                this.turnOn();
                reactor.decreaseTemperature(2);
        }else if(temperature<1500&&isOn()){
                this.turnOff();
        }
            /*if (isOn()) {
                    reactor.decreaseTemperature(2);
            }*/
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<> (new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}


