package sk.tuke.kpi.oop.game.weapons;

public class LaserGun extends Firearm {

    public LaserGun(int actualAmmo, int maxAmmo) {
        super(actualAmmo, maxAmmo);
    }


    @Override
    protected Fireable createBullet() {
        return new LaserBullet();
    }
}
