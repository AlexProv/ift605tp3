package tp3_sys;

import java.io.IOException;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class AgentColoriant extends Agent{

	protected void setup()
	{
		Object[] args = getArguments();
		
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("AgentColoriant"); 
		sd.setName(getName());
		sd.setOwnership("ift605");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this,dfd);
		addBehaviour(new ColorBehaviour());
		System.out.println("ding");
		}
		catch (FIPAException fe)
		{
			doDelete();

		}
		
		
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
