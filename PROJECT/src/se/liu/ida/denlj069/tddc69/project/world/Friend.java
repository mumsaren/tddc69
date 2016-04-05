package se.liu.ida.denlj069.tddc69.project.world;

import java.awt.*;

/**
 * The friendly npc's of the game world.
 *
 * Friends are the questgivers (see QuestSystem) and the player interracts with them by talking.
 * They can move in the world in three different ways (see enum Actions).
 * They consist of two Rectangle objects: One visable main body (friendRect) and one non-visible detection box
 * (detectradius) which activates the update thread.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-30
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class Friend {

    /**
     * The dimension of the friendRect
     */
    private static final int FRIEND_RECT_WIDTH = 40;
    private static final int FRIEND_RECT_HEIGHT = 40;
    /**
     * The dimension of the detectradius
     */
    private static final int DETECTION_BOX_WIDTH = 800;
    private static final int DETECTION_BOX_HEIGHT = 400;
    /**
     * Constants for how long (in milliseconds) the friend should move
     * and rest.
     */
    private static final int MOVE_TIME = 1000;
    private static final int RESTING_TIME = 2000;
    /**
     * Variables for the friend's moving direction in the world
     */
    private int xDirection, yDirection;
    /**
     * The main two Rectangle objects
     */
    private Rectangle friendRect;
    private Rectangle detectradius;
    /**
     * Variable for talk checking
     */
    private boolean talking = false;
    /**
     * Variables used for setting the friend to active (when player collides with detectradius)
     * and when to rest from moving.
     */
    private boolean active = false;
    private boolean resting = false;
    /**
     * Counter for changing the direction (when Action = WALK)
     */
    private int counter = 0;
    /**
     * How fast the friend moves
     */
    private int speed;
    /**
     * Time stamp for checking moving times in the update method
     */
    private long start;

    /**
     * The friend's action
     */
    private Actions action;

    public enum Actions{

	/**
	 * bajs
	 */
	WALK, IDLE, STAND

    }

    public Friend(int x, int y, int speed, Actions action){

        friendRect = new Rectangle(x, y, FRIEND_RECT_WIDTH, FRIEND_RECT_HEIGHT);
        detectradius = new Rectangle((x-DETECTION_BOX_WIDTH)+(DETECTION_BOX_WIDTH/2),
				     (y-DETECTION_BOX_HEIGHT)+(DETECTION_BOX_HEIGHT/2),
				     DETECTION_BOX_WIDTH, DETECTION_BOX_HEIGHT);
        this.action = action;
	this.speed = speed;
        active = true;
        xDirection = 0;
        yDirection = 0;

    }

    public void draw(Graphics2D g){

        g.setColor(Color.green);
        g.fillRect(friendRect.x, friendRect.y, FRIEND_RECT_WIDTH, FRIEND_RECT_HEIGHT);

    }

    public void update(){

	if(active){
	    if(!talking){
		if(!resting){
		    //Personally thinks it looks better with a switch case
		    switch(action){

			case WALK:

			    if(start == 0) {
				start = System.currentTimeMillis();
			    }
			    if(System.currentTimeMillis() < start + MOVE_TIME){

				move();

			    }
			    else{

				setDirectionPath();
				resting = true;
				start = 0;
			    }
			    break;
		    	case IDLE:


			    if(start == 0){

				start = System.currentTimeMillis();

			    }
			    if(System.currentTimeMillis() < start + MOVE_TIME){

				move();

			    }
			    else{

				if(xDirection == 1){

				    xDirection = -1;

				}
				else{

				    xDirection = 1;
				}
				resting = true;
				start = 0;

			    }

			    break;
			case STAND:

    				if(start == 0){

				    start = System.currentTimeMillis();

				}
    				if(System.currentTimeMillis() >= start + MOVE_TIME){

				    if(xDirection == 1){

					xDirection = -1;

				    }
				    else{

					xDirection = 1;


				    }
				    resting = true;
				    start = 0;

				}

			    break;

		    }

		}else{

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

    }

    public void moveX(int distance, int dir){

        friendRect.x += distance*dir;
        detectradius.x += distance*dir;

    }

    public void moveY(int distance, int dir){

        friendRect.y += distance*dir;
        detectradius.y += distance*dir;

    }

    public boolean intersects(Rectangle player){

        return friendRect.intersects(player);

    }

    private void move(){

        friendRect.x += xDirection*speed;
        friendRect.y += yDirection*speed;

    }

    private void setDirectionPath(){

        counter++;

        if(counter == 1){

            xDirection = 1;
            yDirection = 0;

        }
        else if(counter == 2){

            xDirection = 0;
            yDirection = -1;

        }
        else if(counter == 3){

            xDirection = -1;
            yDirection = 0;

        }
        else if(counter == 4){

            xDirection = 0;
            yDirection = 1;

            counter = 0;

        }



    }

    public boolean detects(Rectangle player){

        return detectradius.intersects(player);

    }

    public void alert(){

        if(!active){

            active = true;

        }

    }

    public void notAlerted(){

        active = false;

    }


    public void talk(){

        talking = true;

    }

    public void stopTalking(){

        talking = false;

    }

    public boolean isTalking(){

        return talking;

    }


}

