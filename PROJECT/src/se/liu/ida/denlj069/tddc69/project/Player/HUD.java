package se.liu.ida.denlj069.tddc69.project.Player;

import se.liu.ida.denlj069.tddc69.project.Quest.*;
import se.liu.ida.denlj069.tddc69.project.World.*;

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

    private static final int EXP_BAR_X = 145;
    private static final int EXP_BAR_Y = 25;
    private static final int EXP_BAR_SLICE_WIDTH = 5;
    private static final int EXP_BAR_HEIGHT = 20;
    private static final int EXP_BAR_SLICES = 20;
    private static final int EXP_FONT_SIZE = 14;
    private static final int EXP_TEXT_X = 149;
    private static final int EXP_TEXT_Y = 18;
    private static final int EXP_BAR_BORDER_X = 149;
    private static final int EXP_BAR_BORDER_Y = 24;
    private static final int EXP_BAR_BORDER_WIDTH = 101;
    private static final int EXP_BAR_BORDER_HEIGHT = 21;
    private static final int HEALTH_BAR_X = 15;
    private static final int HEALTH_BAR_Y = 25;
    private static final int HEALTH_BAR_SLICE_WIDTH = 5;
    private static final int HEALTH_BAR_HEIGHT = 20;
    private static final int HEALTH_BAR_SLICES = 20;
    private static final int HEALTH_BAR_BORDER_X = 19;
    private static final int HEALTH_BAR_BORDER_Y = 24;
    private static final int HEALTH_BAR_BORDER_WIDTH = 101;
    private static final int HEALTH_BAR_BORDER_HEIGHT = 21;
    private static final int HEALTH_FONT_SIZE = 14;
    private static final int HEALTH_TEXT_X = 19;
    private static final int HEALTH_TEXT_Y = 18;
    private static final int BACKGROUND_HEIGHT = 80;
    private static final int CASH_IMG_X = 70;
    private static final int CASH_IMG_Y = 10;
    private static final int CASH_FONT_SIZE = 15;
    private static final int CASH_TEXT_X = 80;
    private static final int CASH_TEXT_Y = 50;
    private static final int QUEST_IMG_X = 0;
    private static final int QUEST_IMG_Y = 80;
    private static final int QUEST_FONT_SIZE = 15;
    private static final int QUEST_TEXT_X = 20;
    private static final int QUEST_TEXT_Y = 180;
    private static final int QUEST_C_TEXT_X = 680;
    private static final int QUEST_C_TEXT_SPACE = 20;
    private static final int QUEST_C_TEXT_Y = 180;

    private Player player;
    private QuestSystem quests;
    private World world;
    private Image cashImg, questImg;

    private List<Rectangle> healthBar;
    private List<Rectangle> expBar;

    private boolean showInventory = false;
    private boolean showQuests = false;

    public enum ShowStuff{

        INVENTORY, QUESTS, NULL

    }

    private ShowStuff stuff;

    public HUD(Player player, QuestSystem quests, World world){

        this.player = player;
	this.quests = quests;
	this.world = world;

        loadImages();

        createHealthbar();
        createExpbar();

        stuff = ShowStuff.NULL;

    }

    private void loadImages(){

        cashImg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/HUD/cash.png").getImage();
        questImg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/HUD/Quest.png").getImage();

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

	int xCoord = 0;
        expBar = new ArrayList<Rectangle>();
        for(int i = 0; i < EXP_BAR_SLICES; i++){

            xCoord += EXP_BAR_SLICE_WIDTH;

            Rectangle exp = new Rectangle(xCoord + EXP_BAR_X, EXP_BAR_Y,
					  EXP_BAR_SLICE_WIDTH, EXP_BAR_HEIGHT);
            expBar.add(exp);

        }

    }

    private void createHealthbar(){

        int xCoord = 0;
        healthBar = new ArrayList<Rectangle>();
        for(int i = 0; i < HEALTH_BAR_SLICES; i++){

            xCoord += HEALTH_BAR_SLICE_WIDTH;

            Rectangle health = new Rectangle(xCoord + HEALTH_BAR_X, HEALTH_BAR_Y,
					     HEALTH_BAR_SLICE_WIDTH, HEALTH_BAR_HEIGHT);
            healthBar.add(health);

        }



    }

    private void drawBorder(Graphics2D g){

        g.setColor(Color.black);
        g.fillRect(0,0,world.getMapSectionWidth(),BACKGROUND_HEIGHT);

    }

    private void drawCash(Graphics2D g){

        g.drawImage(cashImg, world.getMapSectionWidth() - CASH_IMG_X, CASH_IMG_Y, null);
        g.setColor(Color.yellow);
        g.setFont(new Font("Serif", Font.ITALIC, CASH_FONT_SIZE));
        g.drawString(String.valueOf(player.getCash()), world.getMapSectionWidth() - CASH_TEXT_X, CASH_TEXT_Y);

    }

    private void drawHealthbar(Graphics2D g){

        g.setFont(new Font("Serif", Font.ITALIC, HEALTH_FONT_SIZE));

        g.setColor(Color.white);
        g.drawString("HEALTH", HEALTH_TEXT_X, HEALTH_TEXT_Y);

        g.drawRect(HEALTH_BAR_BORDER_X, HEALTH_BAR_BORDER_Y, HEALTH_BAR_BORDER_WIDTH, HEALTH_BAR_BORDER_HEIGHT);
        g.setColor(Color.red);
        for(int i = 0; i < player.getHealth()/HEALTH_BAR_SLICE_WIDTH; i++){

            g.fillRect(healthBar.get(i).x, healthBar.get(i).y, healthBar.get(i).width, healthBar.get(i).height);


        }

    }

    private void drawQuests(Graphics2D g){

        g.drawImage(questImg, QUEST_IMG_X, QUEST_IMG_Y, null);

        g.setFont(new Font("Serif", Font.BOLD, QUEST_FONT_SIZE));
        g.setColor(Color.yellow);


        if(quests.doingQuest()){

            Quest quest = quests.getCurrentQuest();

            g.drawString(quest.getName(), QUEST_TEXT_X, QUEST_TEXT_Y);

        }


        for(int i = 0; i < quests.getCompletedQuests().size(); i++){

             g.drawString(quests.getCompletedQuests().get(i).getName(), QUEST_C_TEXT_X,
			  i*QUEST_C_TEXT_SPACE + QUEST_C_TEXT_Y);

        }

    }

    private void drawExpbar(Graphics2D g){

        g.setFont(new Font("Serif", Font.ITALIC, EXP_FONT_SIZE));

        g.setColor(Color.white);
        g.drawString("EXP", EXP_TEXT_X, EXP_TEXT_Y);
        g.drawRect(EXP_BAR_BORDER_X, EXP_BAR_BORDER_Y, EXP_BAR_BORDER_WIDTH, EXP_BAR_BORDER_HEIGHT);

        g.setColor(Color.cyan);
        for(int i = 0; i < player.getExp()/5; i++){

            g.fillRect(expBar.get(i).x, expBar.get(i).y, expBar.get(i).width, expBar.get(i).height);


        }


    }

    public void display(ShowStuff stuff){

        this.stuff = stuff;
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

