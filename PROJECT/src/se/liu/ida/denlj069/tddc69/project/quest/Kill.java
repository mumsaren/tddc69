package se.liu.ida.denlj069.tddc69.project.quest;


import se.liu.ida.denlj069.tddc69.project.world.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-08
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class Kill implements Goal
{

    private Enemy enemy;

    public Kill(Enemy enemy){

       this.enemy = enemy;

    }

    public boolean isCompleted(){

        return !enemy.isAlive();

    }

    public String getType(){

        return "Kill";

    }

}
