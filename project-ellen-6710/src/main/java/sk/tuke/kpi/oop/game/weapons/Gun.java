package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{
    public Gun(int actual,int max){
        super(actual,max);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
