package se.liu.ida.denlj069.tddc69.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-07
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */
public class GamePanel extends JPanel implements Runnable{

    private static final int GWIDTH = 800, GHEIGHT = 480;
    private static final Dimension GAME_DIM = new Dimension(GWIDTH, GHEIGHT);
    private static final long GAME_SPEED = 20;

    private static final int GAME_OVER_FONT_SIZE = 50;
    private static final int GAME_OVER_TEXT_X = 250;
    private static final int GAME_OVER_TEXT_Y = 200;

    private boolean running = false;
    private boolean pause = false;
    private boolean gameover = false;

    private Player p1;
    private World world;
    private HUD hud;


    public GamePanel(){

        addKeyListener(new ActionListener());
        setPreferredSize(GAME_DIM);
        setFocusable(true);
        requestFocus();
        setDoubleBuffered(true);

        world = new World("map");
        p1 = new Player(world);
	QuestSystem qs = new QuestSystem(world,p1);
        hud = new HUD(p1,qs,world);

        startGame();


    }

    public void run(){

        while(running){
            if(!pause){
                update();
                repaint();
            }
            try {
                Thread.sleep(GAME_SPEED);
            } catch (InterruptedException e) {

               e.printStackTrace();

            }
        }

    }

    private void update(){

        p1.update();
        if(p1.isDead()){

            running = false;
            gameover = true;

        }
        world.update();

    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;


        world.draw(g2d);
        p1.draw(g2d);
        hud.draw(g2d);

        if(gameover){

            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, GAME_OVER_FONT_SIZE));
            g2d.drawString("YOU'RE DEAD", GAME_OVER_TEXT_X, GAME_OVER_TEXT_Y);

        }


    }


    private void startGame(){
        if(!running){

            Thread game = new Thread(this);
            game.start();
            running = true;

        }
    }

    public class ActionListener extends KeyAdapter {

        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == e.VK_LEFT){

                p1.setxDirection(-1);
                world.setXDirection(1);


            }
            if(key == e.VK_RIGHT){


                p1.setxDirection(1);
                world.setXDirection(-1);


            }
            if(key == e.VK_UP){

                p1.setyDirection(-1);
                world.setYDirection(1);

            }
            if(key == e.VK_DOWN){

                p1.setyDirection(1);
                world.setYDirection(-1);


            }
            if(key == e.VK_I){

                hud.display(HUD.ShowStuff.INVENTORY);
                pause();

            }
            if(key == e.VK_SHIFT){

                p1.setSpeed(10);

            }
            if(key == e.VK_C){

                p1.attack();


            }
            if(key == e.VK_X){

                p1.use();

            }
            if(key == e.VK_Q){

                hud.display(HUD.ShowStuff.QUESTS);
                pause();

            }


        }
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            if(key == e.VK_LEFT || key == e.VK_RIGHT || key == e.VK_UP ||key == e.VK_DOWN){

                p1.stopMove();

            }
            if(key == e.VK_SHIFT){

                p1.setSpeed(4);

            }
            if(key == e.VK_X){

                p1.stopUse();

            }
        }


    }

    public Dimension getPreferredSize(){

        return GAME_DIM;

    }

    private void pause(){

        repaint();

        if(pause){

            pause = false;

        }
        else{

            pause = true;

        }

    }


}
