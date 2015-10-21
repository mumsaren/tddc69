package se.liu.ida.denlj069.tddc69.project;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-23
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class Enemy implements Runnable{

    private Rectangle enemyRect;
    private Rectangle detectRadius;
    private final static int COLLISION_BOX_RADIUS = 4;
    private final static int DETECTION_RADIUS_ORIGIN = 200;
    private final static int DETECTION_RADIUS_WIDTH = 360;
    private final static int DETECTION_RADIUS_HEIGHT = 360;
    private Rectangle player;
    private int xDirection, yDirection;
    private boolean alerted = false;
    private boolean resting = false;
    private World world;
    private Thread enemy;
    private int health;
    private boolean alive;
    private int pwiX,pwiY = 0;
    private int damage;
    private int expreward;

    private Image ENEMY_LEFT, ENEMY_RIGHT;
    private Image enemyimg;


    public Enemy(int health, int damage, int expreward, int x, int y, World world){

        loadImages();

        enemyimg = ENEMY_LEFT;
	this.health = health;
	this.damage = damage;
	this.expreward = expreward;
        enemyRect = new Rectangle(x, y, enemyimg.getWidth(null), enemyimg.getHeight(null));
        detectRadius = new Rectangle(x-DETECTION_RADIUS_ORIGIN, y-DETECTION_RADIUS_ORIGIN,
				     DETECTION_RADIUS_WIDTH, DETECTION_RADIUS_HEIGHT);
        this.world = world;
        alive = true;

    }

    private void loadImages(){

        ENEMY_LEFT = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Monster/spidermonsterL.png").getImage();
        ENEMY_RIGHT = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Monster/spidermonsterR.png").getImage();


    }

    public void run(){
        try{
            while(alerted){

                 if(!resting){

                    long start = System.currentTimeMillis();
                    long end = start + 1000;
                    while(System.currentTimeMillis() < end){

                        findPlayer();
                        setAnimation();

                        if(canMove()){

                            move();

                        }
                        enemy.sleep(5);

                    }

                 resting = true;
                }
                else{

                     enemy.sleep(2000);
                     resting = false;

                 }
          }

        }catch(Exception e){

            e.printStackTrace();

        }



    }


    private void move(){

        enemyRect.x += xDirection;
        enemyRect.y += yDirection;
        detectRadius.x += xDirection;
        detectRadius.y += yDirection;

    }

    private void findPlayer(){


        if(player.x > enemyRect.x){

            xDirection = 1;

        }
        if(player.x < enemyRect.x){

            xDirection = -1;

        }
        if(player.y > enemyRect.y){

            yDirection = 1;

        }
        if(player.y < enemyRect.y){

            yDirection = -1;

        }


    }

    public void draw(Graphics2D g){

        g.drawImage(enemyimg, enemyRect.x, enemyRect.y, null);

    }

    public boolean detects(Rectangle player){

        return detectRadius.intersects(player);

    }

    public void alert(Rectangle player){


        this.player = player;

        if(!alerted){

            enemy = new Thread(this);
            enemy.start();
            alerted = true;

        }

    }

    public void notAlerted(){

        alerted = false;

    }

    private boolean canMove(){

        Rectangle temp = new Rectangle(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);

        temp.x += xDirection;
        temp.y += yDirection;

        for(int i = 0; i < COLLISION_BOX_RADIUS; i++){
            for(int j = 0; j < COLLISION_BOX_RADIUS; j++) {
                int collisionBoxIndex = ((temp.x - pwiX) / world.getGridSquareSize()) - 1 + i
                        + (((temp.y - pwiY) / world.getGridSquareSize()) - 3 + j)* world.getMapWidth();
                if(world.isMapSolid(collisionBoxIndex) && world.getMapCollision(collisionBoxIndex).intersects(temp)){

                    return false;

                }
            }
        }

        return true;

    }

    public void moveX(int distance, int dir){

        enemyRect.x += dir*distance;
        detectRadius.x += dir*distance;
        pwiX += dir*distance;

    }

    public void moveY(int distance, int dir){

        enemyRect.y += dir*distance;
        detectRadius.y += dir*distance;
        pwiY += dir*distance;

    }

    public boolean hits(Rectangle player){

        return enemyRect.intersects(player);

    }

    public void hurt(int damage, int xdir, int ydir, int force){

        health -= damage;

        if(health <= 0){

            System.out.println("död!");
            alive = false;
            generateLoot();

        }

        if(canMove()){
            enemyRect.x += force*xdir;
            detectRadius.x += force*xdir;
            enemyRect.y += force*ydir;
            detectRadius.y += force*ydir;

        }
    }

    public boolean isAlive(){

        return alive;

    }

    private void generateLoot(){

        Item loot = new Coin(enemyRect.x, enemyRect.y);
        world.addItem(loot);

    }

    private void setAnimation(){

        if(xDirection == 1){

            enemyimg = ENEMY_RIGHT;

        }
        if(xDirection == -1){

            enemyimg = ENEMY_LEFT;

        }

    }

    public int getDamage(){

        return damage;

    }

    public int getExpreward(){

	return expreward;

    }

}
