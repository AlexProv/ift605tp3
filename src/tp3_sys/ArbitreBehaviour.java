package tp3_sys;

import jade.core.behaviours.Behaviour;

import java.util.Random;

public class ArbitreBehaviour extends Behaviour{

	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
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
