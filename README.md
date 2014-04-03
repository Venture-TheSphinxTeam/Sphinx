Sphinx
   By Team Venture

  Sphinx is a web based project management catalog and event subscription system.  
The goal of this system is to provide visibility and communication about projects
within an organization. Employees will be able to browse through their companies 
projects using various search criteria, subscribe to project related events, and 
receive periodic updates about their subscribed projects when aspects about them change. 
Users of the system will also be able to comment on projects with their feedback and vote 
if they like or dislike a project.

  To collect the data it operates on, Sphinx will interface with external project
management system APIs acquire project metadata and event information. The initial
implementation of this system will interact with a custom API for the JIRA project
management system (https://www.atlassian.com/software/jira). Once the system has
been validated with the Jira integration, interfaces with other project management
systems will be considered.

Opensource software utilized:
  - [Mongo DB] (http://www.mongodb.org) (Database)
  - [Play](http://www.playframework.com/)  (Web framework)
  - [Elasticsearch](http://www.elasticsearch.org/) (maybe not)
  - [Jersey](https://jersey.java.net/) (REST client/server)
  - [Jackson](https://github.com/FasterXML/jackson) (JSON Parser)
  - [Play-Jongo](https://github.com/alexanderjarvis/play-jongo)/[Jongo](http://jongo.org/) (Database Connectors)


##Installing and Running:
1. Install mongodb (http://docs.mongodb.org/manual/installation/)
2. Download play
3. Unzip play
4. Run play.bat or ./play in the directory of this project


##Setting Up Your Environment:

No matter what environment you're running, you'll need to grab the play framework from http://www.playframework.com/

1. Unzip Play
2. Make sure that javac and java are on your Path
3. cd to the project (That you totally already cloned)
4. run play.bat in this directory
  * The terminal command on Windows will be something like ../../path/to/play/play.bat
  * On Mac or Linux, you should be able to run the non bat play application instead
5. If you are using eclipse, type "eclipse" once the play terminal starts
  *For other IDEs, check out http://www.playframework.com/documentation/2.1.x/IDE
6. Now you should be able to import this as an eclipse project!


###But What About the Libraries?
Play uses the sbt build tool for both building all of the internal code and for managing dependencies.  When you first compile/run the project, the libraries should all be downloaded by sbt through the maven repositories.  If you do run into any problems with the dependencies resolving, please open an issue or contact smy2748@rit.edu so we can look into it.

###Longer Term Setup
* You'll probably want to add play to your path
