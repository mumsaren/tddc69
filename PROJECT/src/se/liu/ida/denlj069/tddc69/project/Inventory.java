package se.liu.ida.denlj069.tddc69.project;

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
    private int slotSize, xpos, ypos;
    private Image inventoryimg;


    public Inventory(int rows, int columns){

        this.rows = rows;
        this.columns = columns;
        array = new Item[rows][columns];
        slotSize = 40;
        xpos = 20;
        ypos = 140;
        loadImages();

    }

    private void loadImages(){

        inventoryimg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/HUD/Inventory.png").getImage();


    }

    public void addItem(Item item){

          for(int y = 0; y < rows; y++){

              for(int x = 0; x < columns; x++){

                  if(isEmpty(x,y)){

                      //System.out.println(x + "  " + y);
                      item.setX(x*slotSize + xpos);
                      item.setY(y*slotSize + ypos);
                      array[y][x] = item;
                      return;

                  }

              }

          }

    }

    public void draw(Graphics2D g){

        g.drawImage(inventoryimg, 0, 80, null);

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





}
