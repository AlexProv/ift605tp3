package tp3_sys;

import jade.core.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import sun.org.mozilla.javascript.internal.ObjArray;

public class CentralIntelligenceAgency {
	
	public static void main(String[] args) {
		final int NB_AGENT = 3;
		
		jade.core.Runtime rt = jade.core.Runtime.instance();
		Profile p = new ProfileImpl();

		ContainerController cc = rt.createMainContainer(p);
		
		TreeMap<Integer, TreeSet<Integer> > noeuds;
		noeuds = new TreeMap<Integer, TreeSet<Integer>>();
		
		try{
			Object[] o1 = new Object[1];
			Object[] o2 = new Object[1];
			Object[] o3 = new Object[1];
			Set<Integer> noeudsKeys = noeuds.keySet(); 
			int taille = noeudsKeys.size() / NB_AGENT;
			
			TreeMap<Integer, TreeSet<Integer> > aN = (TreeMap<Integer, TreeSet<Integer>>) noeuds.subMap(0, taille);
			TreeMap<Integer, TreeSet<Integer> > bN = (TreeMap<Integer, TreeSet<Integer>>) noeuds.subMap(taille, 2*taille);
			TreeMap<Integer, TreeSet<Integer> > cN = (TreeMap<Integer, TreeSet<Integer>>) noeuds.subMap(2*taille, noeudsKeys.size());
			o1[0] = aN;
			AgentController a1 = cc.createNewAgent("Agent1", AgentColoriant.class.getName(), o1);
			o2[0] = bN;
			AgentController a2 = cc.createNewAgent("Agent2", AgentColoriant.class.getName(), o2);
			o3[0] = cN;
			AgentController a3 = cc.createNewAgent("Agent3", AgentColoriant.class.getName(), o3);
			
			a1.start();
			a2.start();
			a3.start();
		}
		catch(Exception E){}

	}
	
	 private TreeMap<Integer, TreeSet<Integer> > noeuds;
	 
	 public CentralIntelligenceAgency(String fichierTexte,TreeMap<Integer, TreeSet<Integer> > noeuds)
	 {
		 StringTokenizer tokenizer = new StringTokenizer(fichierTexte);
		 
		 while (tokenizer.hasMoreTokens()) {
			 Integer token = Integer.getInteger(tokenizer.nextToken());
			 Integer token1 = Integer.getInteger(tokenizer.nextToken());
			 ajouterNoeud(token,token1,noeuds);
	     }
	 }
	 
	 private void ajouterNoeud(Integer a, Integer b,TreeMap<Integer, TreeSet<Integer> > noeuds)
	 {
		 TreeSet<Integer> tmp = noeuds.get(a);
		 if(tmp == null)
		 {
			 tmp = new TreeSet<Integer>();
		 }
		 tmp.add(b);
		 noeuds.put(a, tmp);
		 
		 tmp = noeuds.get(b);
		 if(tmp == null)
		 {
			 tmp = new TreeSet<Integer>();
		 }
		 tmp.add(a);
		 noeuds.put(b, tmp);
	 }
}
