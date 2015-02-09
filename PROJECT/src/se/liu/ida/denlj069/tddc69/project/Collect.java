package se.liu.ida.denlj069.tddc69.project;

/**
 * Created by mumsaren on 2015-02-01.
 */
public class Collect extends Goal {

    private int amount;
    private String name;

    public Collect(int amount, String name){

        this.amount = amount;
        this.name = name;

    }

    public boolean isCompleted(){

        return amount <= 0;

    }

    public String getType(){

        return "Collect";

    }

    @Override
    public String getName(){

        return name;

    }

    @Override
    public void decreaseAmount(){

        amount--;

    }
}