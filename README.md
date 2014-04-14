
leaderboard
===========

leaderboard for a cloud service, written in java, as standalone and as smartfoxserver 2x extension, within FI-Content2

see http://mediafi.org
and http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard


## Setup server from standalone project

### Required java libraries <a name="javalibs"></a>
- Json library [minimal-json](https://github.com/ralfstx/minimal-json)
- Micro web framework [Spark](http://www.sparkjava.com)
- [MySQL connector](http://dev.mysql.com/downloads/connector/j/)

### Setup

#### Database

1. Install mysql server

2. Create database for highscore tables with name `mygame`

        CREATE DATABASE mygame

3. Create highscore table for each game with specific game ID (`<gameID>` stands for your game ID)
		
        CREATE TABLE mygame.<gameID> (
            highscore INT,
            playerID VARCHAR(50)
        );
	
#### Server

1. Make sure you have java installed

2. Copy required [jars](#javalibs) and LeaderboardREST.java to your server

3. Set up your classpath, i.e. 

        set CLASSPATH=.;spark-0.9.9.4-SNAPSHOT.jar;jetty-webapp-7.3.0.v20110203.jar;log4j-1.2.14.jar;servlet-api-3.0.pre4.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;mysql-connector-java-5.1.18-bin.jar
	
4. Compile

        javac LeaderboardREST.java
	
5. Start server

        java LeaderboardREST
