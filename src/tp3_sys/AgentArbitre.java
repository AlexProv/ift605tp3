package tp3_sys;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AgentArbitre extends Agent{
	
	protected void setup()
	{
		Object[] args = getArguments();
		ContainerController cc =(ContainerController)args[0];
		TreeMap<Integer, TreeSet<Integer>> noeuds = (TreeMap<Integer, TreeSet<Integer>>)args[1];
		
		Object[] o1 = new Object[3];
		Object[] o2 = new Object[3];
		Object[] o3 = new Object[3];
		Set<Integer> noeudsKeys = noeuds.keySet();
		int taille = noeudsKeys.size();
		
		TreeMap<Integer, TreeSet<Integer>> aN = new TreeMap<Integer, TreeSet<Integer>>();
		TreeMap<Integer, TreeSet<Integer>> bN = new TreeMap<Integer, TreeSet<Integer>>();
		TreeMap<Integer, TreeSet<Integer>> cN = new TreeMap<Integer, TreeSet<Integer>>();
		
		Iterator it = noeuds.entrySet().iterator();

		for(int cpt = 0; it.hasNext(); ++cpt)
		{
	        Map.Entry pairs = (Map.Entry)it.next();
			if(cpt < 8)
			{
				aN.put((Integer)pairs.getKey(),(TreeSet<Integer>)pairs.getValue());
			}
			else
			{
				if(cpt < 13)
				{
					bN.put((Integer)pairs.getKey(),(TreeSet<Integer>)pairs.getValue());
				}
				else
				{
					cN.put((Integer)pairs.getKey(),(TreeSet<Integer>)pairs.getValue());
				}
			}
		}
		//end processing tree 
		//ready to start agents
		
		this.addBehaviour(new ArbitreBehaviour(this,taille,noeuds));
		try{
			
			o1[0] = aN;
			o1[1] = new TreeMap< Integer, TreeSet<Integer>>();
			o1[2] = this.getLocalName();
			AgentController a1 = cc.createNewAgent("Agent1", AgentColoriant.class.getName(), o1);
			
			o2[0] = bN;
			o2[1] = new TreeMap< Integer, TreeSet<Integer>>();
			o2[2] = this.getLocalName();
			AgentController a2 = cc.createNewAgent("Agent2", AgentColoriant.class.getName(), o2);
			
			o3[0] = cN;
			o3[1] = new TreeMap< Integer, TreeSet<Integer>>();
			o3[2] = this.getLocalName();
			AgentController a3 = cc.createNewAgent("Agent3", AgentColoriant.class.getName(), o3);
	
			a1.start();
			a2.start();
			a3.start();
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
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
	
	protected void TakeDown() 
	{
		//pour tuer l'agent quand on call delete.
	}
}
