node{
    properties([parameters([string(defaultValue: '127.0.0.1', description: 'give ip to build s site', name: 'ip', trim: false)])])
    stage("Install git"){
        sh "ssh ec2-user@${IP}  sudo yum install git python-pip -y"
    }
    stage("Clone a repo"){
        git 'git@github.com:florinen/Flaskex.git'
    }
    stage("Run App"){
        sh "ssh ec2-user@${IP}  sudo mkdir /flaskex 2> /dev/null"
    }
    stage("Copy files"){
        sh "scp -r * ec2-user@${IP}:/home/ec2-user/"
    }
    stage("Move files"){
        sh "ssh ec2-user@${IP}  sudo mv /home/ec2-user/* /flaskex"
    }
    stage("Install requirements"){
        sh "ssh ec2-user@${IP}     sudo pip install -r /flaskex/requirements.txt"
    }
    stage("move service to /etc"){
        sh " ec2-user@${IP}  sudo mv /flaskex/flaskex.service /etc/systemd/system"
    }
    stage("Start service"){
        sh "ec2-user@${IP}  sudo systemctl start flaskex"
    }
    
}