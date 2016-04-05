package se.liu.ida.denlj069.tddc69.project.quest;

import se.liu.ida.denlj069.tddc69.project.world.*;

/**
 * One of the possible goals of a Quest.
 * This goal is fulfilled if a specific friend is talked to by the player.
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-08
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class Talk implements Goal
{
    /**
     * variable for the specific friend
     */
    private Friend friend;

    /**
     * @param friend the specific friend which the goal checks
     */
    public Talk(Friend friend){

       this.friend = friend;

    }

    /**
     * Checks if player is talking to the specific friend
     *
     * @return true if player is talking to the specific friend, otherwise false
     */
    public boolean isCompleted(){

        return friend.isTalking();

    }

    /**
     *
     * @return text for checking the type of goal
     */
    public String getType(){

        return "Talk";

    }


}
