package tp3_sys;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class ColorBehaviour extends MessagingBehaviour{
	
	TreeMap< Integer, TreeSet<Integer>> noeuds;
	Agent agent;
	String agentLocalName;
	
	public ColorBehaviour(Agent a, TreeMap< Integer, TreeSet<Integer>> noeuds) {
		super(a);
		this.agent = a; 
		this.noeuds = noeuds;
	}
	

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		ACLMessage  msg = myAgent.receive();
		if(msg != null){

			agent.send(formateReplyWithLog(msg));
			
		}
		else {
			block();
		}
		
		//equivalent de ton RUN dans un thread Ben
		System.out.println("ben travail!");
		
	}

	
	
	private Object initialisateurCouleur(int nbCouleur)
	{
		TreeMap<Integer, TreeSet<Integer>> couleurPossible = new TreeMap<Integer, TreeSet<Integer>>();
		ArrayList<Integer> elementNonColorie = new ArrayList<Integer>();
		
		
		for(Map.Entry<Integer, TreeSet<Integer>> entry : noeuds.entrySet())
		{	
			couleurPossible.put(entry.getKey(), new TreeSet<Integer>());
			elementNonColorie.add(entry.getKey());
		}
		
		
		for(Map.Entry<Integer, TreeSet<Integer>> entry : couleurPossible.entrySet())
		{
			for(int i = 0; i < nbCouleur; ++i)
			{
				entry.getValue().add(i);
			}
		}
		
		
		
		return null;
	}
	
	
	
	private TreeMap<Integer, Integer> wantedColor(int nbCouleur, TreeMap<Integer, Integer> couleurVoulue)
	{
		TreeMap<Integer, TreeSet<Integer>> couleurPossible = new TreeMap<Integer, TreeSet<Integer>>();
		ArrayList<Integer> elementNonColorie = new ArrayList<Integer>();
		
		for(Integer noeud : couleurVoulue.keySet())
		{
			noeuds.remove(noeud);
		}
		
		for(Integer key : noeuds.keySet())
		{	
			couleurPossible.put(key, new TreeSet<Integer>());
			elementNonColorie.add(key);
		}
		
		
		for(Map.Entry<Integer, TreeSet<Integer>> entry : couleurPossible.entrySet())
		{
			for(int i = 0; i < nbCouleur; ++i)
			{
				entry.getValue().add(i);
			}
		}
		return null;
	}
	
	
	public TreeMap<Integer, Integer> colorieur(TreeMap<Integer, Integer> couleur, TreeMap<Integer, TreeSet<Integer>> couleurPossible )
	{
		if(couleurPossible.isEmpty())
		{
			return couleur;
		}
		else
		{
			Entry<Integer, TreeSet<Integer>> entry  = couleurPossible.firstEntry();
			TreeSet<Integer> tree = entry.getValue();
			
			TreeMap<Integer, TreeSet<Integer>> tmp = (TreeMap<Integer, TreeSet<Integer>>) couleurPossible.clone();
			
			for(Integer i : tree)
			{
				couleur.put(entry.getKey(), i);
				entry.getKey();
			}
		}
		
		return null;
	}

}
