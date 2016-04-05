package se.liu.ida.denlj069.tddc69.project.world;

/**
 * Subclass of item.
 *
 * Can be picked up by the player.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-25
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class Coin extends Item
{

    public Coin(int x, int y){

        super(x, y, "coin");

    }

    public String getName(){

	return null;

    }

    public String getType(){

        return "coin";

    }

}
