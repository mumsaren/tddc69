package se.liu.ida.denlj069.tddc69.project.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-09
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */

public class Game extends JFrame {

    /**
     * Sets upp the game window and creates the GamePanel object
     * - the main game object which will be drawn in the game
     * window.
     */
    public Game(){

        GamePanel gp = new GamePanel();
        setTitle("RPG 1.0");
        setSize(gp.getPreferredSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        add(gp);

        createMenus();

        setVisible(true);

    }

    /**
     * Create menu and buttons for the game window
     */
    private void createMenus(){

        final JMenu game = new JMenu("Game");
        JMenuItem newgamebutton = new JMenuItem("New Game");
        newgamebutton.addActionListener(new GameListener());
        game.add(newgamebutton);
        game.addSeparator();
        JMenuItem exitgamebutton = new JMenuItem("Exit");
        exitgamebutton.addActionListener(new ExitGameListener());
        game.add(exitgamebutton);

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(game);

        setJMenuBar(menuBar);


    }

    /**
     * Creates a new game window when the 'new game' menu button is clicked
     */
    public class GameListener implements ActionListener{

        public void actionPerformed(ActionEvent event){

            setVisible(false);

            dispose();

	    //Don't know how to fix this complaint
            new Game();

        }


    }

    /**
     * Creates a confirm dialog window when the 'close window (x)' is clicked
     */
    public class ExitGameListener implements ActionListener{

        public void actionPerformed(ActionEvent event){

            int answer = JOptionPane.showConfirmDialog(
                    null, "Do you really want to quit?", "Exit game",
                    JOptionPane.YES_NO_OPTION);

            if(answer == JOptionPane.YES_OPTION){

                System.exit(0);

            }


        }


    }

    public static void main(String[] args) {

	//Don't know how to fix this complaint
        new Game();
    }

}
