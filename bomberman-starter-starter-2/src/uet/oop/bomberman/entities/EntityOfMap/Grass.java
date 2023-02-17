package uet.oop.bomberman.entities.EntityOfMap;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Images;

public class Grass extends Entity {
    public Grass(int x,int y) {
        super (x,y, Sprite.grass.getFxImage());
    }
    @Override
    public void update() {

    }
}
