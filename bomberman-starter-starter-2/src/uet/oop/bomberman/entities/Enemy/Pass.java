package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

/**
 * Di chuyển với tốc độ rất cao và thông minh.
 */
public class Pass extends AutoMove {
    Random random = new Random();
    public Pass(int x, int y, Image img) {
        super(x,y, img, 4, Sprite.pass);
        setPoint(100);
    }

    @Override
    public void update() {
        if (!check_dead) {
            movePass(random.nextInt(5));
        } else {
            DEAD();
        }
    }
}
