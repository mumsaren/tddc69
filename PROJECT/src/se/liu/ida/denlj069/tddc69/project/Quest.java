package se.liu.ida.denlj069.tddc69.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-07
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class Quest{

    private boolean completed;
    private int cashreward;
    private int expreward;
    private boolean active;
    private String name;
    private List<Goal> goals;

    public Quest(int cashreward, int expreward, String name){

        this.cashreward = cashreward;
        this.expreward = expreward;
        this.name = name;
        goals = new ArrayList<Goal>();
	completed = false;
	active = false;

    }


    public void start(){

        active = true;

    }

    public void update(){

        if(active){

            if(!completed){
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

        if(getCurrentGoal().isCompleted()){

            System.out.println(getCurrentGoal().getType());
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

    public Goal getCurrentGoal(){

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
