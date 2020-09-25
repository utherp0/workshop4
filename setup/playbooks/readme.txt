This playbook will automatically install and configure the prerequisites advanced workshop.

The pre-requisites for running this playbook are an installation of Ansible (2.7.x), and installation after installtion of
Ansible via pip of 'openshift' and 'PyYAML', i.e.

'pip3 install openshift'
'pip3 install PyYAML'

To start, please clone this git repository so you can run the playbook.

Once you have provisioned your RHPDS Openshift cluster,

you can now update the "cluster" file with your values. This file is found at {Clone_Location}/workshop4/setup/playbooks/group_vars/all

It needs the Openshift *API* URL and a login token

To capture this, go to the Openshift console and choose "Copy Login Command"

This will give you the URL, and a token.

Please copy these and paste them into the relevant location in the "all" file.

Having changed the file

cd {Clone_Location}/workshop4/playbooks

type:

ansible-playbook Deploy_Advanced_Course.yml

Modify the manifest for the labs that you want to include :

Update the files in : {Clone_Location}/workshop4/setup/playbooks/group_vars/all/manifest
