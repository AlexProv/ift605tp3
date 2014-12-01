package tp3_sys;

import jade.core.Agent;
import jade.core.AID;

public class lolAgent extends Agent{
	
	protected void setup()
	{
		System.out.println("hello world!");
		System.out.println("my name is" + getLocalName());
	}
}
