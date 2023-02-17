package uet.oop.bomberman;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Interaction.Interactive;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 35;
    public static final int HEIGHT = 16;
    public static final int MENU_FRAME = 1;
    private Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + MENU_FRAME));
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private boolean win = false;
    private int playMusicMenu = 0;
    private boolean loseGame = false;
    private boolean canNextLevel = false;
    public static final int MAX_LEVEL = 3;
    public int level = 0;
    public static int cntSound = 0;
    public static int cntPlay = 0;
    private int cntWin = 0;
    private long startLevel = 0;
    private static final long TIME_NEXT_LEVEL = 1500;
    private long time = 0;
    private long timeReal;
    public static Audio audio = new Audio();

    private Interactive interactive = new Interactive();
    private List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> listItem = new ArrayList<>();
    private List<Entity> listPadding = new ArrayList<>();

    public static Images map = new Images("/Images/Map_set1.png", 7, 1);
    public static Images player = new Images("/Player/Player2.png", 3, 5);
    public static Images monster = new Images("/Images/monster.png", 9, 8);
    public static Images item = new Images("/Images/item.png", 4, 4);
    //lớp đệm của map như cỏ
    public static Images padding = new Images("/Images/Padding.png", 1, 1);
    public static final String Map = "bomberman-starter-starter-2/res/TileMap/Map";
    public static final String mapMonster = "bomberman-starter-starter-2/res/TileMap/Tile_monster";
    public static final String mapItem = "bomberman-starter-starter-2/res/TileMap/Tile_item";
    public static final String scoreFile = "bomberman-starter-starter-2/res/Point/score.txt";
    public static Bomber bomberman;

    private StopWatch cntLose = new StopWatch(1987);
    private Text textHeart = new Text(":");
    private Text textTime = new Text("time");
    private Text textLevel = new Text("Level:");
    private Text textScore = new Text("Score :");
    private Text textHighScoreList[] = new Text[Point.TOPSCORE];
    private Menu Menu = new Menu();
    public static Point point = new Point(0, 0);
    private Point highestScore;
    AnimationTimer timer;
    private int cntAddScore = 0;

    // private Stage stage1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        setText();
        mainMenu(stage);
    }

    public void playGame(Stage stage) {
        cntAddScore = 0;
        clear();
        highestScore = point.getListPoint().get(0);
        loseGame = false;
        player.loadImage();
        bomberman = new Bomber(1, 1, player.getList().get(1).getFxImage(), 2);
        bomberman.setHeart(2);
        entities.add(bomberman);
        Group root = new Group();
        root.getChildren().add(canvas);
        //  setText();
        root.getChildren().add(Menu.getHeartPlayer());
        root.getChildren().add(textHeart);
        root.getChildren().add(textTime);
        root.getChildren().add(textLevel);
        root.getChildren().add(textScore);
        root.getChildren().add(Menu.getSoundPlayButton());
        root.getChildren().add(Menu.getPauseButton());
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getBomberControl.getControl(scene);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (getBomberControl.clearEnemy) {
                    for (int i = 1; i < entities.size(); i++) {
                        entities.remove(i);
                    }
                }
                render();
                update();
                if (loseGame) {
                    if (cntSound % 2 == 0) {
                        audio.playAudio(Audio.audio.playerDead.value);
                    }
                    if (point.compare(highestScore) == 1) {
                        if (cntAddScore < 1) {
                            Menu.chucmung(root);
                            audio.stopAudio(Audio.audio.playerDead.value);
                            point.addList(scoreFile);
                            point.topScore(scoreFile);
                            ++cntAddScore;
                        }
                    } else {
                        if (cntAddScore < 1) {
                            point.addList(scoreFile);
                            point.topScore(scoreFile);
                            audio.stopAudio(Audio.audio.playerDead.value);
                        }
                    }
                    if (cntLose.checkEnd()) {
                        audio.stopAudio(Audio.audio.playerDead.value);
                        Menu.setChucmung();
                        root.getChildren().clear();
                        cntLose.setStart();
                        endGame(stage);
                        //  cntLose.setStart();
                    }
                }
                if (canNextLevel && !win) {
                    nextLevel(stage);
                }
                if (win || level > MAX_LEVEL) {
                    if (point.compare(highestScore) == 1) {
                        Menu.chucmung(root);
                        if (cntAddScore < 1) {
                            point.addList(scoreFile);
                            point.topScore(scoreFile);
                        }
                    }
                    ++cntWin;
                    winGame(stage);
                }
             /*   if (canNextLevel) {
                    nextLevel(stage);
                }*/
                Menu.setSound();
                Menu.setPause(root);
            }
        };
        timer.start();
    }

    public void setText() {
        textHighScoreList[0] = new Text("11");
        textHighScoreList[1] = new Text("22");
        textHighScoreList[2] = new Text("33");
        textHighScoreList[0].setFont(Font.font(null, FontWeight.LIGHT, 50));
        //Setting the color of the text
        textHighScoreList[0].setFill(Color.WHITE);
        //setting the position of the text
        textHighScoreList[0].setX(200);
        textHighScoreList[0].setY(260);
        textHighScoreList[1].setFont(Font.font(null, FontWeight.LIGHT, 50));
        //Setting the color of the text
        textHighScoreList[1].setFill(Color.WHITE);
        //setting the position of the text
        textHighScoreList[1].setX(200);
        textHighScoreList[1].setY(370);
        textHighScoreList[2].setFont(Font.font(null, FontWeight.LIGHT, 50));
        //Setting the color of the text
        textHighScoreList[2].setFill(Color.WHITE);
        //setting the position of the text
        textHighScoreList[2].setX(200);
        textHighScoreList[2].setY(480);

        textHeart.setFont(Font.font(null, FontWeight.BOLD, 30));
        //Setting the color of the text
        textHeart.setFill(Color.GREEN);
        //setting the position of the text
        textHeart.setX(45);
        textHeart.setY(550);
        textTime.setFont(Font.font(null, FontWeight.BOLD, 30));
        textTime.setX(200);
        textTime.setY(550);
        textTime.setFill(Color.BLUE);
        textLevel.setFont(Font.font(null, FontWeight.BOLD, 30));
        textLevel.setX(450);
        textLevel.setY(550);
        textLevel.setFill(Color.BLACK);
        textScore.setFont(Font.font(null, FontWeight.BOLD, 30));
        textScore.setX(650);
        textScore.setY(550);
        textScore.setFill(Color.GREY);
    }

    public List<Entity> updateEntity() {
        List<Entity> list = new ArrayList<>();
        list.add(bomberman);
        list.addAll(monster.getStillObjects());
        return list;
    }

    public void load() throws FileNotFoundException {
        padding.loadImage();
        padding.createPadding(padding, WIDTH, HEIGHT);
        listPadding.addAll(padding.getStillObjects()); //tạm thời lấy mỗi cỏ

        map.loadImage();
        map.readMap(Map + this.level + ".txt", map, WIDTH, HEIGHT);
        map.createMap();
        stillObjects.addAll(map.getStillObjects());
        map.getLogic_map();

        item.loadImage();
        //item.readMap(mapItem, item, WIDTH, HEIGHT );
        item.randomItem(mapItem + this.level + ".txt", item, WIDTH, HEIGHT);
        item.createItem();
        listItem.addAll(item.getStillObjects());

        monster.loadImage();
        monster.readMap(mapMonster + this.level + ".txt", monster, WIDTH, HEIGHT);
        monster.createEntity();
        entities.addAll(monster.getStillObjects());
    }

    public void clear() {
        stillObjects.clear();
        listItem.clear();
        entities.clear();

        map.clearMap();
        map.clearStillObjects();
        map.clearList();

        item.clearMap();
        item.clearStillObjects();
        item.clearList();

        monster.clearMap();
        monster.clearStillObjects();
        monster.clearList();

        getBomberControl.bomberDown = false;
        getBomberControl.bomberUp = false;
        getBomberControl.bomberRight = false;
        getBomberControl.bomberLeft = false;
        getBomberControl.bomberSpace = false;
    }

    public void update() {
        if (cntPlay % 2 == 0) {
            time++;
            timeReal = time / 60;
            point.setTime(timeReal);
            textTime.setText("Time: " + timeReal);
            textHeart.setText(": " + bomberman.getHeart());
            textLevel.setText("Level: " + this.level);
            textScore.setText("Score: " + point.getScore());//System.out.println(point.getScore());
            if (bomberman.getHeart() <= 0) {
                loseGame = true;
            }
            entities.forEach(Entity::update);
            interactive.itemHandling();
            interactive.collideWithEnemy(bomberman, entities);
            entities = interactive.monsterDead(bomberman, entities);
            listItem = interactive.removeItem(bomberman, listItem, entities);

            if (interactive.getSwapMap()) {
                bomberman.getBombs().clear();
                point.addScore(this.level * 150);
                ++this.level;
                if (this.level > MAX_LEVEL) {
                    win = true;
                }
                interactive.setSwapMap(false);
                bomberman.setPosition();
                clear();
                canNextLevel = true;
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        listPadding.forEach(g -> g.render(gc));
        listItem.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void mainMenu(Stage stage) {
        point.addList(scoreFile);
        Group root = new Group();
        root.getChildren().add(Menu.getBackgroundMenu());
        root.getChildren().add(Menu.getPlayButton());
        root.getChildren().add(Menu.getInstructionButton());
        root.getChildren().add(Menu.getQuitButton());
        root.getChildren().add(Menu.getVolumOnButton());
        root.getChildren().add(Menu.getHighscoreButtonMain());
        if (cntWin > 0) {
            root.getChildren().add(Menu.getChooselevelButton());
        }
        // Tao scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if (playMusicMenu % 2 == 0) {
            audio.playAudio(Audio.audio.backgroundMusic.value);
        }
        Menu.getVolumOnButton().setOnMouseClicked(mouseEvent -> {
            if (playMusicMenu % 2 == 0) {
                Menu.getVolumOnButton().setImage(Menu.getVolumOff());
            } else {
                Menu.getVolumOnButton().setImage(Menu.getVolumOn());
            }
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            playMusicMenu++;
            audio.stopAudio(Audio.audio.backgroundMusic.value);
            if (playMusicMenu % 2 != 0) {
                audio.stopAudio(Audio.audio.backgroundMusic.value);
            }
            if (playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.backgroundMusic.value);
            }
        });
       /* Menu.getVolumOffButton().setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(Menu.getVolumOffButton());
            root.getChildren().add(Menu.getVolumOnButton());
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            playMusicMenu++;
            audio.playAudio(Audio.audio.backgroundMusic.value);
        });*/
        Menu.getPlayButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            playGame(stage);
        });
        Menu.getInstructionButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            guideMenu(stage);
        });
        Menu.getQuitButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            stage.close();
        });
        Menu.getHighscoreButtonMain().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            highScore(stage);
        });
        Menu.getChooselevelButton().setOnMouseClicked(mouseEvent ->  {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            chooseLevel(stage);
        });
    }

    public void guideMenu(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getInstruction());
        root.getChildren().add(Menu.getBackButton());
        root.getChildren().add(Menu.getNextButton());
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getBackButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            mainMenu(stage);
        });
        Menu.getNextButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            guide2(stage);
        });
    }

    public void guide2(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getHd2Img());
        root.getChildren().add(Menu.getBackButton());
        root.getChildren().add(Menu.getNextButton());
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getBackButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            BombermanGame.audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            guideMenu(stage);
        });
        Menu.getNextButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            BombermanGame.audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            guide3(stage);
        });
    }

    public void guide3(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getHd3Img());
        root.getChildren().add(Menu.getBackButton());
        root.getChildren().add(Menu.getHomeButton());
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getBackButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            BombermanGame.audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            guide2(stage);
        });
        Menu.getHomeButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            BombermanGame.audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            mainMenu(stage);
        });
    }

    public void nextLevel(Stage stage) {
        Group root = new Group();
        Text text = new Text("Level " + (this.level));
        text.setFont(Font.font(null, FontWeight.BOLD, 50));
        text.setFill(Color.WHITE);
        text.setX((WIDTH * Sprite.SCALED_SIZE) / 2 - 150);
        text.setY((HEIGHT * Sprite.SCALED_SIZE) / 2 + 20);
        root.getChildren().add(Menu.getLevel());
        root.getChildren().add(text);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        if (startLevel == 0) {
            startLevel = System.currentTimeMillis();
        }

        long endLevel = System.currentTimeMillis();
        if (this.level > MAX_LEVEL) {
            canNextLevel = false;
            win = true;
        }

        if (endLevel - startLevel >= TIME_NEXT_LEVEL) {
            canNextLevel = false;
            startLevel = 0;
            root.getChildren().clear();
            playGame(stage);
        }
    }

    public void endGame(Stage stage) {
        timer.stop();
        Group root = new Group();
        root.getChildren().add(Menu.getEndGameMenu());
        root.getChildren().add(Menu.getPlayAgainButton());
        root.getChildren().add(Menu.getQuit2Button());
        root.getChildren().add(Menu.getHomeButton());
        root.getChildren().add(Menu.getHighscoreButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            //time = 0;
            loseGame = false;
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            timer.start();
            playGame(stage);
        });
        Menu.getQuit2Button().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            stage.close();
        });
        Menu.getHomeButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            mainMenu(stage);
        });
        Menu.getHighscoreButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            highScore(stage);
        });
    }

    public void winGame(Stage stage) {
        timer.stop();
        Group root = new Group();
        root.getChildren().add(Menu.getWinImg());
        root.getChildren().add(Menu.getQuit2Button());
        root.getChildren().add(Menu.getPlayAgainButton());
        root.getChildren().add(Menu.getHomeButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getQuit2Button().setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            time = 0;
            clear();
            win = false;
            this.level = 0;
            cntAddScore = 0;
            point.clear();
            root.getChildren().clear();
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            timer.start();
            playGame(stage);
        });
        Menu.getHomeButton().setOnMouseClicked(mouseEvent -> {
            time = 0;
            win = false;
            level = 0;
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            mainMenu(stage);
        });
    }

    public void highScore(Stage stage) {
        point.addList(scoreFile);
        // point.topScore(scoreFile);
        Group root = new Group();
        textHighScoreList[0].setText(point.getListScoreString().get(0));
        textHighScoreList[1].setText(point.getListScoreString().get(1));
        textHighScoreList[2].setText(point.getListScoreString().get(2));
        root.getChildren().add(Menu.getHighScoreScreen());
        root.getChildren().add(Menu.getBackButton());
        root.getChildren().add(textHighScoreList[0]);
        root.getChildren().add(textHighScoreList[1]);
        root.getChildren().add(textHighScoreList[2]);
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getBackButton().setOnMouseClicked(mouseEvent -> {
            if (cntSound % 2 == 0 && playMusicMenu % 2 == 0) {
                audio.playAudio(Audio.audio.buttonClick.value);
            }
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            if (!loseGame) {
                mainMenu(stage);
            } else {
                endGame(stage);
            }
        });
    }
    public void chooseLevel(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getBackgroundMenu());
        root.getChildren().add(Menu.getLevel1Button());
        root.getChildren().add(Menu.getLevel2Button());
        root.getChildren().add(Menu.getLevel3Button());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getLevel1Button().setOnMouseClicked(mouseEvent -> {
            root.getChildren().clear();
            this.level = 1;
            timer.start();
            playGame(stage);
        });
        Menu.getLevel2Button().setOnMouseClicked(mouseEvent -> {
            root.getChildren().clear();
            this.level = 2;
            timer.start();
            playGame(stage);
        });
        Menu.getLevel3Button().setOnMouseClicked(mouseEvent -> {
            root.getChildren().clear();
            this.level = 3;
            timer.start();
            playGame(stage);
        });
    }

}

