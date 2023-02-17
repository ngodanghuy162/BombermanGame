package uet.oop.bomberman.entities.EntityOfMap;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
public class Brick extends Entity {
    private boolean isExploted = false;
    private boolean isEndExplotion = false;
    public Brick(int x, int y) {
        super(x,y, Sprite.brick.getFxImage());
    }

    public void setExploted() {
        this.isExploted = true;
    }
    public void update(int timeAfter) {
        if (isExploted && !isEndExplotion) {
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, 60 - timeAfter, 80).getFxImage();
            if (timeAfter == 0) {
                isEndExplotion = true;
                this.img = Sprite.glass.getFxImage();
            }
        }
    }
    @Override
    public void update() {

    }
}
