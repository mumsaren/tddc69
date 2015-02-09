package se.liu.ida.denlj069.tddc69.project;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-09
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class World {

    private String mapname;
    private ArrayList<Character> mapinfo;
    protected Rectangle[] mapcollision;
    protected boolean[] ismapSolid;
    private int mapwidth;
    private int xDirection, yDirection;
    private ArrayList<Item> mapitems;
    private ArrayList<Enemy> enemies;
    private ArrayList<Friend> friends;

    private Quest q001,q002;


    public World(String mapname) {

        this.mapname = mapname;

        try {
            loadMap();
        } catch (IOException e) {
            System.out.println("ERROR WHEN LOADING MAP");
            e.printStackTrace();
        }

        System.out.println(mapwidth);
        System.out.println(mapinfo.size());

        mapcollision = new Rectangle[mapinfo.size()];
        ismapSolid = new boolean[mapinfo.size()];
        mapitems = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        friends = new ArrayList<Friend>();

        loadInfo();
        loadQuests();

    }

    private void loadMap() throws IOException {

        BufferedReader infile = new BufferedReader
                (new FileReader("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/" + mapname + ".txt"));

        char c;
        int counter = 0;
        mapinfo = new ArrayList<Character>();

        while ((c = (char) infile.read()) != 'E') {
            counter++;
            if (c == 'R') {
                mapwidth = counter - 1;
            } else if (c == '-' || c == 'x' || c == 'c' || c == 'e' || c == 'w'
                    || c == 'i' || c == 's' || c == 'm') {
                mapinfo.add(c);
            }
        }

        infile.close();

    }

    private void loadInfo() {

        int x = 0;
        int y = 80;


        for (int i = 0; i < mapinfo.size(); i++) {
            if (x >= mapwidth * 40) {
                x = 0;
                y += 40;
            }
            if (mapinfo.get(i) == '-') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;
            }
            if (mapinfo.get(i) == 'x') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = true;
            }
            if (mapinfo.get(i) == 'c') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;
                mapitems.add(new Coin(x, y));

            }
            if (mapinfo.get(i) == 'e') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;

                Enemy enemy = new Enemy(x, y, this);

                enemies.add(enemy);
            }
            if (mapinfo.get(i) == 'w') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.WALK);

                friends.add(friend);
            }
            if (mapinfo.get(i) == 'i') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.IDLE);

                friends.add(friend);

            }
            if (mapinfo.get(i) == 's') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.STAND);

                friends.add(friend);

            }
            if (mapinfo.get(i) == 'm') {
                mapcollision[i] = new Rectangle(x, y, 40, 40);
                ismapSolid[i] = false;

                mapitems.add(new CollectibleItem(x, y, "spideregg"));
            }

            x += 40;
        }
    }

    private void loadQuests() {

        //quest1
        q001 = new Quest(50, 20, "Avenge Steven");
        q001.addGoal(new Talk(friends.get(2)));
        q001.addGoal(new Kill(enemies.get(0)));
        q001.addGoal(new Talk(friends.get(2)));
        friends.get(2).setQuest(q001);

        //quest2
        q002 = new Quest(100,40, "Collect 2x spider eggs");
        q002.addGoal(new Talk(friends.get(0)));
        q002.addGoal(new Collect(2, "spideregg"));
        q002.addGoal(new Talk(friends.get(0)));
        friends.get(0).setQuest(q002);

    }

    private void updateQuests() {

        q001.update();
        q002.update();

    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < mapinfo.size(); i++) {
            g.drawRect(mapcollision[i].x, mapcollision[i].y, 40, 40);
            if (mapinfo.get(i) == 'x') {
                g.fillRect(mapcollision[i].x, mapcollision[i].y, 40, 40);
            }
        }
        for (int i = 0; i < mapitems.size(); i++) {

            mapitems.get(i).draw(g);

        }
        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).draw(g);

        }
        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).draw(g);

        }

    }

    public void update() {

        updateQuests();

    }

    public void panWorldXdir() {

        for (int i = 0; i < mapinfo.size(); i++) {
            mapcollision[i].x += 800 * xDirection;
        }

        for (int i = 0; i < mapitems.size(); i++) {
            mapitems.get(i).moveX(800, xDirection);

        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).moveX(800, xDirection);


        }
        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).moveX(800, xDirection);

        }

    }

    public void panWorldYdir() {

        for (int i = 0; i < mapinfo.size(); i++) {
            mapcollision[i].y += 400 * yDirection;
        }

        for (int i = 0; i < mapitems.size(); i++) {

            mapitems.get(i).moveY(400, yDirection);

        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).moveY(400, yDirection);

        }

        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).moveY(400, yDirection);

        }

    }

    public void setXDirection(int d) {
        xDirection = d;
    }

    public void setYDirection(int d) {
        yDirection = d;
    }

    public ArrayList<Item> getItems() {

        return mapitems;

    }

    public ArrayList<Enemy> getEnemies() {

        return enemies;

    }

    public void removeItem(int i) {

        mapitems.remove(i);

    }

    public ArrayList<Friend> getFriends() {

        return friends;

    }

    public void addItem(Item item) {

        mapitems.add(item);

    }

    public int getMapWidth(){

        return mapwidth;

    }

    public String getMapname(){

        return mapname;

    }

}
