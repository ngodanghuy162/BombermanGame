package uet.oop.bomberman;

import uet.oop.bomberman.entities.Interaction.Collision;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Point {
    public static final int TOPSCORE = 3;
    private int score = 0;
    private long time = 0;


    public Point() {
    }

    public Point(int score, long time) {
        this.score = score;
        this.time = time;
    }
    private int demChuSo(int k) {
        if (k <= 0) {
            return 1;
        }
        int cnt = 0;
        while (k > 0) {
            k /= 10;
            cnt++;
        }
        return cnt;
    }
    public void setTime(long time) {
        this.time = time;
    }
    private List<String> listScoreString = new ArrayList<>();
    public List<Point> listPoint = new ArrayList<>();

    public List<String> getListScoreString() {
        return listScoreString;
    }

    public List<Point> getListPoint() {
        return listPoint;
    }

    public String toString() {
        String s = new String("");
        s = s + "         " +this.score;
        for (int i = 0; i < 9 - demChuSo(score); i++) {
            s += "  ";
        }
        s = s + this.time + "\n";
        return s;
        //return ("         "+this.score + "            "+this.time + "\n");
    }


    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public long getTime() {
        return this.time;
    }

    public void addList(String path) {
        listPoint.clear();
        listScoreString.clear();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < TOPSCORE; i++) {
                int _score;
                int _time;
                _score = Integer.parseInt(reader.readLine());
                _time = Integer.parseInt(reader.readLine());
                listPoint.add(new Point(_score, _time));
            }
            for (int i = 0; i < TOPSCORE; i++) {
                listScoreString.add(listPoint.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void topScore(String path) {
        listPoint.add(new Point(this.score, this.time));
        sortScore();
        String s = "";
        for (int i = 0; i < TOPSCORE; i++) {
            s = s + listPoint.get(i).getScore() + "\n" + listPoint.get(i).getTime() + "\n";
        }
        writeScore(path, s);
    }
        public void sortScore() {
            Collections.sort(listPoint, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.getScore() < o2.getScore()){
                        return 1;
                    }

                    if (o1.getScore() > o2.getScore()) {
                        return -1;
                    }

                    if (o1.getTime() > o2.getTime()) {
                        return 1;
                    }

                    if (o1.getTime() < o2.getTime()) {
                        return -1;
                    }

                    return 0;
                }
            });
        }

    public void writeScore(String path, String text) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int compare(Point p) {
        if (this.score < p.getScore()) {
            return -1;
        } else if (this.score > p.getScore()) {
            return 1;
        } else {
            if (this.time > p.getTime()) {
                return -1;
            }

            if (this.time < p.getTime()) {
                return 1;
            }
        }
        return 0;
    }

    public void clear() {
        this.score = 0;
        this.time = 0;
    }
}
