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
		/*
		ACLMessage  msg = myAgent.receive();
		if(msg != null){

			agent.send(formateReplyWithLog(msg));
			
		}
		else {
			block();
		}
		*/
		
		TreeMap<Integer, Integer> test = initialisateurCouleur(100);
		TreeMap< Integer, TreeSet<Integer>> cnv = new TreeMap< Integer, TreeSet<Integer>>();
		TreeSet<Integer> a = new TreeSet<Integer>();
		a.add(0);
		cnv.put(1, a);
		a = new TreeSet<Integer>();
		a.add(0);
		cnv.put(2, a);
		TreeMap<Integer, Integer> testa = notWantedColor(100, cnv);
		System.out.println("a");
		//equivalent de ton RUN dans un thread Ben
		System.out.println("ben travail!");
		
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
