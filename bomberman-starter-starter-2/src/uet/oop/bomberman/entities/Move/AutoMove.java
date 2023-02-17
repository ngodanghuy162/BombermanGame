package uet.oop.bomberman.entities.Move;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.StopWatch;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Interaction.Collision;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

/**
 * Di chuyển của quái.
 */
public class AutoMove extends MoveEntity {
    private int start = 5;

    private int newWay;

    private long imageTimeAgo = 0;
    private long imageTimeNow = 0;
    private long timeImg = 300;
    private int swapImg = 0;

    private long timeNow = 0;
    private long timeAgo = 0;
    private long timeDead = 0;

    private int cntDelay = 0;
    private int saveWay = 5;

    public static final long TIME_DELAY = 2;
    public static final long timeWay = 500;
    public static final long TIME_DEAD = 1500;

    private int startImg;
    private int checkView = 1;

    private int player_x;
    private int player_y;
    private int check_direction = 0;

    private boolean check_map = false;

    public static final double RANGE = 5 * Sprite.SCALED_SIZE;
    private Collision collision = new Collision();

    private StopWatch time = new StopWatch(300);

    Bomber bomber;

    int[] toX = {0, 0, -1, 1};  //up->down->left->right
    int[] toY = {-1, 1, 0, 0};

    public enum move {
        UP(0), DOWN(1), LEFT(2), RIGHT(3), STOP(4);

        public final int value;
        move(int value) {
            this.value = value;
        }
    }

    public enum view {
        LEFT(-1), RIGHT(1), DIED(0);

        private final int value;
        view(int value) {
            this.value = value;
        }
    }

    public enum direction {
        LEFT(1) , LEFT_UP(2), UP(3), UP_RIGHT(4), RIGHT(5), RIGHT_DOWN(6), DOWN(7), DOWN_LEFT(8);

        private final int value;
        direction(int value) {
            this.value = value;
        }
    }

    public AutoMove(int x, int y, Image img, int speed, int startImg) {
        super( x, y, img,speed);
        this.startImg = startImg;
    }

    public void moveMinvoRotate() {
        //thay đổi tầm nhìn
        if (canMove(1)) {
            if (checkView == view.LEFT.value) {
                checkView = view.RIGHT.value;
            } else {
                checkView = view.LEFT.value;
            }
        }

        moveIMG();

    }

    public void moveKondoria(int way) {
        canMove(way);

        move(start);
    }

    public void moveMinvo(int way) {
        canMove(way);

        if(checkPlayer()) {
            direction(AIMoveToPlayer());
        }
        else {
            check_direction = 0;
            direction(start);
        }
    }

    public void moveOneal(int way) {
        if (canMove(way)) {
            Random random = new Random();
            int new_speed = Math.abs(random.nextInt()) % 2 + 1;
            setSpeed(new_speed);//System.out.println(new_speed);
        }

        if(checkPlayer()) {
            direction(SimpleMoveToPlayer());
        }
        else {
            check_direction = 0;
            direction(start);
        }
    }

    public void moveBalloom(int way) {
        canMove(way);
        direction(start);
    }

    public void moveGhost(int way) {
        canMove(way);
        if(checkPlayer()) {
            move(SimpleMoveToPlayer());
        }
        else {
            check_direction = 0;
            move(start);
        }
    }

    public void movePass(int way) {
        canMove(way);

        if (checkPlayer()) {
            direction(AIMoveToPlayer());
        } else {
            check_direction = 0;
            move(start);
        }
    }
    @Override
    public boolean canMove(int way) {
        timeNow = System.currentTimeMillis();
        if (timeAgo == 0) {
            timeAgo = System.currentTimeMillis();
            start = 5;
        }
        if(timeNow - timeAgo >= timeWay  && x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            timeAgo = timeNow;
            start = way;
            return true;
        }

        /*if(!checkPlayer()) {
            check_direction = 0;
            direction(start);
        }
        else {
            direction(SimpleMoveToPlayer());
        }*/
        return false;
    }

    //di chuyển không va chạm
    public void move(int way) {
        if (way == move.UP.value && y - speed >= 0) {
            moveUp();
        }

        if (way == move.DOWN.value && y + speed <= Sprite.SCALED_SIZE * (BombermanGame.HEIGHT - 1)) {
            moveDown();
        }

        if (way == move.LEFT.value && x - speed >= 0) {
            moveLeft();
            checkView = view.LEFT.value;
        }

        if (way == move.RIGHT.value && x + speed <= Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1)) {
            moveRight();
            checkView = view.RIGHT.value;
        }

        if (way == move.STOP.value) {
            moveStop();
        }

        moveIMG();
    }

    public void direction(int way) {
        if (way == move.UP.value) {
            if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveUp();
        }

        if (way == move.DOWN.value) {
            if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveDown();
        }

        if (way == move.LEFT.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveLeft();
            checkView = view.LEFT.value;
        }

        if (way == move.RIGHT.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveRight();
            checkView = view.RIGHT.value;
        }

        if (way == move.STOP.value) {
            moveStop();
        }

        moveIMG();
    }


    public int SimpleMoveToPlayer() {
        Random random = new Random();
        int way = 5;
        checkDirection();

        //player bên trái
        if (check_direction == direction.LEFT.value) {
            way = move.LEFT.value;
            if (y > player_y) {
                way = move.UP.value;
            }
            if (y < player_y) {
                way = move.DOWN.value;
            }
        }

        //player góc trên bên trái
        if (check_direction == direction.LEFT_UP.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.LEFT.value;
            } else if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.UP.value;
            }
        }

        //player bên trên
        if (check_direction == direction.UP.value) {
            way = move.UP.value;
            if (x > player_x) {
                way = move.LEFT.value;
            }
        }

        //player góc trên bên phải
        if (check_direction == direction.UP_RIGHT.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.RIGHT.value;
            } else if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.UP.value;
            }
        }

        //player bên phải
        if(check_direction == direction.RIGHT.value) {
            way = move.RIGHT.value;
            if (y > player_y) {
                way = move.UP.value;
            }
            if (y < player_y) {
                way = move.DOWN.value;
            }
        }

        //player góc dưới bên phải
        if (check_direction == direction.RIGHT_DOWN.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.RIGHT.value;
            } else if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.DOWN.value;
            }
        }

        //player bên dưới
        if (check_direction == direction.DOWN.value) {
            way = move.DOWN.value;
            if (x < player_x) {
                way = move.RIGHT.value;
            }
            if (x > player_x) {
                way = move.LEFT.value;
            }
        }

        //player góc dưới bên trái
        if (check_direction ==direction.DOWN_LEFT.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.LEFT.value;
            } else if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.DOWN.value;
            }
        }

        return way;
    }
    private boolean check = false;
    public int AIMoveToPlayer() {
        if (time.checkEnd() && x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            newWay = new Random().nextInt(4);
            time.setStart();
        }
        int way = newWay;
        boolean[][] visit = new boolean[100][100];
        int[][] distance = new int[100][100];
        int[][] map1 = BombermanGame.map.getMap();
        int[][] map = new int[100][100];

        for (int i = 0; i < BombermanGame.WIDTH; i++) {
            for (int j = 0; j < BombermanGame.HEIGHT; j++) {
                if (map1[i][j] > 0) {
                    map[j][i] = 1;
                }
            }
        }

        Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        int player_x = BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE;
        int player_y = BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE;

        queue.add(new Pair<>(player_x, player_y));
        visit[player_x][player_y] = true;
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> value = queue.element();
            int _x = value.getKey();
            int _y = value.getValue();
            queue.poll();

            for (int i = 0; i < 4; ++i) {
                int check_x = _x + toX[i];
                int check_y = _y + toY[i];

                if (check_x > BombermanGame.WIDTH || check_x < 1) continue;
                if (check_y > BombermanGame.HEIGHT || check_y < 1) continue;
                if (map[check_y][check_x] == 1) {
                    continue;
                }

                if(!visit[check_y][check_x]) {
                    distance[check_y][check_x] = distance[_y][_x] + 1;
                    visit[check_y][check_x] = true;
                    queue.add(new Pair<>(check_x, check_y));
                }
            }
        }

        for (int i = 0; i < 4; ++i) {
            int check_x = x / Sprite.SCALED_SIZE + toX[i];
            int check_y = y / Sprite.SCALED_SIZE + toY[i];
            if (check_x > 0 && check_y > 0 && check_x < BombermanGame.WIDTH && check_y < BombermanGame.HEIGHT) {//System.out.println(map[check_y][check_x]);
                if (map[check_y][check_x] == 1) {//System.out.println("true");
                    continue;
                }
                if (distance[check_y][check_x] == distance[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] - 1) {
                    check = true;
                    if (y % Sprite.SCALED_SIZE == 0 && x % Sprite.SCALED_SIZE == 0) {
                        check_map = true;
                        if (i == move.UP.value) {
                            saveWay = move.UP.value;
                            //System.out.println("up");
                        }
                        if (i == move.DOWN.value) {
                            saveWay = move.DOWN.value;
                            //System.out.println("down");
                        }
                        if (i == move.LEFT.value) {
                            saveWay = move.LEFT.value;
                            //System.out.println("left");
                        }
                        if (i == move.RIGHT.value) {
                            saveWay = move.RIGHT.value;
                            //System.out.println("right");
                        }
                    }
                }
            }
        }
        if (check_map) {
            way = saveWay;
        }
        if (check && !check_map) {
            if (x % Sprite.SCALED_SIZE != 0) {
                way = move.RIGHT.value;
            }
            if (y % Sprite.SCALED_SIZE != 0) {
                way = move.UP.value;
            }
        }
        /*for (int i = 0; i < BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                System.out.print(distance[i][j] + " ");
            }System.out.println();
        }System.out.println("true");*/
        return way;
    }

    public void moveIMG() {
        imageTimeNow = System.currentTimeMillis();
        if (imageTimeNow - imageTimeAgo > timeImg) {
            swapImg++;
            imageTimeAgo = System.currentTimeMillis();
        }
        if (swapImg >= 2)
            swapImg = 0;
        if (checkView == view.LEFT.value)
            img = BombermanGame.monster.getList().get(swapImg + startImg).getFxImage();
        if (checkView == view.RIGHT.value)
            img = BombermanGame.monster.getList().get(swapImg + startImg + 4).getFxImage();
        if (checkView == view.DIED.value)
            img = BombermanGame.monster.getList().get(startImg + 3).getFxImage();
    }

    @Override
    public void moveUp() {
        cntDelay++;
        if (cntDelay > TIME_DELAY) {
            if (y - speed > 0) {
                y -= speed;
            }
            cntDelay = 0;
        }
    }

    @Override
    public void moveDown() {
        cntDelay++;
        if (cntDelay > TIME_DELAY) {
            if (y + speed < BombermanGame.HEIGHT * Sprite.SCALED_SIZE) {
                y += speed;
            }
            cntDelay = 0;
        }
    }

    @Override
    public void moveLeft() {
        cntDelay++;
        if (cntDelay > TIME_DELAY) {
            if (x - speed > 0) {
                x -= speed;
            }
            cntDelay = 0;
        }
    }

    @Override
    public void moveRight() {
        cntDelay++;
        if (cntDelay > TIME_DELAY) {
            if (x + speed < BombermanGame.WIDTH * Sprite.SCALED_SIZE) {
                x += speed;
            }
            cntDelay = 0;
        }
    }

    public void moveStop() {

    }

    public boolean canRemove(long time) {
        if (timeDead == 0) {
            timeDead = time;
        }

        long timeRemove = System.currentTimeMillis();

        if (timeRemove - timeDead > TIME_DEAD) {
            return true;
        }
        return false;
    }

    public void DEAD() {
        checkView = view.DIED.value;
        moveStop();
        if (canRemove(System.currentTimeMillis())) {
            setRemove(true);

        }
        moveIMG();
    }

    public boolean checkPlayer() {
        player_x = BombermanGame.bomberman.getX();
        player_y = BombermanGame.bomberman.getY();
        int distance_x = Math.abs(x - player_x);
        int distance_y = Math.abs(y - player_y);

        double distance = Math.sqrt((distance_x * distance_x) + (distance_y * distance_y));
        if (distance <= RANGE) {
            checkDirection();
            return true;
        }
        check_direction = 0;
        return false;
    }

    public void checkDirection() {
        //bên trái
        if (x > player_x + Sprite.DEFAULT_SIZE) {
            //trên
            if (y > player_y + Sprite.SCALED_SIZE) {
                check_direction = direction.LEFT_UP.value;
            }

            //trái
            if (y <= player_y + Sprite.SCALED_SIZE && y + Sprite.SCALED_SIZE >= player_y) {
                check_direction = direction.LEFT.value;
            }

            //dưới
            if (y + Sprite.SCALED_SIZE < player_y) {
                check_direction = direction.DOWN_LEFT.value;
            }
        }

        //trùng tọa độ x
        if (x <= player_x + Sprite.DEFAULT_SIZE && x + Sprite.DEFAULT_SIZE >= player_x) {
            //trên
            if (y > player_y + Sprite.DEFAULT_SIZE) {
                check_direction = direction.UP.value;
            }

            //dưới
            if (y < player_y) {
                check_direction = direction.DOWN.value;
            }
        }

        //bên phải
        if (x + Sprite.DEFAULT_SIZE < player_x) {
            //trên
            if (y > player_y + Sprite.SCALED_SIZE) {
                check_direction = direction.UP_RIGHT.value;
            }

            //phải
            if (y <= player_y + Sprite.SCALED_SIZE && y + Sprite.SCALED_SIZE >= player_y) {
                check_direction = direction.RIGHT.value;
            }

            //dưới
            if (y + Sprite.SCALED_SIZE < player_y) {
                check_direction = direction.RIGHT_DOWN.value;
            }
        }
    }

    @Override
    public void update() {
    }
}
