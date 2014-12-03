package tp3_sys;

import tp3_sys.CentralIntelligenceAgency;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
//import jade.domain.introspection.ACLMessage;
import jade.lang.acl.*;
import java.util.Random;

import sun.security.action.GetLongAction;

public class ArbitreBehaviour extends MessagingBehaviour{

	protected Agent agent;
	public ArbitreBehaviour(Agent a) {
		super(a);
		agent = a;
	}
	
	@Override
	public void action() {
		agent.getAID();
		agent.getLocalName();
		
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	     msg.setContent( "alive" );
	     for (int i = 1; i<=CentralIntelligenceAgency.NB_AGENT; i++) {
	        msg.addReceiver( new AID( "Agent" + i, AID.ISLOCALNAME) );
	     }
	     agent.send(msg);
	}
	
	public Object whoIsRight(Object a, Object b)
	{
		Random r = new Random();
		if(r.nextBoolean())
		{
			return a;
		}
		return b;
	}
	
	
}
