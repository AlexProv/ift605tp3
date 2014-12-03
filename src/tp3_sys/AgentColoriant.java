package tp3_sys;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

public class AgentColoriant extends Agent{

	protected void setup()
	{
		Object[] args = getArguments();
		TreeMap< Integer, TreeSet<Integer> > noeuds = (TreeMap< Integer, TreeSet<Integer> >)args[0];
		
		addBehaviour(new ColorBehaviour(this,noeuds));
		
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
	
	public AgentColoriant() {
	}

}
