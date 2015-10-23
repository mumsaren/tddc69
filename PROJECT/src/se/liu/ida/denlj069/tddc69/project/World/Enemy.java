package se.liu.ida.denlj069.tddc69.project.World;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-23
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class Enemy{

    private Rectangle enemyRect;
    private Rectangle detectRadius;
    private final static int COLLISION_BOX_RADIUS = 4;
    private final static int DETECTION_RADIUS_ORIGIN = 200;
    private final static int DETECTION_RADIUS_WIDTH = 360;
    private final static int DETECTION_RADIUS_HEIGHT = 360;
    private final static int ATTACKING_TIME = 1000;
    private final static int RESTING_TIME = 2000;
    private Rectangle player;
    private int xDirection, yDirection;
    private boolean alerted = false;
    private boolean resting = false;
    private World world;
    private int health;
    private int speed;
    private boolean alive;
    private int pwiX,pwiY = 0;
    private int damage;
    private int expreward;
    private long start;

    private Image enemyLeft, enemyRight;
    private Image enemyImg;


    public Enemy(int health, int damage, int speed, int expreward, int x, int y, World world){

        loadImages();

        enemyImg = enemyLeft;
	this.health = health;
	this.damage = damage;
	this.speed = speed;
	this.expreward = expreward;
        enemyRect = new Rectangle(x, y, enemyImg.getWidth(null), enemyImg.getHeight(null));
        detectRadius = new Rectangle(x-DETECTION_RADIUS_ORIGIN, y-DETECTION_RADIUS_ORIGIN,
				     DETECTION_RADIUS_WIDTH, DETECTION_RADIUS_HEIGHT);
        this.world = world;
        alive = true;

    }

    private void loadImages(){

        enemyLeft = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Monster/spidermonsterL.png").getImage();
        enemyRight = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Monster/spidermonsterR.png").getImage();


    }

    public void update(){

	if(alerted){

	    if(!resting){

		if(start == 0) {

		    start = System.currentTimeMillis();

		}
		if(System.currentTimeMillis() < start + ATTACKING_TIME){

		    findPlayer();
		    setAnimation();
		    if(canMove()){

			move();

		    }

		}
		else{

		    resting = true;
		    start = 0;

		}

	    }
	    else{

		if(start == 0){
		    start = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() >= start + RESTING_TIME){

		    resting = false;
		    start = 0;

		}

	    }

	}

    }

    private void move(){

        enemyRect.x += xDirection*speed;
        enemyRect.y += yDirection*speed;
        detectRadius.x += xDirection*speed;
        detectRadius.y += yDirection*speed;

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

        g.drawImage(enemyImg, enemyRect.x, enemyRect.y, null);

    }

    public boolean detects(Rectangle player){

        return detectRadius.intersects(player);

    }

    public void alert(Rectangle player){


        this.player = player;

        if(!alerted){

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

            enemyImg = enemyRight;

        }
        if(xDirection == -1){

            enemyImg = enemyLeft;

        }

    }

    public int getDamage(){

        return damage;

    }

    public int getExpreward(){

	return expreward;

    }

}
