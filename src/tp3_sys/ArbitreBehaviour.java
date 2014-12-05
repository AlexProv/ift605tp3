package tp3_sys;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class ArbitreBehaviour extends Behaviour {

	private static final long serialVersionUID = -7958667562651848671L;
	protected Agent agent;
	boolean done;
	int size; 
	TreeMap<Integer, TreeSet<Integer>> noeuds;
	TreeMap<Integer, Integer> tester;
	
	public ArbitreBehaviour(Agent a,int size,TreeMap<Integer, TreeSet<Integer>> noeuds) {
		super(a);
		agent = a;
		this.size = size;
		this.noeuds = noeuds;
		done = false;
		tester = new TreeMap<Integer, Integer>();
	}
	
	
	@Override
	public void action() {
	    done = false;
	    
		TreeMap<String, TreeMap<Integer, Integer>> agentResults = new TreeMap<String, TreeMap<Integer, Integer>>();
		TreeMap<String,TreeMap< Integer, TreeSet<Integer>>> agentCouleurNonVoulue = new TreeMap<String,TreeMap< Integer, TreeSet<Integer>>>(); 
		
		agentCouleurNonVoulue.put("Agent1",new TreeMap< Integer, TreeSet<Integer>>());
		agentCouleurNonVoulue.put("Agent2",new TreeMap< Integer, TreeSet<Integer>>());
		agentCouleurNonVoulue.put("Agent3",new TreeMap< Integer, TreeSet<Integer>>());
		
		agentResults.put("Agent1", new TreeMap<Integer, Integer>());
		agentResults.put("Agent2", new TreeMap<Integer, Integer>());
		agentResults.put("Agent3", new TreeMap<Integer, Integer>());
		
	
		//arbiter logic
		ACLMessage msg = agent.receive();
		if(msg != null)
		{
			if(msg.getPerformative()== ACLMessage.REQUEST){
				String agentName = msg.getSender().getLocalName();
				String resultData = msg.getContent();
				TreeMap<Integer, Integer> result = CentralIntelligenceAgency.stringToTree(resultData);
				
				agentResults.put(agentName, result);
				
				boolean conflit = false; 
				agentloop: for(String key : agentResults.keySet())
				{
					TreeMap<Integer, Integer> agentAnswer = agentResults.get(key); //get agent solutions
					
					for(Integer nKey : agentAnswer.keySet()) //for all 
					{
						if(tester.get(nKey) == null)
						{
							TreeSet<Integer> keyNoeuds = noeuds.get(nKey);
							Integer masterColor = agentAnswer.get(nKey);
							for(Integer colorKey : keyNoeuds)
							{
								Integer color = tester.get(colorKey);
								if(color == masterColor)
								{
									conflit = true;
									addToCouleurNonVoulue(agentCouleurNonVoulue, key, nKey, agentAnswer.get(nKey));
									break agentloop;
								}
							}
							tester.put(nKey, agentAnswer.get(nKey));
							
						}
						else
						{
							if(!agentAnswer.get(nKey).equals(tester.get(nKey)))
							{
								//conflit 
								addToCouleurNonVoulue(agentCouleurNonVoulue, key, nKey, agentAnswer.get(nKey));
								conflit = true;
								break agentloop;
							}
							else
							{
								TreeSet<Integer> keyNoeuds = noeuds.get(nKey);
								Integer masterColor = tester.get(nKey);
								for(Integer colorKey : keyNoeuds)
								{
									Integer color = tester.get(colorKey);
									if(color == masterColor)
									{
										conflit = true;
										addToCouleurNonVoulue(agentCouleurNonVoulue, key, nKey, agentAnswer.get(nKey));
										break agentloop;
									}
								}
							}
						}
					}
				}
				if(conflit)
				{
					//restart l'algo ac les nouvelle list de bad colors
					//send message to all agents with the new notWantedcolor list 
					//or send message that job's done
					for(String agentKey : agentCouleurNonVoulue.keySet())
					{
						TreeMap< Integer, TreeSet<Integer>> badcolors = agentCouleurNonVoulue.get(agentKey);
						ACLMessage msgAnswer = new ACLMessage(ACLMessage.REQUEST);
						msgAnswer.setContent(CentralIntelligenceAgency.graphToString(badcolors));
						msgAnswer.addReceiver( new AID( agentKey, AID.ISLOCALNAME) );
					    agent.send(msgAnswer);
					}
					
				}
				else
				{
					boolean tmp = true;
					for(int i = 1;i<=size;i++)
					{
						if(tester.get(i) == null)
							tmp = false;
					}
					done = tmp;
				}
			}
		}
		else
		{
			block();
		}
	}
	
	
	private void addToCouleurNonVoulue(TreeMap<String,TreeMap< Integer, TreeSet<Integer>>> tree, String agent, Integer noeud, Integer badColor)
	{
		TreeMap< Integer, TreeSet<Integer>> tempTree = tree.get(agent);
		try{
			TreeSet<Integer> listBadColor = tempTree.get(noeud);
			listBadColor.add(badColor);
		}
		catch(Exception E)
		{
			TreeSet<Integer> listBadColor = new TreeSet<Integer>();
			listBadColor.add(badColor);
			tempTree.put(noeud, listBadColor);
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
	
	@Override
	public boolean done() {
		if(done)
		{
			String[] agents = new String[3];
			agents[0] = "Agent1";
			agents[1] = "Agent2";
			agents[2] = "Agent3";
			
			for(String a : agents)
			{
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("Beaucoup de sang.");
				msg.addReceiver( new AID( a, AID.ISLOCALNAME) );
				agent.send(msg);
			}
			System.out.println("Les couleurs sont\n(les differentes couleurs sont representees pare des nombres):");
			for(Integer i : tester.keySet())
			{
				System.out.println("Noeud" + i + " = " + tester.get(i));
			}
			System.out.println("Fin de l'execution...");
			System.exit(0);//La seule maniere de vraiment terminer l'execution trouve qui fonctionne. 
		}
		return done;
	}
}
