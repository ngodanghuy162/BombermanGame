package uet.oop.bomberman.entities.EntityOfMap;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Images;

public class Wall extends Entity {
    public Wall(int x, int y) {
        super(x,y, Sprite.wall.getFxImage());
    }
    @Override
    public void update() {

    }
}
