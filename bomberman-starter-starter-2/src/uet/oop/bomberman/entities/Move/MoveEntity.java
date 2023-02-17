package uet.oop.bomberman.entities.Move;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

/**
 * Di chuyển của player.
 */
public abstract class MoveEntity extends Entity {
    protected int way;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;
    protected int dieTime = 0;
    protected boolean isDead = false;
    public MoveEntity(int x, int y, Image img, int speed) {
        super(x,y,img);
        this.speed = speed;
    }
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract boolean canMove(int way);
    public void setWay(int way) {
        this.way = way;
    }
    public int getWay() {
        return way;
    }
    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    -4:sang phai
     */
}

