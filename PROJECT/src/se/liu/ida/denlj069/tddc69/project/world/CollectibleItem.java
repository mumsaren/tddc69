package se.liu.ida.denlj069.tddc69.project.world;


/**
 * Subclass of Item.
 *
 * A CollectibleItem can be picked up and placed in the player
 * inventory. It is identified by a name. It can be used in a Quest
 * with the goal Collectible.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-19
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class CollectibleItem extends Item
{
    /**
     * name used for identifying the item
     */
    private String name;

    /**
     *
     * @param x position x in world
     * @param y position y in world
     * @param name the name of the item
     */
    public CollectibleItem(int x, int y, String name){

        super(x, y, name);
        this.name = name;

    }

    /**
     * Used for identifying the CollectibleItem
     *
     * @return the name of item
     */
    public String getName(){

        return name;

    }

    /**
     * Used for checking if this Item is a CollectibleItem
     *
     * @return text
     */
    public String getType(){

        return "collectible";

    }


}
