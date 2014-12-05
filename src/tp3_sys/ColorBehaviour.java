package tp3_sys;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ColorBehaviour extends MessagingBehaviour{
	
	TreeMap< Integer, TreeSet<Integer>> noeuds;
	Agent agent;
	String agentLocalName;
	TreeMap< Integer, TreeSet<Integer>> couleurNonVoulue;
	String arbitre;
	boolean done = false;
	
	public ColorBehaviour(Agent a, TreeMap< Integer, TreeSet<Integer>> noeuds,TreeMap< Integer, TreeSet<Integer>> couleurNonVoulue,String arbitre) {
		super(a);
		this.agent = a; 
		this.noeuds = noeuds;
		this.couleurNonVoulue = couleurNonVoulue;
		this.arbitre = arbitre;
	}
	

	@Override
	public void action() {
		
		TreeMap<Integer, Integer> answer = notWantedColor(100, couleurNonVoulue);
		String data = CentralIntelligenceAgency.treeToString(answer);
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setContent(data);
        msg.addReceiver( new AID( arbitre, AID.ISLOCALNAME) );
	    agent.send(msg);
	    
	    ACLMessage arbiterAnswer = agent.receive();
	    if(arbiterAnswer!=null)
	    {
			if(arbiterAnswer.getPerformative() == ACLMessage.REQUEST)
			{
				couleurNonVoulue = null; //need to cast msg to couleurNonVoulue 
			}
			else if(arbiterAnswer.getPerformative() == ACLMessage.INFORM)
			{
				done = true;
			}
	    }
	    
	}

	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return done;
	}
	
	private TreeMap<Integer, Integer> initialisateurCouleur(int nbCouleur)
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
		
		
		TreeMap<Integer, Integer> resultat = colorieur(new TreeMap<Integer, Integer>(), couleurPossible);
		if(resultat == null)
		{
			//TODO envoye erreur pas possible colorier avec si peu de couleur
		}
		else
		{
			// OK!
		}
		return resultat;
	}
	
	
	private TreeMap<Integer, Integer> notWantedColor(int nbCouleur, TreeMap< Integer, TreeSet<Integer>> couleurNonVoulue)
	{
		TreeMap<Integer, TreeSet<Integer>> couleurPossible = new TreeMap<Integer, TreeSet<Integer>>();
		ArrayList<Integer> elementNonColorie = new ArrayList<Integer>();
		
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
		
		for(Integer noeud : couleurNonVoulue.keySet())
		{
			if(couleurPossible.get(noeud) != null)
			{
				for(Integer i :couleurNonVoulue.get(noeud))
				{
					TreeSet<Integer> couleurPotentiel = couleurPossible.get(noeud);
					couleurPotentiel.remove(i);
					couleurPossible.put(noeud, couleurPotentiel);
				}
			}
		}
		
		TreeMap<Integer, Integer> resultat = colorieur(new TreeMap<Integer, Integer>(), couleurPossible);
		if(resultat == null)
		{
			//TODO envoye erreur pas possible colorier avec les contraintes de couleur demande
		}
		else
		{
			// OK!
		}
		return resultat;
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
			Integer noeudCourant = entry.getKey();
			
			TreeMap<Integer, TreeSet<Integer>> cloneCouleurPossible = (TreeMap<Integer, TreeSet<Integer>>) couleurPossible.clone();
			TreeMap<Integer, Integer> cloneCouleur = (TreeMap<Integer, Integer>) couleur.clone();
			for(Integer couleurPotentiel : entry.getValue())
			{
				couleur.put(noeudCourant, couleurPotentiel);
				cloneCouleurPossible.remove(noeudCourant);
				
				// retire les couleurs non permises
				for(Integer i : noeuds.get(noeudCourant))
				{
					TreeSet<Integer> couleurPourNoeud = cloneCouleurPossible.get(i);
					if(couleurPourNoeud != null)
					{
						couleurPourNoeud.remove(couleurPotentiel);
						cloneCouleurPossible.put(i,couleurPourNoeud);
					}
				}				
				
				cloneCouleur.put(noeudCourant, couleurPotentiel);
				
				TreeMap<Integer, Integer> resultat = colorieur(cloneCouleur, cloneCouleurPossible);
				if(resultat != null)
				{
					return resultat;
				}
				else
				{
					cloneCouleur.remove(noeudCourant);
				}
				
			}
		}
		
		return null;
	}

}
