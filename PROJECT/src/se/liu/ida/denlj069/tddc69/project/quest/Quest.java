package se.liu.ida.denlj069.tddc69.project.quest;

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
    private int cashReward;
    private int expReward;
    private boolean active;
    private String name;
    private List<Goal> goals;

    public Quest(int cashReward, int expReward, String name){

        this.cashReward = cashReward;
        this.expReward = expReward;
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

    public int getCashReward(){

        return cashReward;

    }

    public int getExpReward(){

        return expReward;

    }

    public String getName(){

        return name;

    }

}
