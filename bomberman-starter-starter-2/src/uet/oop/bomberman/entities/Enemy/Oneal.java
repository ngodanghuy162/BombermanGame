package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Loại quái không thể đi qua tường nhưng lại có thể đuổi theo player.
 */
public class Oneal extends AutoMove {
    protected int speed = 10;
    Random random = new Random();


    public Oneal(int x, int y, Image img) {
        super( x, y, img, 3, Sprite.oneal);
        setPoint(100);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        if (!check_dead) {
            moveOneal(way);
        } else {
            DEAD();
        }
    }
}
