package se.liu.ida.denlj069.tddc69.project;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-07
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class Quest{

    private boolean completed = false;
    private int cashreward;
    private int expreward;
    private boolean active = false;
    private String name;

    private ArrayList<Goal> goals;


    public Quest(int cashreward, int expreward, String name){

        this.cashreward = cashreward;
        this.expreward = expreward;
        this.name = name;
        goals = new ArrayList<Goal>();

    }


    public void start(){

        active = true;

    }

    public void update(){

        if(active){

            if(!isCompleted()){
                checkGoals();
                if(goals.isEmpty()){

                    completed = true;
                    active = false;

                    System.out.println("quest done");

                }
            }


        }


    }

    private void checkGoals(){

        if(getCurrentCoal().isCompleted()){

            System.out.println(getCurrentCoal().getType());
            System.out.println("goal completed");

            goals.remove(0);

        }

    }

    public boolean isActive(){

        return active;

    }

    public void addGoal(Goal goal){

        goals.add(goal);

    }

    public Goal getCurrentCoal(){

        if(!goals.isEmpty()){
            return goals.get(0);
        }
        return null;

    }

    public boolean isCompleted(){

        return completed;

    }

    public int getCashreward(){

        return cashreward;

    }

    public int getExpreward(){

        return expreward;

    }

    public String getName(){

        return name;

    }

}
