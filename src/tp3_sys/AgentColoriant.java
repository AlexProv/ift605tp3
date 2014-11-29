package tp3_sys;

import jade.core.Agent;
import jade.core.AID;

public class AgentColoriant extends Agent{

	protected void setup()
	{
		Object[] args = getArguments();
		addBehaviour(new Colorbehaviour());
	}
	
	protected void TakeDown() 
	{
		//pour tuer l'agent quand on call delete.
	}
	
	public AgentColoriant() {
	}

}
