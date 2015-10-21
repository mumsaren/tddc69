package se.liu.ida.denlj069.tddc69.project;

/**
 * Created by mumsaren on 2015-02-01.
 */
public class Collect extends Goal {

    private int amount;
    private String name;
    private Inventory playerInventory;

    public Collect(int amount, String name, Inventory inventory){

        this.amount = amount;
        this.name = name;
	this.playerInventory = inventory;

    }

    public boolean isCompleted(){

	return playerInventory.contains(amount, name);

    }

    public String getType(){

        return "Collect";

    }


}