package se.liu.ida.denlj069.tddc69.project;


/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-09-19
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class CollectibleItem extends Item {

    private String name;

    public CollectibleItem(int x, int y, String name){

        super(x, y, name);
        this.name = name;

    }


    @Override
    public String getName(){

        return name;

    }

    public String getType(){

        return "collectible";

    }


}
