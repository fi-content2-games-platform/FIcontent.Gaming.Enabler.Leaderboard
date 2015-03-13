creating a service in in /etc/init.d/   in file 'leaderboard':

(see init.d-leaderboard-script)

(now you can use the following commands:
service leaderboard start
service leaderboard status
service leaderboard restart
service leaderboard stop


(start automatically at each reboot by adding link to /etc/rc3.d/ with:)
ln -s /etc/init.d/leaderboard S99leaderboard
