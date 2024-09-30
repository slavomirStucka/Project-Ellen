package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int actual;
    private int maxEnergy;
    private List<ExhaustionEffect> exeffectList;
    private boolean isExhaustion;
    public Health(int startEnergy,int maxEnergy){
        this.actual=startEnergy;
        this.maxEnergy=maxEnergy;
        exeffectList=new ArrayList<>();

    }
    public Health(int maxStartEnergy){
        this.actual=maxStartEnergy;
        this.maxEnergy=maxStartEnergy;
        exeffectList=new ArrayList<>();
    }
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
    public void onExhaustion(ExhaustionEffect effect){
        if(exeffectList!=null) {
            exeffectList.add(effect);
        }
    }

    public int getValue() {
        return this.actual;
    }
    public void refill(int amount){
        this.actual+=amount;
        if(this.actual>this.maxEnergy){
            this.actual=this.maxEnergy;
        }
    }
    public void restore(){
        this.actual=this.maxEnergy;
    }
    public void drain(int amount){
        this.actual-=amount;
        if(this.actual<=0){
            this.actual=0;
            if(!isExhaustion){
                exhaust();
            }
        }
    }
    public void exhaust(){
        this.actual = 0;
            if(!isExhaustion) {
                //isExhaustion = true;
                if(exeffectList!=null) {
                    exeffectList.forEach(ExhaustionEffect::apply);
                }
                isExhaustion = true;
            }
    }
    public boolean isExhaustion(){
        return this.isExhaustion;
    }
}
