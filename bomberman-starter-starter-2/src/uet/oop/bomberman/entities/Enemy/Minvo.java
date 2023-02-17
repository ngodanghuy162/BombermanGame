package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * loại quái có tốc độ di chuyển nhanh gấp đôi quái khác và đuổi theo player.
 */
public class Minvo extends AutoMove {
    Random random = new Random();
    public Minvo(int x, int y, Image img) {
        super(x,y, img, 3, Sprite.minvo);
        setPoint(100);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        if (!check_dead) {
            moveMinvo(way);
        } else {
            DEAD();
        }
    }
}
