package se.liu.ida.denlj069.tddc69.project;

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

    GamePanel gp;

    public Game(){

        gp = new GamePanel();
        setTitle("RPG 1.0");
        setSize(gp.getPreferredSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(gp);

        createMenus();

        setVisible(true);

    }

    private void createMenus(){

        final JMenu game = new JMenu("Game");
        JMenuItem newgamebutton = new JMenuItem("New Game");
        newgamebutton.addActionListener(new newGameListener());
        game.add(newgamebutton);
        game.addSeparator();
        JMenuItem exitgamebutton = new JMenuItem("Exit");
        exitgamebutton.addActionListener(new exitGameListener());
        game.add(exitgamebutton);

        final JMenuBar bar = new JMenuBar();
        bar.add(game);

        setJMenuBar(bar);


    }

    public class newGameListener implements ActionListener{

        public void actionPerformed(ActionEvent event){

            setVisible(false);

            dispose();

            Game game = new Game();

        }


    }

    public class exitGameListener implements ActionListener{

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

        Game game = new Game();
    }

}
