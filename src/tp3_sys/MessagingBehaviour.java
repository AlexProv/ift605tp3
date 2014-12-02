package tp3_sys;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

public class MessagingBehaviour extends CyclicBehaviour {
	
	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	Agent agent;
	String agentLocalName;
	
	public MessagingBehaviour(Agent a) {
		super(a);
		agent = a;
	}
	
	
	@Override
	public void action() {
		ACLMessage  msg = myAgent.receive();
		if(msg != null){

			agent.send(formateReplyWithLog(msg));
		}
		else {
			block();
		}
	}
	
	protected ACLMessage formateReplyWithLog(ACLMessage msg)
	{
		ACLMessage reply = msg.createReply();

		if(msg.getPerformative()== ACLMessage.REQUEST){
			String content = msg.getContent();
			if ((content != null) && (content.indexOf("alive") != -1)){
				System.out.println("Agent " + agent.getLocalName() + " - Received PING Request from "
						+ msg.getSender().getLocalName());
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("alive");
			}
			else{
				System.out.println("Agent " + agent.getLocalName() + " - Unexpected request [" + content + "] received from "
						+ msg.getSender().getLocalName());
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("( UnexpectedContent (" + content + "))");
			}

		}
		else {
			System.out.println("Agent " + agent.getLocalName() + " - Unexpected message [" + 
					ACLMessage.getPerformative(msg.getPerformative()) + "] received from " + msg.getSender().getLocalName());
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			reply.setContent("( (Unexpected-act " + ACLMessage.getPerformative(msg.getPerformative()) + ") )");   
		}
		return reply;
	}
	
	protected ACLMessage formateReply(ACLMessage msg)
	{
		ACLMessage reply = msg.createReply();

		if(msg.getPerformative()== ACLMessage.REQUEST){
			String content = msg.getContent();
			if ((content != null) && (content.indexOf("alive") != -1)){
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("alive");
			}
			else{
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("( UnexpectedContent (" + content + "))");
			}

		}
		else {
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			reply.setContent("( (Unexpected-act " + ACLMessage.getPerformative(msg.getPerformative()) + ") )");   
		}
		return reply;
	}
}
