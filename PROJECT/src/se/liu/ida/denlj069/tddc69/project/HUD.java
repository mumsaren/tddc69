package se.liu.ida.denlj069.tddc69.project;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-25
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class HUD{

    private Player player;
    private QuestSystem quests;
    private Image cashimg, questimg;

    private java.util.List<Rectangle> healthbar;
    private List<Rectangle> expbar;

    private boolean showInventory = false;
    private boolean showQuests = false;

    public enum ShowStuff{

        INVENTORY, QUESTS, NULL;

    }

    private ShowStuff stuff;

    public HUD(Player player, QuestSystem quests){

        this.player = player;
	this.quests = quests;

        loadImages();

        createHealthbar();
        createExpbar();

        stuff = ShowStuff.NULL;

    }

    private void loadImages(){

        cashimg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/HUD/cash.png").getImage();
        questimg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/HUD/Quest.png").getImage();

    }


    public void draw(Graphics2D g){

        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));

        drawBorder(g);

        drawHealthbar(g);

        drawCash(g);

        drawExpbar(g);

        if(showQuests){

           drawQuests(g);

        }

        if(showInventory){

            player.getInventory().draw(g);

        }

    }

    private void createExpbar(){

        int xCoord = 145;
        expbar = new ArrayList<Rectangle>();
        for(int i = 0; i < 20; i++){

            xCoord += 5;

            Rectangle exp = new Rectangle(xCoord, 25, 5, 20);
            expbar.add(exp);

        }

    }

    private void createHealthbar(){

        int xCoord = 15;
        healthbar = new ArrayList<Rectangle>();
        for(int i = 0; i < 20; i++){

            xCoord += 5;

            Rectangle health = new Rectangle(xCoord, 25, 5, 20);
            healthbar.add(health);

        }



    }

    private void drawBorder(Graphics2D g){

        g.setColor(Color.black);
        g.fillRect(0,0,800,80);

    }

    private void drawCash(Graphics2D g){

        g.drawImage(cashimg, 800 - 70, 10, null);
        g.setFont(new Font("Serif", Font.ITALIC, 16));
        g.setColor(Color.black);
        g.drawString("" + player.getCash(), 800 - 80, 50);
        g.setColor(Color.yellow);
        g.setFont(new Font("Serif", Font.ITALIC, 15));
        g.drawString("" + player.getCash(), 800 - 80, 50);

    }

    private void drawHealthbar(Graphics2D g){

        g.setFont(new Font("Serif", Font.ITALIC, 14));

        g.setColor(Color.white);
        g.drawString("HEALTH", 19, 18);

        g.drawRect(19, 24, 101, 21);
        g.setColor(Color.red);
        for(int i = 0; i < player.getHealth()/5; i++){

            g.fillRect(healthbar.get(i).x, healthbar.get(i).y, healthbar.get(i).width, healthbar.get(i).height);


        }

    }

    private void drawQuests(Graphics2D g){

        g.drawImage(questimg, 0, 80, null);

        g.setFont(new Font("Serif", Font.BOLD, 14));
        g.setColor(Color.yellow);


        if(quests.doingQuest()){

            Quest quest = quests.getCurrentQuest();

            g.drawString(quest.getName(), 20, 180);

        }


        for(int i = 0; i < quests.getCompletedQuests().size(); i++){

             g.drawString(quests.getCompletedQuests().get(i).getName(), 680, i*20 + 180);

        }

    }

    private void drawExpbar(Graphics2D g){

        g.setFont(new Font("Serif", Font.ITALIC, 14));

        g.setColor(Color.white);
        g.drawString("EXP", 149, 18);
        g.drawRect(149, 24, 101, 21);

        g.setColor(Color.cyan);
        for(int i = 0; i < player.getExp()/5; i++){

            g.fillRect(expbar.get(i).x, expbar.get(i).y, expbar.get(i).width, expbar.get(i).height);


        }


    }

    public void display(ShowStuff s){

        stuff = s;
        checkStuff();

    }

    private void checkStuff(){

        switch(stuff){

            case INVENTORY:
                if(showInventory){

                    showInventory = false;

                }else{

                    showInventory = true;

                }

                break;
            case QUESTS:
                if(showQuests){

                    showQuests = false;

                }else{

                    showQuests = true;

                }

                break;
            case NULL:
                break;
        }


    }

}

