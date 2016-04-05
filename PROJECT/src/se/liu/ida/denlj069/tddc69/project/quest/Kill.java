package se.liu.ida.denlj069.tddc69.project.quest;


import se.liu.ida.denlj069.tddc69.project.world.*;

/**
 * One of the possible goals of a Quest.
 * This goal is fulfilled if a specific enemy is killed.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-08
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class Kill implements Goal
{
    /**
     * variable for the specific enemy
     */
    private Enemy enemy;
    /**
     * @param enemy the specific enemy which the goal checks
     */
    public Kill(Enemy enemy){

       this.enemy = enemy;

    }
    /**
     * Checks if the enemy is dead
     *
     * @return true if the enemy is dead, otherwise false
     */
    public boolean isCompleted(){

        return !enemy.isAlive();

    }
    /**
     * @return text for checking the type of goal
     */
    public String getType(){

        return "Kill";

    }

}
