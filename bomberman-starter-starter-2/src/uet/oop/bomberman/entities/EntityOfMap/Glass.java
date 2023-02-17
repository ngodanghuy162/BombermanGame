package uet.oop.bomberman.entities.EntityOfMap;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.BombermanGame;
public class Glass extends Entity {
    public Glass(int x,int y) {
        super(x, y, Sprite.glass.getFxImage());
    }
    @Override
    public void update() {

    }
}
