leaderboard
===========

a cloud service, written in java, as standalone and as smartfoxserver 2x extension, within <a href="http://mediafi.org">FI-Content2</a>. It allows to post and retrieve high scores of games.

API specification:
http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard

Example usage:
http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard.developerguide

## Setup server from standalone project

### Required java libraries <a name="javalibs"></a>
- Json library [minimal-json](https://github.com/ralfstx/minimal-json)
- Micro web framework [Spark](http://www.sparkjava.com)
- [MySQL connector](http://dev.mysql.com/downloads/connector/j/)

### Setup

#### Database

1. Install mysql server

2. Create a file config.properties, here is an example:
	```
	# escape the characters #, !, =, and : with a preceding backslash

	# (local) address of database
	url = jdbc\:mysql\://localhost\:3306/mygame

	# database user/password
	user = root
	password = secret123

	# logfile is optional
	logfile = log.txt
	```

#### Server

1. Make sure you have java installed

2. Copy required [jars](#javalibs) and LeaderboardREST.java to your server

3. Set up your classpath, i.e. 

	set CLASSPATH=.;spark-0.9.9.4-SNAPSHOT.jar;jetty-webapp-7.3.0.v20110203.jar;log4j-1.2.14.jar;servlet-api-3.0.pre4.jar;slf4j-api-1.6.1.jar;slf4j-log4j12-1.6.1.jar;mysql-connector-java-5.1.18-bin.jar

	~~set CLASSPATH=commons-codec-1.9.jar;jetty-io-9.0.2.v20130417.jar;jetty-security-9.0.2.v20130417.jar;log4j-1.2.14.jar;jetty-http-9.0.2.v20130417.jar;jetty-servlet-9.0.2.v20130417.jar;jetty-xml-9.0.2.v20130417.jar;jetty-webapp-9.0.2.v20130417.jar;slf4j-log4j12-1.6.1.jar;mysql-connector-java-5.1.25-bin.jar;slf4j-api-1.6.1.jar;jetty-util-9.0.2.v20130417.jar;javax.servlet-3.0.0.v201112011016.jar;spark-core-1.1.1.jar;com.eclipsesource.json_2013-10-22.jar;jetty-server-9.0.2.v20130417.jar;slf4j-api-1.7.2.jar;mysql-connector-java-5.1.25-bin.jar;gson-2.2.4.jar;com.eclipsesource.json_2013-10-22.jar;.~~

4. Compile

        javac LeaderboardREST.java
	
5. Start server

        java LeaderboardREST
