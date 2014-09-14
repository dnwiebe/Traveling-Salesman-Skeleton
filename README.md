## Traveling-Salesman-Skeleton

This is the skeleton for the kata we'll be using for Coding in the 
Clink #10 on November 1, 2014.  Since we'll be using Eclipse inside
the prison, it comes with .classpath and .project files for Eclipse.

However, it's small enough that importing it into any other IDE should
be straightforward even if the IDE doesn't automatically import Eclipse
projects.

### Problem Description

For this modified version of the Traveling Salesman, you'll want to 
write code that can, given a starting location, produce the route from 
that starting location that visits all the other locations for the 
lowest possible total cost. You'll have data for both costs of both 
time and distance, but you'll only use one kind at once.

#### Modifications

The classic version of Traveling Salesman requires that each route wind 
back around to its starting point, so that the figure it makes is 
closed.  However, in the version for this kata, you only have to visit 
each location once, so any route that ends at its starting point is 
more expensive than a similar route that lacks the last leg.

Also, the classic version requires that you arrive at every location 
exactly once.  We'll start out with an exactly-once solution, but later 
cards will admit an at-least-once solution.

**Note:** in the provided data, Miami can only be accessed via Atlanta.
This means that if you're writing an exactly-once algorithm, 1) every 
one of your routes must either end or begin in Miami, and 2) you won't 
be able to find a route starting in Atlanta (because you'd have to 
visit cities on both sides of Atlanta, which would put you in Atlanta 
at least twice since you're starting there).

### Data

#### [map.csv](blob/master/src/main/resources/map.csv)

This file contains comma-separated data you can reference in your 
solution.

I picked 22 major US cities mostly at random, drew lines between them 
until it looked like enough (it worked out to be 42 of them), and used 
Google Maps to find the costs of the legs in minutes and miles.  Each 
line of map.csv has information about the costs in both directions, 
which are frequently slightly different.

#### [times.csv](https://github.com/dnwiebe/Traveling-Salesman-Skeleton/blob/master/src/main/resources/times.csv)

I ran my visit-exactly-once algorithm to find a lowest-time route 
starting at each location; this file is the result.

It's not really intended to be machine-readable, although it can 
certainly be parsed.  Mostly it's for 1) admiring, and 2) using
for assertions in integration tests.

**Note:** I can't say for certain that my algorithm was completely 
correct; maybe it doesn't actually find the minimum-cost route, and 
your algorithm can find routes in the data that are cheaper than mine.  
If that's the case, good on you; but you could still use one of these 
routes for an integration test and then correct it when it fails.

#### [distances.csv](https://github.com/dnwiebe/Traveling-Salesman-Skeleton/blob/master/src/main/resources/distances.csv)

I also did the same thing with the distances in map.csv; those results 
are in distances.csv.

### Code

#### [CSVReader](https://github.com/dnwiebe/Traveling-Salesman-Skeleton/blob/master/src/main/java/salesman/util/CSVReader.java)

For some of the folks who come to Coding in the Clink, Java is not 
their first language.  For those people, figuring out how Java does 
file I/O and finding the right classes to write tests with, without 
access to the Internet from in prison, might be prohibitive.

Therefore, I've test-driven a small bare-bones CSV reader that you can 
use to get data in from the map.csv file, if you want.  It doesn't 
understand quotes and it doesn't do escaping; but other than that I 
have tried not to stifle your creativity, and CSVReader will take just 
about anything (filename, InputStream, Reader, BufferedReader) and 
convert it into a List of Lists of Strings, where the outer List 
contains rows in the CSV file, and the inner List contains fields in a 
row.

#### JUnit and Mockito

The test suite that drove CSVReader, or
[CSVReaderTest](https://github.com/dnwiebe/Traveling-Salesman-Skeleton/blob/master/src/test/java/salesman/util/CSVReaderTest.java), demonstrates several standard uses of both 
[JUnit](http://junit.org/), the _de facto_ Java standard for unit 
testing, and
[Mockito](https://code.google.com/p/mockito/), the _de facto_
Java standard for dynamic mocking, that you can swipe for your own 
code. Remember: no Internet in prison.

If you've had experience with unit testing and dynamic mocking in
other languages, CSVReaderTest will probably give you enough example
code to get you going.  If you haven't, your pair partner will probably
be able to explain it to you.  If not, there'll be at least one
facilitator drifting around during the event who knows both JUnit and
Mockito.
