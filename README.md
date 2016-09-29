# TasKing
Application tracks tasks within an organisation

#How it works
Most of the code is self explanatory. However:
The jar was to run from a shared drive with no db, hence persistence of data is achieved using json files.
The code also allows for export of all the data it contains into a csv file.

#Installation
package up the jar, create the tasknum.txt file and use the bat files to create new users. Run the Jar file to use the tool.

#Features
Users can add tasks for themselves with an estimated time of completion. They can resolve this on completion with an actual time of completion.
If user is a manager, he/she can add tasks for reportees with estimated time of completion. Reportees can resolve these with an actual time of completion
If user is a manager, he/she can add tasks with an estimated time to a "pool" that will be visible to all reportees of that user. Reportees can pull/release tasks or mark a pulled task for complettion with actual completion time.
Manager can export all data for all completed tasks of reportees and analyze for efficiency within teams.


#Caveats
All user ids have to be 8 digit.
