package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Có thể đi qua tường nhưng không đuổi player.
 */
public class Kondoria extends AutoMove {
    Random random = new Random();
    public Kondoria(int x, int y, Image img) {
        super(x, y, img, 1, Sprite.kondoria);
        setPoint(100);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        if (!check_dead) {
            moveKondoria(way);
        } else {
            DEAD();
        }
    }
}
