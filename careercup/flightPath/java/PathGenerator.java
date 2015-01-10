/** Flight Path generator. 
 * Details in the README. 
 * Author: Sameer Jagdale
*/
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
public class PathGenerator {
	public static List<String> findPath(List<String> airports) {
		if((airports.size() % 2) != 0 ) {
			throw new RuntimeException("List of airports have to be in pairs.");
		}
		
		HashMap<String,String> routeMap = new HashMap<String,String>();
		for(int i = 0; i < airports.size(); i += 2) {
			routeMap.put(airports.get(i),airports.get(i+1));	
		}
		Set<String> keySet = routeMap.keySet();
		Collection<String> valueSet = routeMap.values();
		String source = null;
		for( String key : keySet) {
			if(!valueSet.contains(key)) {
				source = key;
			}
		}
		if(source == null) {
			return null;
		}
		String dest = null;
		for( String value : valueSet) {
			if(!keySet.contains(value)) {
				dest = value;
			}
		}
		if(dest == null) {
			return null;	
		}
		HashSet<String> visitedSet = new HashSet<String>();
		String currAirport = source;
		List<String> routeList = new ArrayList<String>();
		while(visitedSet.size() < airports.size() && !currAirport.equals(dest)) {
			routeList.add(currAirport);	
			if(!routeMap.containsKey(currAirport)) {
				return null;	
			}
			if(routeMap.get(currAirport) == null) {
				return null;
			}
			if(visitedSet.contains(routeMap.get(currAirport))) {
				return null;
			}
			visitedSet.add(currAirport);
			currAirport = routeMap.get(currAirport);
		}		
		routeList.add(dest);
		return routeList;
	}
	
	public static void main(String args[]) {
		List<String> list = new ArrayList<String>();
		list.add("JFK");	
		list.add("LXA");	
		list.add("SNA");	
		list.add("RKJ");	
		list.add("LXA");	
		list.add("SNA");	
		list = findPath(list);
		for(String airport : list) {
			System.out.println(airport);
		}
	}
}
