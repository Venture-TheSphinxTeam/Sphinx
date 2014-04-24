cd ~/Sphinx/
/opt/play stop

git stash save -u
git pull
git stash pop

cp conf/application.conf.prod conf/application.conf
/opt/play "start 80"
