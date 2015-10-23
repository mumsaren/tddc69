package se.liu.ida.denlj069.tddc69.project.Quest;

import se.liu.ida.denlj069.tddc69.project.Player.*;
import se.liu.ida.denlj069.tddc69.project.World.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestSystem implements PlayerListener
{

    private World world;
    private Player player;
    private boolean doingQuest;
    private Quest currentQuest;
    private Map<Friend, Quest> questGivers;
    private List<Quest> completedQuests;

    public QuestSystem(World world, Player player){

	this.world = world;
	this.player = player;
	doingQuest = false;
	questGivers = new HashMap<Friend, Quest>();
	completedQuests = new ArrayList<Quest>();
	player.addPlayerListener(this);
	loadQuests();

    }

    private void loadQuests(){

	//quest1
	Quest q001 = new Quest(50, 20, "Avenge Steven");
	q001.addGoal(new Talk(world.getFriends().get(2)));
	q001.addGoal(new Kill(world.getEnemies().get(0)));
	q001.addGoal(new Talk(world.getFriends().get(2)));
	questGivers.put(world.getFriends().get(2), q001);

	//quest2
	Quest q002 = new Quest(100,40, "Collect 2x spider eggs");
	q002.addGoal(new Talk(world.getFriends().get(0)));
	q002.addGoal(new Collect(2, "spideregg", player.getInventory()));
	q002.addGoal(new Talk(world.getFriends().get(0)));
	questGivers.put(world.getFriends().get(0), q002);

    }

    private void update(){

	if(!doingQuest){

	    for(final Map.Entry<Friend, Quest> friendQuestEntry : questGivers.entrySet()){

		if(friendQuestEntry.getKey().isTalking()){

		    currentQuest = friendQuestEntry.getValue();
		    currentQuest.start();
		    doingQuest = true;
		    currentQuest.update();

		}

	    }

	}else{

	    currentQuest.update();
	    if(currentQuest.isCompleted()){

		player.giveCash(currentQuest.getCashReward());
		player.giveExp(currentQuest.getExpReward());
		doingQuest = false;
		completedQuests.add(currentQuest);

	    }
	}

    }

    public boolean doingQuest(){

	return doingQuest;

    }

    public Quest getCurrentQuest(){

	return currentQuest;

    }

    public List<Quest> getCompletedQuests(){

	return completedQuests;

    }

    @Override
    public void playerChanged(){

	update();

    }

}
