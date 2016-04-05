package se.liu.ida.denlj069.tddc69.project.quest;

import se.liu.ida.denlj069.tddc69.project.player.*;

/**
 * One of the possible goals of a quest.
 * This goal is fulfilled if the right amount of a specific
 * Collectibleitem's name is in the players inventory.
 *
 * Created by mumsaren on 2015-02-01.
 */
public class Collect implements Goal
{
    /**
     * variable for the amount of the specific item
     */
    private int amount;
    /**
     * the name of the specific Collectibleitem
     */
    private String name;
    /**
     * variable for the player inventory
     */
    private Inventory playerInventory;

    public Collect(int amount, String name, Inventory inventory){

        this.amount = amount;
        this.name = name;
	this.playerInventory = inventory;

    }

    /**
     * Checks if the amount of the specific Collectibleitem's name is
     * in the inventory.
     *
     * @return true if it is, otherwise false
     */
    public boolean isCompleted(){

	return playerInventory.contains(amount, name);

    }

    /**
     * @return text for checking the type of goal
     */
    public String getType(){

        return "Collect";

    }


}