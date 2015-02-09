package se.liu.ida.denlj069.tddc69.project;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-30
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class Friend implements Runnable {


    private int xDirection, yDirection;
    private Rectangle friendRect;
    private Rectangle detectradius;
    private Thread friend;
    private boolean talking = false;
    private boolean active = false;
    private boolean resting = false;
    private int counter = 0;

    private Quest quest;

    private Actions action;

    public enum Actions{

        WALK, IDLE, STAND;

    }

    public Friend(int x, int y, Actions action){

        friendRect = new Rectangle(x, y, 40, 40);
        detectradius = new Rectangle((x-800)+400,(y-400)+200, 800, 400);

        this.action = action;

        active = true;

        xDirection = 0;
        yDirection = 0;



    }

    public void draw(Graphics2D g){

        g.setColor(Color.green);
        g.fillRect(friendRect.x, friendRect.y, 40, 40);

        //g.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        //g.fillRect(detectradius.x, detectradius.y, 800, 400);


    }

    public void run(){

       try{
        while(active){

            if(hasQuest()){

                if(quest.isCompleted()){

                    quest = null;

                }

            }

            if(!talking){

                switch(action){

                    case WALK:

                        if(!resting){

                            setDirectionPath();


                            long start = System.currentTimeMillis();
                            while(System.currentTimeMillis() < start + 1000){

                                move();

                                friend.sleep(15);

                            }


                            resting = true;
                        }
                        else{

                            friend.sleep(1000);
                            resting = false;

                        }



                        break;
                    case IDLE:

                        if(!resting){

                            if(xDirection == 1){

                                xDirection = -1;

                            }
                            else{

                                xDirection = 1;


                            }


                            long start = System.currentTimeMillis();
                            while(System.currentTimeMillis() < start + 1000){

                                move();

                                friend.sleep(20);

                            }


                            resting = true;
                        }
                        else{

                            friend.sleep(3000);
                            resting = false;

                        }


                        break;
                    case STAND:

                        if(!resting){

                            if(xDirection == 1){

                                xDirection = -1;

                            }
                            else{

                                xDirection = 1;


                            }


                            long start = System.currentTimeMillis();
                            while(System.currentTimeMillis() < start + 1000){

                                friend.sleep(15);

                            }


                            resting = true;
                        }
                        else{

                            friend.sleep(3000);
                            resting = false;

                        }


                        break;



                }
            }


        }
        }catch(Exception e){

            e.printStackTrace();

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

        friendRect.x += xDirection;
        friendRect.y += yDirection;

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


    public boolean hasQuest(){

        return quest != null;

    }

    public void setQuest(Quest q){

        quest = q;

    }

    public Quest getQuest(){

        return quest;

    }

    public boolean detects(Rectangle player){

        return detectradius.intersects(player);

    }

    public void alert(){

        if(!active){

            friend = new Thread(this);
            friend.start();
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

