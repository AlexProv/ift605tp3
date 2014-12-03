package tp3_sys;

import jade.core.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;


public class CentralIntelligenceAgency {
	
	public static final int NB_AGENT = 3;	
	
	public static void main(String[] args) throws IOException {
		BufferedReader saveFile = new BufferedReader(new FileReader("graph.grr"));
		String aux = "";
		StringBuilder builder = new StringBuilder();
		
		while((aux = saveFile.readLine()) != null)
		{
			builder.append(aux + '\n');
		}
		String data = builder.toString();
		TreeMap<Integer, TreeSet<Integer> > graph = graphBuilder(data);

		jade.core.Runtime rt = jade.core.Runtime.instance();
		Profile p = new ProfileImpl();

		ContainerController cc = rt.createMainContainer(p);
		
		TreeMap<Integer, TreeSet<Integer> > noeuds;
		noeuds = new TreeMap<Integer, TreeSet<Integer>>();
		
		try{
			Object[] oArbitre = new Object[1];
			Object[] o1 = new Object[1];
			Object[] o2 = new Object[1];
			Object[] o3 = new Object[1];
			Set<Integer> noeudsKeys = noeuds.keySet(); 
			int taille = noeudsKeys.size() / NB_AGENT;
			
			TreeMap<Integer, TreeSet<Integer>> aN = new TreeMap<Integer, TreeSet<Integer>>();
			TreeMap<Integer, TreeSet<Integer>> bN = new TreeMap<Integer, TreeSet<Integer>>();
			TreeMap<Integer, TreeSet<Integer>> cN = new TreeMap<Integer, TreeSet<Integer>>();
			
			Iterator it = noeuds.entrySet().iterator();
			int cpt = 0;
			while(it.hasNext())
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
			o1[0] = aN;
			o1[0] = graph;
			AgentController a1 = cc.createNewAgent("Agent1", AgentColoriant.class.getName(), o1);
			o2[0] = bN;
			AgentController a2 = cc.createNewAgent("Agent2", AgentColoriant.class.getName(), o2);
			o3[0] = cN;
			AgentController a3 = cc.createNewAgent("Agent3", AgentColoriant.class.getName(), o3);

			AgentController aArbitre = cc.createNewAgent("AgentArbitre", AgentArbitre.class.getName(), oArbitre);
			a1.start();
			a2.start();
			a3.start();
			aArbitre.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	 public static String treeToString(TreeMap<Integer, Integer> tree)
	{
		StringBuilder builder = new StringBuilder();
		
		for(Entry<Integer, Integer> i : tree.entrySet())
		{
			builder.append(i.getKey() + " " + i.getValue() + "\n" );
		}
		return builder.toString();
	}
	
	public static TreeMap<Integer, Integer>  stringToTree(String s)
	{		
		TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();
		 
		 StringTokenizer tokenizer = new StringTokenizer(s);
		 
		 while (tokenizer.hasMoreTokens()) {
			 Integer token = Integer.parseInt(tokenizer.nextToken());
			 Integer token1 = Integer.parseInt(tokenizer.nextToken());
			 
			 tree.put(token,token1);
	     }
		 
		 return tree;
	}
	
	 
	 static public TreeMap<Integer, TreeSet<Integer> > graphBuilder(String fichierTexte)
	 {
		 TreeMap<Integer, TreeSet<Integer> > tmpNoeuds = new TreeMap<Integer, TreeSet<Integer> >();
		 
		 StringTokenizer tokenizer = new StringTokenizer(fichierTexte);
		 
		 while (tokenizer.hasMoreTokens()) {
			 //System.out.println(tokenizer.nextToken());
			 //System.out.println(tokenizer.nextToken());
			 Integer token = Integer.parseInt(tokenizer.nextToken());
			 Integer token1 = Integer.parseInt(tokenizer.nextToken());
			 //System.out.println(tokenizer.nextToken());
			 
			 ajouterNoeud(token,token1,tmpNoeuds);
	     }
		 
		 return tmpNoeuds;
	 }
	 
	 static private void ajouterNoeud(Integer a, Integer b,TreeMap<Integer, TreeSet<Integer> > noeuds)
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
