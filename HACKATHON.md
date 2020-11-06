#

## Links

* How to set up the cluster: https://docs.google.com/document/d/1mnHIHaoP6-yeNITU_4QdkISfMhiuXpxdxKFgQuNaG9E/edit
* https://docs.google.com/document/d/1cP0Bby1we9sI7NDMJHgwqLVoz-IRmmrmF_2VwfyPUVM/edit

## Start up the cluster

TODO: Create the cluster in RHPDS, appropriately sized.

## Set up the cluster

1. Edit `setup/playbooks/group_vars/all` with token & URL information from the cluster that was set up.
1. ```sh
  # Deploy the Devex lab docs
  cd setup/playbooks
  ansible-playbook Deploy_Docs.yml
  ```
1. This generates a bunch of documentation, at URLs:
  * http://docs-devex.apps.cluster-9ebb.9ebb.example.opentlc.com/workshop/essentials/lab/applicationbasics
  * http://docs-devex.apps.cluster-9ebb.9ebb.example.opentlc.com/workshop/innovation/lab/rbac
  * http://docs-devex.apps.cluster-9ebb.9ebb.example.opentlc.com/workshop/intro/lab/intro
  * TODO MORE!

1. ```sh
  # Deploy Etherpad
  cd setup/etherpad
  ./deploy.sh
  ```
1. This creates an etherpad interface here: https://etherpad-etherpad.apps.cluster-9ebb.9ebb.example.opentlc.com/
1. Create a pad

Welcome to the L&G Hackathon!

Getting your userID
Please choose an untaken username from below and type your name next to it to claim it.
Once you have a username, you can use the labs.
The pasword for all user accounts is openshift

Cluster URL
The OpenShift console is reachable at:

Team1
user1
user2
user3
user4
user5
user6
user7
user8
user9
user10

Team2
user11
user12
user13
user14
user15
user16
user17
user18
user19
user20

Team3
user21
user22
user23
user24
user25
user26
user27
user28
user29
user30

Team4
user31
user32
user33
user34
user35
user36
user37
user38
user39
user40

Team5
user41
user42
user43
user44
user45
user46
user47
user48
user49
user50

Team6
user51
user52
user53
user54
user55
user56
user57
user58
user59
user60

Team7
user61
user62
user63
user64
user65
user66
user67
user68
user69
user70

Team8
user71
user72
user73
user74
user75
user76
user77
user78
user79
user80

Team9
user81
user82
user83
user84
user85
user86
user87
user88
user89
user90

Team10
user91
user92
user93
user94
user95
user96
user97
user98
user99
user100


## Create groups & projects

This creates teams `team1` -> `team10`.

Run Ansible script

## Operator instances

AMQ Broker, Fuse Online are per-project.

All others are shared.

## Object creation

Broker instance, Kafka instance, etc.
