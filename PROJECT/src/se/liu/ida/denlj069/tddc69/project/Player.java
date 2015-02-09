package se.liu.ida.denlj069.tddc69.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-08
 * Time: 01:23
 * To change this template use File | Settings | File Templates.
 */
public class Player{

    private int xDirection, yDirection, speed, faceDirectionX, faceDirectionY;
    private final int collisionboxradius = 4;

    private Rectangle playerRect;
    private Rectangle attackRadius;


    private World world;

    private Inventory inventory;

    private int health;
    private int cash;
    private int exp;

    private boolean hurted = false;

    private Image PLAYER_LEFT, PLAYER_RIGHT, PLAYER_FRONT, PLAYER_BACK,
                  PLAYER_PUNCHL, PLAYER_PUNCHR, PLAYER_PUNCHB, PLAYER_PUNCHF;

    private Image playerImg;

    private boolean attacking = false;
    private boolean talking = false;
    private boolean moving = false;
    private boolean using = false;

    private int damage = 10;
    private int force = 50;
    private int hurtvalue = 0;

    private boolean doingquest = false;
    private Quest currentquest;
    private ArrayList<Quest> completedquests;

    private long start;

    private int pwiX = 0;
    private int pwiY = 0;


    public Player(World world){

        this.world = world;

        health = 100;
        cash = 0;
        exp = 0;
        speed = 4;


        inventory = new Inventory(5, 10);

        loadImages();

        playerImg = PLAYER_FRONT;

        playerRect = new Rectangle(160, 160, playerImg.getWidth(null)*2, playerImg.getHeight(null)*2);
        attackRadius = new Rectangle(160-9, 160-6, playerRect.width + 8, playerRect.height + 12);

        completedquests = new ArrayList<Quest>();

    }

    private void loadImages(){

        PLAYER_LEFT = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/playerLS.png").getImage();
        PLAYER_RIGHT = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/playerRS.png").getImage();
        PLAYER_BACK = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/playerBS.png").getImage();
        PLAYER_FRONT = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/playerFS.png").getImage();
        PLAYER_PUNCHL = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/punchLS.png").getImage();
        PLAYER_PUNCHR = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/punchRS.png").getImage();
        PLAYER_PUNCHB = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/punchBSR.png").getImage();
        PLAYER_PUNCHF = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Player/punchFSR.png").getImage();

    }

    public void update(){


        if(doingquest){

            if(currentquest.isCompleted()){

                cash += currentquest.getCashreward();
                exp += currentquest.getExpreward();
                doingquest = false;
                completedquests.add(currentquest);

            }


        }


        if(attacking){

            if(System.currentTimeMillis() >= start + 700){

                attacking = false;
                setAnimation();
            }

            checkCollisions();

        }
        else{

            if(hurted){

                if(System.currentTimeMillis() >= start + 2000){

                    hurted = false;

                }
            }

            setAnimation();

            if(moving){
                if(canMove()) {
                    move();
                }

            }

            checkCollisions();


            }
    }

    public void draw(Graphics2D g){


        if(hurted) {

            g.setComposite(AlphaComposite.SrcOver.derive(0.7f));

        }
/*
        int index;
        for(int i = 0; i < collisionboxradius; i++){
            for(int j = 0; j < collisionboxradius; j++) {
                index = ((playerRect.x + pwiX) / 40) - 1 + i
                        + (((playerRect.y + pwiY) / 40) - 3 + j)* world.getMapWidth();
                g.setColor(Color.red);
                g.fillRect(world.mapcollision[index].x, world.mapcollision[index].y, world.mapcollision[index].width, world.mapcollision[index].height);
            }
        }

*/



        g.drawImage(playerImg, playerRect.x, playerRect.y,
                    playerImg.getWidth(null)*2, playerImg.getHeight(null)*2, null);


    }

    private void move(){

        playerRect.x += speed*xDirection;
        playerRect.y += speed*yDirection;
        attackRadius.x += speed*xDirection;
        attackRadius.y += speed*yDirection;

    }

    public void stopMove(){

       xDirection = 0;
       yDirection = 0;
       moving = false;

    }

    private void checkCollisions(){

        //Panorering av kartan
        if(playerRect.x > 780){

            world.panWorldXdir();
            playerRect.x = 20;
            attackRadius.x = 20;
            pwiX += 800*xDirection;

        }
        if(playerRect.x < 20){

            world.panWorldXdir();
            playerRect.x = 780;
            attackRadius.x = 780;
            pwiX += 800*xDirection;

        }
        if(playerRect.y > 440){

            world.panWorldYdir();
            playerRect.y = 80;
            attackRadius.y = 80;
            pwiY += 400*yDirection;

        }
        if(playerRect.y < 80){

            world.panWorldYdir();
            playerRect.y = 420;
            attackRadius.y = 420;
            pwiY += 400*yDirection;


        }
        //kolla kollision med föremål
        for(int i = 0; i < world.getItems().size(); i++){

            Item item = world.getItems().get(i);

            if(item.intersects(playerRect)){

                if(item.getType().equals("collectible")){

                    if(using){
                        inventory.addItem(item);
                        if(doingquest && item.getName().equals(currentquest.getCurrentCoal().getName())){

                            currentquest.getCurrentCoal().decreaseAmount();

                        }
                        world.removeItem(i);
                    }

                }
                else if(item.getType().equals("coin")){

                    world.removeItem(i);
                    cash++;

                }
            }

        }
        //kollision med fiender
        for(int i = 0; i < world.getEnemies().size(); i++){

            Enemy enemy = world.getEnemies().get(i);

            if(enemy.detects(playerRect)){

                   enemy.alert(playerRect);
                   if(enemy.hits(playerRect)
                      && !hurted){
                       hurtvalue = enemy.getDamage();
                       hurted();
                   }
                   if(enemy.hits(attackRadius) && attacking){
                       if(enemy.isAlive()){
                           enemy.hurt(damage, faceDirectionX, faceDirectionY, force);
                       }
                       else{
                           world.getEnemies().remove(i);
                       }
                   }
            }
            else{

                world.getEnemies().get(i).notAlerted();

            }


        }

        //kollision med vänner
        for(int i = 0; i < world.getFriends().size(); i++){

            Friend friend = world.getFriends().get(i);

            if(friend.detects(playerRect)){

                friend.alert();

                if(friend.intersects(playerRect)){

                    if(using){

                        if(friend.hasQuest() && !friend.getQuest().isActive()){

                            currentquest = friend.getQuest();
                            currentquest.start();
                            doingquest = true;

                        }

                        friend.talk();
                        talking = true;

                    }else{

                        friend.stopTalking();

                    }

                    using = false;

                }else{

                    friend.stopTalking();

                }



            }else{

                friend.notAlerted();

            }

        }

    }

    private boolean canMove(){

        Rectangle temp = new Rectangle(playerRect.x, playerRect.y, playerRect.width, playerRect.height);

        temp.x += xDirection*speed;
        temp.y += yDirection*speed;

        int index;
        for(int i = 0; i < collisionboxradius; i++){
            for(int j = 0; j < collisionboxradius; j++) {
                index = ((temp.x + pwiX) / 40) - 1 + i
                        + (((temp.y + pwiY) / 40) - 3 + j)* world.getMapWidth();
                if(world.ismapSolid[index] && world.mapcollision[index].intersects(temp)){

                    return false;

                }
            }
        }

        return true;
    }

    public void setSpeed(int s){

        speed = s;

    }

    public void attack(){

        if(!attacking){

            attacking = true;

            start = System.currentTimeMillis();

            if(faceDirectionX == 1){

                playerImg = PLAYER_PUNCHR;

            }
            if(faceDirectionX == -1){

                playerImg = PLAYER_PUNCHL;


            }
            if(faceDirectionY == -1){

                playerImg = PLAYER_PUNCHB;

            }
            if(faceDirectionY == 1){

                playerImg = PLAYER_PUNCHF;

            }

        }

    }


    private void setAnimation(){

        if(faceDirectionX == 1){

            playerImg = PLAYER_RIGHT;

        }
        if(faceDirectionX == -1){

            playerImg = PLAYER_LEFT;

        }
        if(faceDirectionY == 1){

           playerImg = PLAYER_FRONT;

        }
        if(faceDirectionY == -1){

            playerImg = PLAYER_BACK;

        }



    }

    private void hurted(){

        if(!hurted){

            health -= hurtvalue;

            hurted = true;

            start = System.currentTimeMillis();

        }

    }


    public void setxDirection(int dir){

        xDirection = dir;
        faceDirectionX = dir;
        faceDirectionY = 0;
        moving = true;



    }

    public void setyDirection(int dir){

        yDirection = dir;
        faceDirectionY = dir;
        faceDirectionX = 0;
        moving = true;

    }

    public void use(){


        using = true;

    }

    public void stopUse(){

        using = false;

    }

    public boolean hasQuest(){

        return doingquest;

    }

    public boolean isDead(){

        return health == 0;

    }

    public int getCash(){

        return cash;

    }

    public int getExp(){

        return exp;

    }


    public int getHealth(){

        return health;

    }

    public ArrayList<Quest> getCompletedquests(){

        return completedquests;

    }

    public Quest getCurrentQuest(){

        return currentquest;

    }

    public Inventory getInventory(){

        return inventory;

    }
}
