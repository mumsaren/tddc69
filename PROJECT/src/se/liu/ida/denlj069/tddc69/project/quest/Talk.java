package se.liu.ida.denlj069.tddc69.project.quest;

import se.liu.ida.denlj069.tddc69.project.world.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-08
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class Talk implements Goal
{


    private Friend friend;

    public Talk(Friend friend){

       this.friend = friend;

    }

    public boolean isCompleted(){

        return friend.isTalking();

    }

    public String getType(){

        return "Talk";

    }


}
