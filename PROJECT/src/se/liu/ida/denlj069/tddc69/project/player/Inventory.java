package se.liu.ida.denlj069.tddc69.project.player;

import se.liu.ida.denlj069.tddc69.project.world.*;

import javax.swing.*;
import java.awt.*;

/**
 * The inventory for the player.
 *
 * Used for maintaining items that can
 * be picked up by the player in the world.
 *
 * Consists of an 2D item array and has methods for
 * adding, drawing and checking the inventory.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-20
 * Time: 09:19
 * To change this template use File | Settings | File Templates.
 */
public class Inventory {

    /**
     * the inventory Item 2D array and its dimensions
     */
    private Item[][] array;
    private int rows, columns;
    /**
     * Constants for placing the items in the inventory
     * (used for drawing the items correctly)
     */
    private static final int X_SPACING = 20;
    private static final int Y_SPACING = 140;
    private static final int SLOT_SIZE = 40;
    private static final int IMG_X = 0;
    private static final int IMG_Y = 80;

    /**
     * the background image of the inventory
     */
    private Image inventoryImg;

    /**
     * @param rows number of rows in the inventory
     * @param columns number of columns in the inventory
     */
    public Inventory(int rows, int columns){

        this.rows = rows;
        this.columns = columns;
        array = new Item[rows][columns];
        loadImages();

    }

    private void loadImages(){

        inventoryImg = new ImageIcon("./src/se/liu/ida/denlj069/tddc69/project/img/hud/" + "Inventory.png").getImage();


    }

    /**
     * Adds an item to a free space in the inventory
     *
     * @param item the item that is going to be placed in the inventory
     */
    public void addItem(Item item){

          for(int y = 0; y < rows; y++){

              for(int x = 0; x < columns; x++){

                  if(isEmpty(x,y)){
                      item.setX(x*SLOT_SIZE + X_SPACING);
                      item.setY(y*SLOT_SIZE + Y_SPACING);
                      array[y][x] = item;
                      return;

                  }

              }

          }

    }

    /**
     * Draws the inventory background image and all items
     * in the inventory.
     */
    public void draw(Graphics2D g){

        g.drawImage(inventoryImg, IMG_X, IMG_Y, null);

        for(int y = 0; y < rows; y++){

            for(int x = 0; x < columns; x++){

                if(!isEmpty(x,y)){
                    array[y][x].draw(g);
                }


            }


        }


    }

    private boolean isEmpty(int x, int y){

        return array[y][x] == null;

    }

    /**
     * Checks whether a given amount of a specific item is
     * present in the inventory.
     *
     * @param amount the amount of a specific item
     * @param name the name of the specific item
     * @return true if there is amount of the item, otherwise false.
     */
    public boolean contains(int amount, String name){

	for(int y = 0; y < rows; y++){

	    for(int x = 0; x < columns; x++){

		if(!isEmpty(x,y) && name.equals(array[y][x].getName())){

		    amount--;

		}


	    }


	}

	return amount <= 0;

    }
}
