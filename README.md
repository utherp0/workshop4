# workshop4
Developer Experience Workshops for OCP4.3

This repo contains all the collateral and material for running the DevEx Workshops for OCP4.3

To run a workshop request an OpenShift4.3 Workshop via RHPDS. If you do not wish to use RHPDS create a 4.3 Cluster and ensure you create users for the attendees (userx with password openshift).

The workshop has been split into two components, a 'basics' and an 'advanced' module. These are built separately as documents and can be given independently - the course takes about 6-8 hours to do them both depending on attendee level.

Generate the documentations for the attendees using Maven - clone this repo, go to the documentation directories (documentationbasic and documentationadvanced) and run:

mvn clean package -DfacilitatorName='YOUR NAME' -DfacilitatorEmail='YOUR EMAIL' -DfacilitatorTitle='YOUR JOB TITLE' -DwebConsoleUrl='CONSOLE URL FOR OCP4'

It is worth creating short URLs for the following URLs to be provided to the attendees:

1: The Console URL (the console login point for the attendees)
2: The API Endpoint URL (the URL used to make direct calls, primarily for the Tekton chapter)

For the advanced course there are some Operator pre-requisites that are installed using an Ansible playbook - please check the documentation and the playbooks directory of the repo for details

