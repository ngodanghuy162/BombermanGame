package uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;

import java.security.Signature;

/**
 * Đối tượng bom nổ.
 */
public class Explotion extends Entity {
    private boolean isLast ;
    private int way;

    public int getWay() {
        return way;
    }
    public void setLast() {
        this.isLast = true;
    }

    public void setWay(int way) {
        this.way = way;
    }
    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    4:sang phai
     */
    public Explotion(int x, int y, int way,boolean isLast) {
        super(x,y,Sprite.brick.getFxImage());
        this.x = x;
        this.y = y;
        this.way = way;
        this.isLast = isLast;
        if (isLast) {
            if (way == 1)
                setImg(Sprite.explosion_vertical2.getFxImage());
            else if (way == 2)
                setImg(Sprite.explosion_vertical2.getFxImage());
            else if (way == 3)
                setImg(Sprite.explosion_horizontal2.getFxImage());
            else if (way == 4)
                setImg(Sprite.explosion_horizontal2.getFxImage());
        }
        else {
        if (way == 1)
            img = Sprite.explosion_vertical_top_last1.getFxImage();
        else if (way == 2)
            img = Sprite.explosion_vertical_down_last2.getFxImage();
        else if (way == 3)
            img = Sprite.explosion_horizontal_left_last2.getFxImage();
        else if (way == 4)
            img = Sprite.explosion_horizontal_right_last2.getFxImage();
        }
    }

    public void update(int timeAfter) {
        if (this.way == 3) {
                if (isLast == true) {
                    img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, 60 - timeAfter, 80).getFxImage();
                }
                else {
                    img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, 60 - (timeAfter), 80).getFxImage();
                }
        }
        if (this.way == 4) {
                img = isLast ?
                        Sprite.movingSprite(Sprite.explosion_horizontal_right_last,Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, 60 - timeAfter, 80).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, 60 - timeAfter, 80).getFxImage();
            }
        if (this.way == 1) {
                img = isLast ?
                        Sprite.movingSprite(Sprite.explosion_vertical_top_last,Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, 60 - timeAfter, 80).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1, Sprite.explosion_vertical2, 60 - timeAfter, 80).getFxImage();
            }
        if (this.way == 2) {
            img = isLast ?
                 Sprite.movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, 60 - timeAfter, 80).getFxImage():
                 Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1, Sprite.explosion_vertical2, 60 - timeAfter, 80).getFxImage();
        }
    }

    @Override
    public void update() {
    }
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
