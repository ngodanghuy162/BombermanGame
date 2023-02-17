package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Loại quái di chuyển đơn giản(random), không thể đi xuyên qua tường.
 */
public class Balloom extends AutoMove {
    protected int speed = 10;
    protected int way;
    Random random = new Random();


    public Balloom(int x, int y, Image img) {
        super( x, y, img, 1, Sprite.balloom);
        setPoint(100);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        setWay(way);
        if (!check_dead) {
            moveBalloom(way);
        } else {
            DEAD();
        }
    }
}
