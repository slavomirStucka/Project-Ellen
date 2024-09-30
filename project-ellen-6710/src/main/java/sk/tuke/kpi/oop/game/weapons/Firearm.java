package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int maxAmmo;
    private int actualAmmo;
    public Firearm(int actual,int max){
        this.maxAmmo=max;
        this.actualAmmo=actual;
    }
    public Firearm(int actualMax){
        this.actualAmmo=actualMax;
        this.maxAmmo=actualMax;
    }
    public int getAmmo(){
        return this.actualAmmo;
    }
    public void reload(int newAmmo){
        this.actualAmmo+=newAmmo;
        if(this.actualAmmo>=this.maxAmmo){
            this.actualAmmo=this.maxAmmo;
        }
    }
    protected abstract Fireable createBullet();
    public Fireable fire(){
        if(this.actualAmmo>0){
            this.actualAmmo--;
        }else if(this.actualAmmo==0){
            return null;
        }
        return createBullet();
    }
}
