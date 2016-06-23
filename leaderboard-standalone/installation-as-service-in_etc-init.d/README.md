Installation as a service
=========================

- copy the file 'leaderboard' to /etc/init.d/
- make it executable with `chmod 755 leaderboard`
- adjust the path (in `DAEMON_PATH`)

now you can use the following commands:
```
service leaderboard start
service leaderboard status
service leaderboard restart
service leaderboard stop
```

In CentOS
---------
to start automatically at each reboot by adding link to /etc/rc3.d/ with:
```
cd /etc/rc3.d/
ln -s /etc/init.d/leaderboard S99uded
```
