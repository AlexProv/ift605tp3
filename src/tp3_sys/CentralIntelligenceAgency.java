package tp3_sys;

import java.util.TreeSet;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CentralIntelligenceAgency {
	 private TreeMap<Integer, TreeSet<Integer> > noeuds;
	 
	 public CentralIntelligenceAgency(String fichierTexte)
	 {
		 StringTokenizer tokenizer = new StringTokenizer(fichierTexte);
		 
		 while (tokenizer.hasMoreTokens()) {
			 Integer token = Integer.getInteger(tokenizer.nextToken());
			 Integer token1 = Integer.getInteger(tokenizer.nextToken());
			 ajouterNoeud(token,token1);
	     }
	 }
	 
	 private void ajouterNoeud(Integer a, Integer b)
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
