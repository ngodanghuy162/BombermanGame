package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

/**
 * Đứng yên 1 chỗ quay, khi bị nổ sẽ trở thành 1-4 Minvo.
 */
public class RedMinvoRotate extends AutoMove {
    Random random = new Random();
    public RedMinvoRotate(int x, int y, Image img) {
        super(x,y, img, 1, Sprite.redMinvoRotate);
        setPoint(100);
        setAmount(random.nextInt(3) + 1);
    }

    @Override
    public void update() {
        if (!check_dead) {
            moveMinvoRotate();
        } else {
            DEAD();
        }
        if (getRemove() && !getSwapMonster()) {
            setSwapMonster(true);
        }
    }
}
