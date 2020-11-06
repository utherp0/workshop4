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





## Create groups & projects

This creates teams `team1` -> `team10`.

Run Ansible script

## Operator instances

AMQ Broker, Fuse Online are per-project.

All others are shared.

## Object creation

Broker instance, Kafka instance, etc.
