package se.liu.ida.denlj069.tddc69.project;

import java.awt.*;
import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-19
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class Item {

    private Rectangle itemRect;
    private Image itemImg;
    private static final int ITEM_RECT_WIDTH = 20;
    private static final int ITEM_RECT_HEIGHT = 20;

    public Item(int x, int y, String imgName){

        itemRect = new Rectangle(x, y, ITEM_RECT_WIDTH, ITEM_RECT_HEIGHT);
        loadImage(imgName);

    }

    public void loadImage(String imgName){

        itemImg = new ImageIcon("/home/mumsaren/Dokument/TDDC69/PROJECT/src/se/liu/ida/denlj069/tddc69/project/img/Items/" + imgName + ".png").getImage();

    }

    public void draw(Graphics2D g){

        g.drawImage(itemImg, itemRect.x, itemRect.y, itemImg.getWidth(null),
                itemImg.getHeight(null), null);

    }

    public void moveX(int distance, int dir){

        itemRect.x += dir*distance;

    }

    public void moveY(int distance, int dir){

        itemRect.y += dir*distance;

    }

    public void setX(int x){

        itemRect.x = x;

    }

    public void setY(int y){

        itemRect.y = y;

    }

    public boolean intersects(Rectangle rec){

        return itemRect.intersects(rec);

    }

    public abstract String getType();

    public String getName(){

        return null;

    }


}
