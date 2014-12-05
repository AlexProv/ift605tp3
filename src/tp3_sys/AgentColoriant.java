package tp3_sys;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.StaleProxyException;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

public class AgentColoriant extends Agent{

	protected void setup()
	{
		Object[] args = getArguments();
		TreeMap< Integer, TreeSet<Integer> > noeuds = (TreeMap< Integer, TreeSet<Integer> >)args[0];
		TreeMap< Integer, TreeSet<Integer>> couleurNonVoulue = (TreeMap< Integer, TreeSet<Integer>>)args[1];
		String arbiter = (String)args[2];
		
		addBehaviour(new ColorBehaviour(this,noeuds,couleurNonVoulue,arbiter));
	}
	
	protected void TakeDown()
	{
		//pour tuer l'agent quand on call delete.
	}
	
	public AgentColoriant() {}
	

}
