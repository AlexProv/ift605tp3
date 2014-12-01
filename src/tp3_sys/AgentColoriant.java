package tp3_sys;

import java.io.IOException;

import jade.core.Agent;
import jade.core.AID;

public class AgentColoriant extends Agent{

	protected void setup()
	{
		Object[] args = getArguments();
		addBehaviour(new Colorbehaviour());
		System.out.println("FUCK");
        try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void TakeDown() 
	{
		//pour tuer l'agent quand on call delete.
	}
	
	public AgentColoriant() {
	}

}
