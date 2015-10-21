package se.liu.ida.denlj069.tddc69.project;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

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
    private final static int GRID_SQUARE_SIZE = 40;
    private final static int MAP_SECTION_WIDTH = 800;
    private final static int MAP_SECTION_HEIGHT = 400;
    private Rectangle[] mapCollision;
    private boolean[] mapSolid;
    private int mapwidth;
    private int xDirection, yDirection;
    private List<Item> mapitems;
    private List<Enemy> enemies;
    private List<Friend> friends;

    public World(String mapname) {

        this.mapname = mapname;

        try {
            loadMap();
        } catch (IOException e) {
            System.out.println("ERROR WHEN LOADING MAP");
            e.printStackTrace();
        }

        mapCollision = new Rectangle[mapinfo.size()];
        mapSolid = new boolean[mapinfo.size()];
        mapitems = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        friends = new ArrayList<Friend>();

        loadInfo();

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
        int y = GRID_SQUARE_SIZE*2;


        for (int i = 0; i < mapinfo.size(); i++) {
            if (x >= mapwidth * GRID_SQUARE_SIZE) {
                x = 0;
                y += GRID_SQUARE_SIZE;
            }
            if (mapinfo.get(i) == '-') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;
            }
            if (mapinfo.get(i) == 'x') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = true;
            }
            if (mapinfo.get(i) == 'c') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;
                mapitems.add(new Coin(x, y));

            }
            if (mapinfo.get(i) == 'e') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Enemy enemy = new Enemy(50, 10, 5, x, y, this);

                enemies.add(enemy);
            }
            if (mapinfo.get(i) == 'w') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.WALK);

                friends.add(friend);
            }
            if (mapinfo.get(i) == 'i') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.IDLE);

                friends.add(friend);

            }
            if (mapinfo.get(i) == 's') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, Friend.Actions.STAND);

                friends.add(friend);

            }
            if (mapinfo.get(i) == 'm') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                mapitems.add(new CollectibleItem(x, y, "spideregg"));
            }

            x += GRID_SQUARE_SIZE;
        }
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < mapinfo.size(); i++) {
            g.drawRect(mapCollision[i].x, mapCollision[i].y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
            if (mapinfo.get(i) == 'x') {
                g.fillRect(mapCollision[i].x, mapCollision[i].y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
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



    }

    public void panWorldXdir() {

        for (int i = 0; i < mapinfo.size(); i++) {
            mapCollision[i].x += MAP_SECTION_WIDTH * xDirection;
        }

        for (int i = 0; i < mapitems.size(); i++) {
            mapitems.get(i).moveX(MAP_SECTION_WIDTH, xDirection);

        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).moveX(MAP_SECTION_WIDTH, xDirection);


        }
        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).moveX(MAP_SECTION_WIDTH, xDirection);

        }

    }

    public void panWorldYdir() {

        for (int i = 0; i < mapinfo.size(); i++) {
            mapCollision[i].y += MAP_SECTION_HEIGHT * yDirection;
        }

        for (int i = 0; i < mapitems.size(); i++) {

            mapitems.get(i).moveY(MAP_SECTION_HEIGHT, yDirection);

        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).moveY(MAP_SECTION_HEIGHT, yDirection);

        }

        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).moveY(MAP_SECTION_HEIGHT, yDirection);

        }

    }

    public void setXDirection(int dir) {
        xDirection = dir;
    }

    public void setYDirection(int dir) {
        yDirection = dir;
    }

    public java.util.List<Item> getItems() {

        return mapitems;

    }

    public List<Enemy> getEnemies() {

        return enemies;

    }

    public void removeItem(int i) {

        mapitems.remove(i);

    }

    public List<Friend> getFriends() {

        return friends;

    }

    public void addItem(Item item) {

        mapitems.add(item);

    }

    public int getMapWidth(){

        return mapwidth;

    }

    public Rectangle getMapCollision(int pos){

	return mapCollision[pos];

    }

    public boolean isMapSolid(int pos){

	return mapSolid[pos];

    }

    public int getGridSquareSize(){

	return GRID_SQUARE_SIZE;

    }

}
