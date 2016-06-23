leaderboard
===========

a cloud service, written in java, as standalone and as smartfoxserver 2x extension, within <a href="http://mediafi.org">FI-Content2</a>. It allows to post and retrieve high scores of games.

API specification:
http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard

Example usage:
http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard.developerguide

## Setup server from standalone project

### Requirements
The software was tested on Linux and Windows and should run on any platform supporting Java and MySQL.


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
	url = jdbc\:mysql\://localhost\:3306/

	# database user/password
	user = root
	password = secret123

	# logfile is optional
	logfile = log.txt
	```

#### Server

1. Make sure you have java installed

2. Copy/clone the files in leaderboard-standalone

3a. [Install the leaderboard as a service](leaderboard-standalone/installation-as-service-in_etc-init.d)
or
3b. start the server manually with leaderboard-standalone/bin/run.*

### Testing
One good way to get started is to use the div/admin-and-test.html file. For further information, please refer to the [development guide] (http://wiki.mediafi.org/doku.php/ficontent.gaming.enabler.leaderboard.developerguide).