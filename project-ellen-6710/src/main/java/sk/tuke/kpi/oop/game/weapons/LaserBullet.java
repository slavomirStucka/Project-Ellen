package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class LaserBullet extends Bullet {
    public LaserBullet() {
        setAnimation(new Animation("sprites/energy_bullet.png",16,16));
        this.setDamage(30);
    }

}
