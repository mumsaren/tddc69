package se.liu.ida.denlj069.tddc69.project.game;

import se.liu.ida.denlj069.tddc69.project.player.*;
import se.liu.ida.denlj069.tddc69.project.quest.*;
import se.liu.ida.denlj069.tddc69.project.world.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static se.liu.ida.denlj069.tddc69.project.player.HUD.*;

/**
 * The main game object.
 * #Creates world, player, QuestSystem and HUD objects
 * #Starts the main game thread - Updates and draws everything
 * #Handles keyboard input from user
 *
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-07
 * Time: 12:36
 *
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

    /**
     * Sets up necessary window parameters, creates the main game objects and starts
     * the main game thread
     */
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

    /**
     * The main game thread. Updates and repaints the game window while game is running
     * and not paused.
     */
    public void run(){

	//While loop spins on field cause needed
        while(running){
            if(!pause){
                update();
                repaint();
            }
            try {
		//This is for setting game speed, busy-waiting is inevitable
                Thread.sleep(GAME_SPEED);
            } catch (InterruptedException e) {

               e.printStackTrace();

            }
        }

    }

    /**
     * The update method called from the main thread, updates the player first
     * (unless the player is dead) and then the world.
     */
    private void update(){

        p1.update();
        if(p1.isDead()){

            running = false;
            gameover = true;

        }
        world.update();

    }

    /**
     * The main draw method. Sends graphic object to world, p1 and hud.
     */
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

    /**
     * Starts the main thread.
     */
    private void startGame(){
        if(!running){

            Thread game = new Thread(this);
            game.start();
            running = true;

        }
    }

    /**
     * Event handler for checking actions performed by user (pressed keyboard buttons).
     * It calls public methods in player, world and hud when certain buttons is pressed.
     */
    public class ActionListener extends KeyAdapter {

	//Don't know how to fix
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT){

                p1.setxDirection(-1);
                world.setxDirection(1);

            }
            if(key == KeyEvent.VK_RIGHT){


                p1.setxDirection(1);
                world.setxDirection(-1);


            }
            if(key == KeyEvent.VK_UP){

                p1.setyDirection(-1);
                world.setyDirection(1);

            }
            if(key == KeyEvent.VK_DOWN){

                p1.setyDirection(1);
                world.setyDirection(-1);


            }
            if(key == KeyEvent.VK_I){

                hud.display(ShowStuff.INVENTORY);
                pause();

            }
            if(key == KeyEvent.VK_SHIFT){

                p1.setSpeed(10);

            }
            if(key == KeyEvent.VK_C){

                p1.attack();


            }
            if(key == KeyEvent.VK_X){

                p1.use();

            }
            if(key == KeyEvent.VK_Q){

                hud.display(ShowStuff.QUESTS);
                pause();

            }


        }
	//Don't know how to fix
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP ||key == KeyEvent.VK_DOWN){

                p1.stopMove();

            }
            if(key == KeyEvent.VK_SHIFT){

                p1.setSpeed(4);

            }
            if(key == KeyEvent.VK_X){

                p1.stopUse();

            }
        }


    }

    public Dimension getGameDim(){

        return GAME_DIM;

    }

    private void pause(){

        repaint();

	pause = !pause;

    }


}
