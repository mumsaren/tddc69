package se.liu.ida.denlj069.tddc69.project.player;

import se.liu.ida.denlj069.tddc69.project.world.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-20
 * Time: 09:19
 * To change this template use File | Settings | File Templates.
 */
public class Inventory {

    private Item[][] array;
    private int rows, columns;
    private static final int X_SPACING = 20;
    private static final int Y_SPACING = 140;
    private static final int SLOT_SIZE = 40;
    private static final int IMG_X = 0;
    private static final int IMG_Y = 80;
    private Image inventoryImg;


    public Inventory(int rows, int columns){

        this.rows = rows;
        this.columns = columns;
        array = new Item[rows][columns];
        loadImages();

    }

    private void loadImages(){

        inventoryImg = new ImageIcon("./src/se/liu/ida/denlj069/tddc69/project/img/hud/" + "Inventory.png").getImage();


    }

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
