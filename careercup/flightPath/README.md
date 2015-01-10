# Flight Path
you have an vector like this 

[JFK, LXA, SNA, RKJ, LXA, SNA] 
each 2 group define a route. so, 

JFK -> LXA 
SNA -> RKJ 
LXA -> SNA 
Find the path from departure to destination. note: departure and destination are not known. 

The final destination should be 

JFK-> LXA -> SNA -> RKJ 
The function signature is something like this 

vector<string> findPath(vector<string> airports) 
{ 

} 

The airports (nodes) cannot be duplicated and the path should print all the airports (nodes)

** source: ** http://www.careercup.com/question?id=5768610725232640
