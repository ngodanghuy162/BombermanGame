package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public abstract class Entity {
    protected int point = 0;
    protected int speed;
    //khoảng cách so với tạo độ gôc
    int distance = 0;
    protected int x;

    protected int maxImg;
    //Tọa độ X tính từ góc trái trên trong Canvas
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int w;
    protected int h;
    protected boolean check_dead = false;

    //thay đổi loại quái
    private boolean swap_monster = false;
    //số lượng quái đẻ ra
    private int amount = 1;

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }


    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }
    public void addX(int x) {
        this.x += x;
    }

    public void addY(int y) {
        this.y += y;
    }
    public int getY() {
        return y;
    }
    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
    protected Image img;
    protected boolean remove = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.w = Sprite.SCALED_SIZE;
        this.h = Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return this.point;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean getRemove() {
        return this.remove;
    }
    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    protected void setImg(Image img) {
        this.img = img;
    }

    public void setCheckDead(boolean check_dead) {
        this.check_dead = check_dead;
    }

    public void setSwapMonster(boolean swap_monster) {
        this.swap_monster = swap_monster;
    }

    public boolean getSwapMonster() {
        return this.swap_monster;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void addSpeed(int speed){
        this.speed += speed;
    }
    public int getSpeed() {
        return this.speed;
    }

    public int getMaxImg() {
        return this.maxImg;
    }
    public void setMaxImg(int maxImg) {
        this.maxImg = maxImg;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}



