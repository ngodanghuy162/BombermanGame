package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

/**
 * Loại quái có thể đi xuyên tường và đuổi theo player.
 */
public class Ghost extends AutoMove {
    protected int speed = 8;

    Random random = new Random();

    public Ghost(int x, int y, Image img) {
        super( x, y, img, 2, Sprite.ghost);
        setPoint(100);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        if (!check_dead) {
            moveGhost(way);
        } else {
            DEAD();
        }
    }
}
