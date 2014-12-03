package tp3_sys;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

import jade.core.Agent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentController;

public class AgentArbitre extends Agent{
	
	protected void setup()
	{
		Object[] args = getArguments();
		
		ArbitreBehaviour arbitreBehaviour = new ArbitreBehaviour(this);
		addBehaviour(arbitreBehaviour);
		

		
        try {
        	
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void TakeDown() 
	{
		//pour tuer l'agent quand on call delete.
	}
}
