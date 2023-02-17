package uet.oop.bomberman.entities;

import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Interaction.Collision;
import uet.oop.bomberman.entities.Move.MoveEntity;
import uet.oop.bomberman.getBomberControl;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomb.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Các thuộc tính của Bomber.
 */
public class Bomber extends MoveEntity {
    //mạng player
    private int heart = 2;
    public static boolean isDead = false;
    private int cntLeft = 1;
    private int cntRight = 1;
    private int cntUp = 1;
    private int cntDown = 1;
    private int cntDead = 0;
    private int maxBomb = 1;
    private Collision collision = new Collision();

    private boolean check_move_bomb = true;
    private boolean check_isExplotion = true;

    public int act = 0;

    public long imageTimeNow = 0;
    public long imageTimeAgo = 0;
    public static final long TIME_IMG = 100;
    public static final long TIME_DEAD = 500;

    private int swapImg = 0;

    //di chuyển sau khi lấy được item
    public int moveItem = 0;

    /**
     * hướng nhìn
     */
    public enum status {
        STOP(0), LEFT(1), RIGHT(2), UP(3), DOWN(4), DEAD(5);

        public final int value;

        status(int value) {
            this.value = value;
        }
    }
    public void setMaxBomb() {
        maxBomb++;
    }

    private List<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img, int speed) {
        super(x, y, img, speed);
        //chênh lệch chiều rộng
        setW(Sprite.SCALED_SIZE - 7);
        //chênh lệch chiều cao
        setH(Sprite.SCALED_SIZE - 6);
    }

    public int getHeart() {
        return heart;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
    public void removeAllBom() {
        bombs.clear();
    }

    public void reduceHeart() {
        this.heart--;
    }

    public void setPosition() {
        this.setX(32);
        this.setY(32);
    }
    public void setHeart(int heart) {
        this.heart = heart;
    }

    @Override
    public void update() {
        if (heart > 0) {
            int x_ = this.x;
            int y_ = this.y;
            this.moveBomber();
            this.addBomb();
            for (int i = 0; i < bombs.size(); i++) {
                //không đi qua bom
                bombs.get(i).update();
                bombs.get(i).isInExplotion(bombs);
                if (!this.collision.CheckCollision(this, bombs.get(i)) && check_move_bomb) {
                    bombs.get(i).setWall();
                }

                //bom nổ
                if (this.collision.CheckCollision(this, bombs.get(i)) && bombs.get(i).isExplotion()) {
                    if (this.x != x_) {
                        this.setX(x_);
                    }
                    if (this.y != y_) {
                        this.setY(y_);
                    }
                    if (bombs.get(i).getTimeAfterExplode() <= 0 && bombs.get(i).getRemove() && check_isExplotion) {
                        heart--;
                        if (heart >= 1) {
                            setPosition();
                        }
                    }
                }
                for (int j = 0; j < bombs.get(i).getExplotionList().size(); j++) {
                    if (collision.CheckCollision(bombs.get(i).getExplotionList().get(j), this) && bombs.get(i).isExplotion()) {
                        if (this.x != x_) {
                            this.setX(x_);
                        }
                        if (this.y != y_) {
                            this.setY(y_);
                        }
                        if (bombs.get(i).getTimeAfterExplode() <= 0 && bombs.get(i).getRemove() && check_isExplotion) {
                            heart--;
                            if (heart >= 1) {
                                setPosition();
                            }
                        }
                    }
                }
                if (bombs.get(i).getRemove()) {
                    bombs.get(i).setWallToZero();
                    bombs.remove(i);
                }

            }
        } else {
                isDead = true;
                act = status.DEAD.value;
                this.moveIMG();
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).update();
                if (bombs.get(i).getRemove()) {
                    bombs.remove(i);
                }
            }
        }
    }


    private void addBomb() {
        if (getBomberControl.bomberSpace == true && bombs.size() < maxBomb) {
            int xBomber = (this.getX() + 10) / 32;
            int yBomber = (this.getY() + 10) / 32;
            Bomb newBom = new Bomb(xBomber, yBomber, Sprite.bomb.getFxImage());
            if (newBom.isHaveBomb(bombs) == false) {
                bombs.add(newBom);
                newBom.makeExplotion(1);
                newBom.makeExplotion(2);
                newBom.makeExplotion(3);
                newBom.makeExplotion(4);
            }
        }
    }


    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    -4:sang phai
     */
    public static enum move {
        UP(1), DOWN(2), LEFT(3), RIGHT(4);

        private final int value;

        move(int value) {
            this.value = value;
        }
    }

    @Override
    public boolean canMove(int way) {
        if (moveItem == 1) {
            return true;
        }
        if (way == move.UP.value) {
            return !collision.CheckMapCollision(x, y - speed, w, h, BombermanGame.map.getMap());
        }

        if (way == move.DOWN.value) {
            return !collision.CheckMapCollision(x, y + speed, w, h, BombermanGame.map.getMap());
        }

        if (way == move.LEFT.value) {
            return !collision.CheckMapCollision(x - speed, y, w, h, BombermanGame.map.getMap());
        }

        if (way == move.RIGHT.value) {
            return !collision.CheckMapCollision(x + speed, y, w, h, BombermanGame.map.getMap());
        }

        return true;
    }

    public void moveBomber() {
            act = status.STOP.value;
            if (getBomberControl.bomberLeft) {
                getBomberControl.bomberDown = false;
                getBomberControl.bomberRight = false;
                getBomberControl.bomberUp = false;
                act = status.LEFT.value;
                this.moveLeft();
            }
            if (getBomberControl.bomberRight) {
                getBomberControl.bomberDown = false;
                getBomberControl.bomberLeft = false;
                getBomberControl.bomberUp = false;
                act = status.RIGHT.value;
                this.moveRight();
            }
            if (getBomberControl.bomberUp) {
                getBomberControl.bomberDown = false;
                getBomberControl.bomberRight = false;
                getBomberControl.bomberLeft = false;
                act = status.UP.value;
                this.moveUp();
            }
            if (getBomberControl.bomberDown) {
                getBomberControl.bomberLeft = false;
                getBomberControl.bomberRight = false;
                getBomberControl.bomberUp = false;
                act = status.DOWN.value;
                this.moveDown();
            }
    }

    @Override
    public void moveUp() {
        if (canMove(move.UP.value)) {
            y -= speed;
        } else {
            //di chuyển mượt hơn
            boolean check_left = !collision.CheckMapCollision(x, y - speed, 0, h, BombermanGame.map.getMap());
            boolean check_right = !collision.CheckMapCollision(x + w, y - speed, 0, h, BombermanGame.map.getMap());

            if (check_left && ! check_right) {
                moveLeft();
            }
            if (!check_left && check_right) {
                moveRight();
            }
        }
        moveIMG();
    }

    public void moveDown() {
        if (canMove(move.DOWN.value)) {
            y += speed;
        } else {
            //di chuyển mượt hơn
            boolean check_left = !collision.CheckMapCollision(x, y + speed, 0, h, BombermanGame.map.getMap());
            boolean check_right = !collision.CheckMapCollision(x + w, y + speed, 0, h, BombermanGame.map.getMap());

            if (check_left && ! check_right) {
                moveLeft();
            }
            if (!check_left && check_right) {
                moveRight();
            }
        }
        moveIMG();
    }

    public void moveLeft() {
        if (canMove(move.LEFT.value)) {
            x -= speed;
        } else {
            //di chuyển mượt hơn
            boolean check_up = !collision.CheckMapCollision(x - speed, y, w, 0, BombermanGame.map.getMap());
            boolean check_down = !collision.CheckMapCollision(x - speed, y + h, w, 0, BombermanGame.map.getMap());

            if (check_up && ! check_down) {
                moveUp();
            }
            if (!check_up && check_down) {
                moveDown();
            }
        }
        moveIMG();
    }

    public void moveRight() {
        if (canMove(move.RIGHT.value)) {
            x += speed;
        } else {
            //di chuyển mượt hơn
            boolean check_up = !collision.CheckMapCollision(x + speed, y, w, 0, BombermanGame.map.getMap());
            boolean check_down = !collision.CheckMapCollision(x + speed, y + h, w, 0, BombermanGame.map.getMap());

            if (check_up && ! check_down) {
                moveUp();
            }
            if (!check_up && check_down) {
                moveDown();
            }
        }
        moveIMG();
    }

    public void moveIMG() {
        imageTimeNow = System.currentTimeMillis();
        if (BombermanGame.cntSound % 2 == 0) {
            BombermanGame.audio.playAudio(Audio.audio.bobDropped.value);
        }
        if (act == status.DEAD.value) {
            if (imageTimeNow - imageTimeAgo > TIME_DEAD) {
                cntDead++;
                if (cntDead > 2) {
                    cntDead = 2;
                }
                imageTimeAgo = System.currentTimeMillis();
            }
        } else if (imageTimeNow - imageTimeAgo > TIME_IMG) {
            swapImg++;

            BombermanGame.audio.stopAudio(Audio.audio.bobDropped.value);

            imageTimeAgo = System.currentTimeMillis();
        }


        if (swapImg >= 2)
            swapImg = 0;

        if (act == status.LEFT.value)
            img = BombermanGame.player.getList().get(swapImg + 9).getFxImage();
        if (act == status.RIGHT.value)
            img = BombermanGame.player.getList().get(swapImg + 3).getFxImage();
        if (act == status.UP.value)
            img = BombermanGame.player.getList().get(swapImg).getFxImage();
        if (act == status.DOWN.value)
            img = BombermanGame.player.getList().get(swapImg + 6).getFxImage();
        if (act == status.DEAD.value)
            img = BombermanGame.player.getList().get(cntDead + 12).getFxImage();

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMoveItem(int item) {
        this.moveItem = item;
    }

    public void setCheck_move_bomb(boolean check_move_bomb) {
        this.check_move_bomb = !check_move_bomb;
    }

    public void setCheck_isExplotion(boolean check_isExplotion) {
        this.check_isExplotion = !check_isExplotion;
    }

    public void setAct(int act) {
        this.act = act;
    }
    public int getAct() {
        return this.act;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(gc);
        }
    }
}
