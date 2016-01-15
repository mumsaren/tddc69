package se.liu.ida.denlj069.tddc69.project.World;

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

    private String mapName;
    private ArrayList<Character> mapInfo;
    private final static int GRID_SQUARE_SIZE = 40;
    private final static int MAP_SECTION_WIDTH = 800;
    private final static int MAP_SECTION_HEIGHT = 400;
    private final static char[] MAP_BUILDING_BLOCKS = {'-', 'x', 'c', 'e', 'w',
    							'i','s', 'm'};
    private Rectangle[] mapCollision;
    private boolean[] mapSolid;
    private int mapWidth;
    private int xDirection, yDirection;
    private List<Item> mapItems;
    private List<Enemy> enemies;
    private List<Friend> friends;

    public World(String mapName) {

        this.mapName = mapName;
	mapInfo = new ArrayList<Character>();

	loadMap();

        mapCollision = new Rectangle[mapInfo.size()];
        mapSolid = new boolean[mapInfo.size()];
        mapItems = new ArrayList<Item>();
        enemies = new ArrayList<Enemy>();
        friends = new ArrayList<Friend>();

        loadInfo();

    }

    private void loadMap() {

	BufferedReader infile = null;
	try {
	    //Why should it be opened in front???
	    infile = new BufferedReader
		    (new FileReader("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/World/" + mapName + ".txt"));
	} catch (FileNotFoundException e) {
	    System.out.println("ERROR WHEN LOADING MAP");
	    e.printStackTrace();
	}

	char c;
        int counter = 0;

	try {
	    assert infile != null;
	    while ((c = (char) infile.read()) != 'E') {
		counter++;
		if (c == 'R') {
		    mapWidth = counter - 1;
		}else {
		    for(char block : MAP_BUILDING_BLOCKS){
			if(c == block){
			    mapInfo.add(c);
			}
		    }
		}
	    }
	} catch (IOException e) {
	    System.out.println("ERROR WHEN READING MAP");
	    e.printStackTrace();
	}

	try {
	    infile.close();
	} catch (IOException e) {
	    System.out.println("ERROR WHEN CLOSING MAP");
	    e.printStackTrace();
	}

    }

    private void loadInfo() {

        int x = 0;
        int y = GRID_SQUARE_SIZE*2;


        for (int i = 0; i < mapInfo.size(); i++) {
            if (x >= mapWidth * GRID_SQUARE_SIZE) {
                x = 0;
                y += GRID_SQUARE_SIZE;
            }
            if (mapInfo.get(i) == '-') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;
            }
            if (mapInfo.get(i) == 'x') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = true;
            }
            if (mapInfo.get(i) == 'c') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;
                mapItems.add(new Coin(x, y));

            }
            if (mapInfo.get(i) == 'e') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Enemy enemy = new Enemy(50, 10, 4, 5, x, y, this);

                enemies.add(enemy);
            }
            if (mapInfo.get(i) == 'w') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, 1, Friend.Actions.WALK);

                friends.add(friend);
            }
            if (mapInfo.get(i) == 'i') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, 2, Friend.Actions.IDLE);

                friends.add(friend);

            }
            if (mapInfo.get(i) == 's') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                Friend friend = new Friend(x, y, 1, Friend.Actions.STAND);

                friends.add(friend);

            }
            if (mapInfo.get(i) == 'm') {
                mapCollision[i] = new Rectangle(x, y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
                mapSolid[i] = false;

                mapItems.add(new CollectibleItem(x, y, "spideregg"));
            }

            x += GRID_SQUARE_SIZE;
        }
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < mapInfo.size(); i++) {
            g.drawRect(mapCollision[i].x, mapCollision[i].y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
            if (mapInfo.get(i) == 'x') {
                g.fillRect(mapCollision[i].x, mapCollision[i].y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
            }
        }
        for (int i = 0; i < mapItems.size(); i++) {

            mapItems.get(i).draw(g);

        }
        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).draw(g);

        }
        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).draw(g);

        }

    }

    public void update() {

	for(int i = 0; i < enemies.size(); i++){

	    enemies.get(i).update();

	}
	for(int i = 0; i < friends.size(); i++){

	    friends.get(i).update();

	}

    }

    public void panWorldXdir() {

        for (int i = 0; i < mapInfo.size(); i++) {
            mapCollision[i].x += MAP_SECTION_WIDTH * xDirection;
        }

        for (int i = 0; i < mapItems.size(); i++) {
            mapItems.get(i).moveX(MAP_SECTION_WIDTH, xDirection);

        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).moveX(MAP_SECTION_WIDTH, xDirection);


        }
        for (int i = 0; i < friends.size(); i++) {

            friends.get(i).moveX(MAP_SECTION_WIDTH, xDirection);

        }

    }

    public void panWorldYdir() {

        for (int i = 0; i < mapInfo.size(); i++) {
            mapCollision[i].y += MAP_SECTION_HEIGHT * yDirection;
        }

        for (int i = 0; i < mapItems.size(); i++) {

            mapItems.get(i).moveY(MAP_SECTION_HEIGHT, yDirection);

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

    public List<Item> getItems() {

        return mapItems;

    }

    public List<Enemy> getEnemies() {

        return enemies;

    }

    public void removeItem(int i) {

        mapItems.remove(i);

    }

    public List<Friend> getFriends() {

        return friends;

    }

    public void addItem(Item item) {

        mapItems.add(item);

    }

    public int getMapWidth(){

        return mapWidth;

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

    public int getMapSectionWidth(){

	return MAP_SECTION_WIDTH;

    }

    public int getMapSectionHeight(){

	return MAP_SECTION_HEIGHT;

    }

}
