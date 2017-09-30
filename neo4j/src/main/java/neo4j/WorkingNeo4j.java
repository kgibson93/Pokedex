package neo4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.util.packed.PackedLongValues.Iterator;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class WorkingNeo4j {
	
	public static void createEdges(Session session){
		//think about the Dedoop methedology
			//start by blocking all types into "folders" and load that pokemon into each one
			//compare folders based on hard coded strengths and weaknesses (ex: the water folder does not need to compare to the Normal folder)
			//use switch statment
	}
	
	
	public static void createNodes(Session session, BufferedReader data) throws IOException{
		
		
		//reads the lines one at a time and stores the first line as the keys
		String dataLine = data.readLine();
		String header = dataLine;
		String[] splitHeader = header.split(",");
		
		for (int i = 0; i < splitHeader.length; i++){
			splitHeader[i] = splitHeader[i].replaceAll("[^\\x00-\\x7F]", "");
			splitHeader[i] = splitHeader[i].replaceAll("[ .]", "");
		}

		HashMap<String,String> statMap = new HashMap<String,String>();
		dataLine = data.readLine();
		
		while (dataLine != null){
			String[] splitData = dataLine.split(",");
			for(int i = 0; i < splitHeader.length; i++){
				if (!splitData[i].trim().equals("")){
					statMap.put(splitHeader[i],splitData[i]);
				}
			}
		}
			
		java.util.Iterator<Entry<String, String>> it = statMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey());
			System.out.println(pair.getValue());
		}

		
		/*
		while (dataLine != null){
			String[] splitData = dataLine.split(",");
			
			StringBuilder node = new StringBuilder("CREATE (a:Pokemon {");
			//node.append("CREATE (a:Pokemon {");
			
			for(int i = 0; i < splitHeader.length; i++){
				if (!splitData[i].trim().equals("")){
					node.append(splitHeader[i] + ":" + "'" + splitData[i] + "'");
					
					if(i != splitHeader.length - 1){
						node.append(",");
					}
				}
			}
			node.append("})");
			//System.out.println(node);
			System.out.println(node.toString());
			session.run(node.toString());
			dataLine = data.readLine();
		}
		*/
		
		
		
		//return node;
	}
	
	public static void main(String Args[]) throws IOException{
	
		//Establishes a connection to the Neo4j server 
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "neo4j1" ) );
		Session session = driver.session();
		
		//Reads in data, we are sticking with CSV for now
		BufferedReader data= new BufferedReader(new FileReader("C:/Users/Kevin/Documents/kaggle/pokemonTest.csv"));
		
		//Create and load the nodes into Neo4j
		WorkingNeo4j.createNodes(session,data);
		
		StatementResult result = session.run( "MATCH (a) WHERE a.Name = 'Charmander' RETURN a.Name AS Name" );
		while ( result.hasNext() )
		{
		    Record record = result.next();
		    System.out.println( record.get( "Name" ).asString());
		}
 
		
		
		////////////////////////////////Test queries///////////////
		//session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );
		//session.run( "CREATE (n:Person:Swedish)" );
		
		//MATCH (n) DETACH
		//DELETE n
/*
		StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
		while ( result.hasNext() )
		{
		    Record record = result.next();
		    System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
		}
		*/
		
		System.out.println("Done");
		
		session.close();
		driver.close();
		
	}

}
