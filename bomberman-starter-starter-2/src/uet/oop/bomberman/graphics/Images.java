package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Enemy.*;
import uet.oop.bomberman.entities.Collide;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import uet.oop.bomberman.entities.EntityOfMap.*;

/**
 * Cắt và xử lý hình ảnh, các đối tượng hình ảnh được lưu trữ trong List.
 */
public class Images {
    private String path;
    private int x;
    private int y;
    private int width;
    private int height;
    private int[][] map = new int[100][100];
    private int [][] logic_map = new int[100][100];
    private int[] item = new int[20];
    SpriteSheet sheet;
    Images image;
    private List<Sprite> list = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public Images(String path, int x, int y) {
        this.path = path;
        this.x = x;
        this.y = y;
    }

    public void loadImage() {
        int k = 0;
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                list.add(new Sprite(Sprite.DEFAULT_SIZE, j, i, new SpriteSheet(path, Math.max(this.x, this.y) * Sprite.DEFAULT_SIZE), 16, 16));
            }
        }
    }
    public void readMap(String path, Images image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < height; i++) {
                String s;
                s = reader.readLine();
                int left = 0;
                int j = 0;
                while (left < s.length()) {
                    int right = left;
                    while (right < s.length() && s.charAt(right) != ' ') {
                        right++;
                    }
                    map[j][i] = Integer.parseInt(s.substring(left, right));
                    j++;
                    left = right + 1;
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int convert2Dto1D(int row, int column) {
        return height * column + row;
    }

    public void createPadding(Images image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                stillObjects.add(new Grass(i, j));
            }
        }
    }

    public void createMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == 0) {
                    Entity g = new Glass(i, j);
                    stillObjects.add(g);
                } else if (map[i][j] == 1) {
                    Entity w = new Wall(i,j);
                    stillObjects.add(w);
                } else if (map[i][j] == 2) {
                    Entity b = new Brick(i, j);
                    stillObjects.add(b);
                }
            }
        }
    }

    public void createItem() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] != 0) {
                    Entity object = new Item(i, j, image.getList().get(map[i][j] - 1).getFxImage(), map[i][j] - 1);
                    stillObjects.add(object);
                }
            }
        }
    }

    public void createEntity() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == 1) {
                    Entity object = new MinvoRotate(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 2) {
                    Entity object = new RedMinvoRotate(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 3) {
                    Entity object = new Kondoria(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 4) {
                    Entity object = new Minvo(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 5) {
                    Entity object = new Ghost(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 6) {
                    Entity object = new Pass(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                /*if (map[i][j] == 7) {
                    Entity object = new Balloom(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }*/
                if (map[i][j] == 8) {
                    Entity object = new Oneal(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
                if (map[i][j] == 9) {
                    Entity object = new Balloom(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
            }
        }
    }

    public int[][] randomItem(String path, Images image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);

            BufferedReader reader = new BufferedReader(fileReader);
            for (int i = 0; i < 15; i++) {
                String s = reader.readLine();
                item[i] = Integer.parseInt(s);
            }
            reader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        int[][] arr = BombermanGame.map.getMap();
        Random random = new Random();

        for (int numItem = 0; numItem <= 15; numItem++) {
            while (item[numItem] > 0) {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j ++) {
                        if (arr[i][j] == 2 && random.nextInt(10) < 1 && item[numItem] > 0) {
                            this.map[i][j] = numItem + 1;
                            item[numItem]--;
                        }
                    }
                }
            }

        }
        return map;
    }


    public List<Sprite> getList() {
        return list;
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public void clearStillObjects() {
        this.stillObjects.clear();
    }

    public void clearList() {
        this.list.clear();
    }

    public void clearMap() {
        this.map = new int[100][100];
    }

    public int[][] getMap() {
        return map;
    }

    public int[][] getLogic_map() {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                if (this.getMap()[i][j] > 0) {
                    logic_map[j][i] = 1;
                }
            }
        }

        /*for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(logic_map[i][j] + " ");
            }System.out.println();
        }*/
        return logic_map;
    }
    public Images getImage() {
        return this.image;
    }


    public void setMap(int i, int j, int value) {
        this.map[i][j] = value;
    }
}
//1 tuong 2 gach 0 la co
