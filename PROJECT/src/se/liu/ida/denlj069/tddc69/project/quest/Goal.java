package se.liu.ida.denlj069.tddc69.project.quest;

/**
 * The interface Goal which Quest consists of.
 * Has three classes that implements this interface:
 * Talk, Kill and Collect
 *
 * Created with IntelliJ IDEA.
 * User: Mumsaren
 * Date: 2013-10-08
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public interface Goal {

    /**
     * @return true if goal is completed, otherwise false
     */
    boolean isCompleted();

    /**
     * @return text for checking the type of goal
     */
    String getType();

}
