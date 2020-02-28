This playbook will automatically install and configure the prerequisites for the Knative and CamelK labs.

To use the playbook.

You need both Ansible installed locally, and the Openshift client called "oc"

To start, please clone this git repository so you can run the playbook.

Once you have provisioned your RHPDS Openshift cluster,

you can now update the "all" file with your values. This file is found at {Clone_Location}/workshop4/playbooks/group_vars

It needs the Openshift URL and a login token

To capture this, go to the Openshift console and choose "Copy Login Command"

This will give you the URL, and a token.

Please copy these and paste them into the relevant location in the "all" file.

Having changed the file

cd {Clone_Location}/workshop4/playbooks

type:

ansible-playbook Deploy_Advanced_Course.yml