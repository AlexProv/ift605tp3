package tp3_sys;

import jade.core.behaviours.Behaviour;

public class ColorBehaviour extends Behaviour{

	@Override
	public void action() {
		// TODO Auto-generated method stub
		//equivalent de ton RUN dans un thread Ben
		while(true)
		{
			System.out.println("ben travail!");
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		//self explanatory 
		return false;
	}

}
