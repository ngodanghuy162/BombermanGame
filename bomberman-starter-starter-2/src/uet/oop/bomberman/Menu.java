package uet.oop.bomberman;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Menu {
    private int timeRenderChucMung = 1000;
    private int cntPause = 0;
    private int cntChucmung = 0;
    private Image number1 = new Image("MenuImage/1.png");
    private Image number2 = new Image("MenuImage/2.png");
    private Image number3 = new Image("MenuImage/3.png");
    private ImageView level1Button = new ImageView(number1);
    private ImageView level2Button = new ImageView(number2);
    private ImageView level3Button = new ImageView(number3);
    private Image chooseLevelIMG = new Image("MenuImage/choselevel.png");
    private ImageView chooselevelButton = new ImageView(chooseLevelIMG);
    private Image buttonhighscoreMenu = new Image("MenuImage/button_score.png");
    private ImageView highscoreButtonMain = new ImageView(buttonhighscoreMenu);
    private Image backgroundMenuImage = new Image("MenuImage/background.png");
    private ImageView backgroundMenu = new ImageView(backgroundMenuImage);
    private Image playImage = new Image("MenuImage/button_play.png");
    private ImageView playButton = new ImageView(playImage);
    private Image instructionImage = new Image("MenuImage/button_instructions.png");
    private ImageView instructionButton = new ImageView(instructionImage);
    private Image quit = new Image("MenuImage/button_quit.png");
    private ImageView quitButton = new ImageView(quit);
    private Image guideImage = new Image("MenuImage/huongdan.png");
    private ImageView instruction = new ImageView(guideImage);
    private Image endGameImage = new Image("MenuImage/end.png");
    private ImageView endGameMenu = new ImageView(endGameImage);
    private Image playAgain = new Image("MenuImage/playag.png");
    private ImageView playAgainButton = new ImageView(playAgain);
    private Image levelimg = new Image("MenuImage/level.png");
    private Image quit2 = new Image("MenuImage/quit.png");
    private ImageView quit2Button = new ImageView(quit2);
    private ImageView level = new ImageView(levelimg);
    private Image back = new Image("MenuImage/button_back.png");
    private ImageView backButton = new ImageView(back);
    private Image next = new Image("MenuImage/next.png");
    private ImageView nextButton = new ImageView(next);
    private Image volumOn = new Image("MenuImage/button_music1.png");
    private Image volumOff = new Image("MenuImage/button_music2.png");
    private ImageView volumOnButton = new ImageView(volumOn);
    private ImageView volumOffButton = new ImageView(volumOff);
    private Image continueIMG = new Image("MenuImage/continue.png");

    private Image pauseIMG = new Image("MenuImage/pau.png");
    private ImageView pauseButton = new ImageView(continueIMG);
    private Image heart = new Image("MenuImage/green.png");
    private Image winImage = new Image("MenuImage/win.jpg");
    private Image home = new Image("MenuImage/home.jpg");
    private ImageView homeButton = new ImageView(home);
    private Image hd2 = new Image("MenuImage/q1.png");
    private Image hd3 = new Image("MenuImage/q2.png");
    private ImageView hd2Img = new ImageView(hd2);
    private Image soundOnPlay = new Image("MenuImage/soundon.jpg");

    private Image soundOffPlay = new Image("MenuImage/soundoff.jpg");
    private Image pauseIMGScreen = new Image("MenuImage/pause.jpg");
    private ImageView pauseScreen = new ImageView(pauseIMGScreen);
    private Image highscore = new Image("MenuImage/diemcao.png");
    private Image scoreButton = new Image("MenuImage/button_diemcao.png");
    private ImageView highscoreButton = new ImageView(scoreButton);
    private ImageView highScoreScreen = new ImageView(highscore);
    private ImageView soundPlayButton = new ImageView(soundOnPlay);
    private ImageView hd3Img = new ImageView(hd3);
    private ImageView winImg = new ImageView(winImage);
    private ImageView heartPlayer = new ImageView(heart);
    private Image chucmungdiemcao = new Image("MenuImage/highscore.jpg");
    private ImageView chucmungHighscoreScreen = new ImageView(chucmungdiemcao);

    public Menu() {
        level1Button.setX(100);
        level1Button.setY(400);
        level2Button.setX(200);
        level2Button.setY(400);
        level3Button.setX(300);
        level3Button.setY(400);
        instructionButton.setX(100);
        instructionButton.setY(380);
        playButton.setX(300);
        playButton.setY(300);
        quitButton.setX(300);
        quitButton.setY(460);
        highscoreButtonMain.setX(500);
        highscoreButtonMain.setY(380);
        chooselevelButton.setX(300);
        chooselevelButton.setY(200);
        playAgainButton.setX(150);
        playAgainButton.setY(400);
        quit2Button.setX(850);
        quit2Button.setY(400);
        heartPlayer.setX(10);
        heartPlayer.setY(525);
        backButton.setY(5);
        backButton.setX(5);
        nextButton.setY(5);
        nextButton.setX(1060);
        volumOnButton.setX(1040);
        volumOnButton.setY(5);
        volumOffButton.setX(1040);
        volumOffButton.setY(5);
        homeButton.setX(1025);
        homeButton.setY(10);
        soundPlayButton.setX(1050);
        soundPlayButton.setY(515);
        pauseButton.setX(980);
        pauseButton.setY(515);
        highscoreButton.setX(500);
        highscoreButton.setY(400);
    }

    public Image getVolumOff() {
        return volumOff;
    }

    public Image getVolumOn() {
        return volumOn;
    }

    public ImageView getHighscoreButtonMain() {
        return highscoreButtonMain;
    }

    public ImageView getChooselevelButton() {
        return chooselevelButton;
    }

    public ImageView getLevel1Button() {
        return level1Button;
    }

    public ImageView getLevel2Button() {
        return level2Button;
    }

    public ImageView getLevel3Button() {
        return level3Button;
    }

    public ImageView getHighscoreButton() {
        return highscoreButton;
    }

    public ImageView getChucmungHighscore() {
        return chucmungHighscoreScreen;
    }

    public ImageView getBackButton() {
        return backButton;
    }

    public ImageView getSoundPlayButton() {
        return soundPlayButton;
    }

    public ImageView getVolumOffButton() {
        return volumOffButton;
    }

    public ImageView getVolumOnButton() {
        return volumOnButton;
    }

    public ImageView getBackgroundMenu() {
        return backgroundMenu;
    }

    public ImageView getEndGameMenu() {
        return endGameMenu;
    }

    public ImageView getInstructionButton() {
        return instructionButton;
    }

    public ImageView getPlayButton() {
        return playButton;
    }

    public ImageView getInstruction() {
        return instruction;
    }

    public ImageView getQuitButton() {
        return quitButton;
    }

    public ImageView getPlayAgainButton() {
        return playAgainButton;
    }

    public ImageView getLevel() {
        return level;
    }


    public ImageView getHeartPlayer() {
        return this.heartPlayer;
    }

    public ImageView getWinImg() {
        return winImg;
    }

    public ImageView getQuit2Button() {
        return quit2Button;
    }

    public ImageView getHomeButton() {
        return homeButton;
    }

    public ImageView getHd2Img() {
        return hd2Img;
    }

    public ImageView getNextButton() {
        return nextButton;
    }

    public ImageView getHd3Img() {
        return hd3Img;
    }

    public ImageView getPauseButton() {
        return pauseButton;
    }

    public ImageView getHighScoreScreen() {
        return highScoreScreen;
    }

    public void setSound() {
        soundPlayButton.setOnMouseClicked(mouseEvent -> {
            BombermanGame.cntSound++;
        });
        if (BombermanGame.cntSound % 2 == 0) {
            soundPlayButton.setImage(soundOnPlay);
        } else {
            soundPlayButton.setImage(soundOffPlay);
        }
    }

    public void setPause(Group group) {
        pauseButton.setOnMouseClicked(mouseEvent -> {
            BombermanGame.cntPlay++;
        });
        if (BombermanGame.cntPlay % 2 == 0) {
            group.getChildren().remove(pauseScreen);
            pauseButton.setImage(continueIMG);
            cntPause = 0;
        } else {
            if (cntPause < 1) {
                group.getChildren().add(pauseScreen);
                cntPause++;
            }
            pauseButton.setImage(pauseIMG);
        }
    }
    public void setChucmung() {
        cntChucmung = 0;
        timeRenderChucMung = 1000;
    }

    public void chucmung(Group group) {
        if (cntChucmung < 1 ) {
            group.getChildren().add(chucmungHighscoreScreen);
            cntChucmung++;
        } else {
            timeRenderChucMung--;
            if (timeRenderChucMung <= 0) {
                group.getChildren().remove(chucmungHighscoreScreen);
            }
        }

    }
}


