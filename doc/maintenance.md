## Resumator Database Backup

Everyday whole backup of the database server is generated in a file named within the format of the current day.Resumator database is backed up daily via 2 scripts.

####1-)backup.sh
This is a script that runs pg)dump command to take backup and store it in the file system.
It is located on /opt/scripts/ of the resumator-nokube machine. It is running every night to backup database in tar format and copy from the database container into the resumator-nokube machine.

####2-)copyToRemote.sh
This is a script that gets the backup file of the database and transfers it into a google cloud storage.It uses "gsutil" to do the transfer. It is also run once every night,after the backup is created. It is located on /opt/scripts/ of the resumator-nokube machine. Eventual backups should take their place in *eu.artifacts.sixth-storm-119714.appspot.com* bucket,under *resumator-backups* folder.


> Both these scripts are set up to run in crontab.Logs of the script outputs are available in /opt/scripts/logs folder. Backup files are available in /opt/scripts/backups.