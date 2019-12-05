# workshop4
Developer Experience Workshop for OCP4.2+

This repo contains all the collateral and material for running the DevEx Workshop for OCP4.2+

To run a workshop request an OpenShift4.2 Workshop via RHPDS.

Generate the documentation for the attendees using Maven - clone this repo, go to the documentation directory and run:

mvn clean package -DfacilitatorName='YOUR NAME' -DfacilitatorEmail='YOUR EMAIL' -DfacilitatorTitle='YOUR JOB TITLE' -DwebConsoleUrl='CONSOLE URL FOR OCP4' -DterminalUrl='TERMINAL URL FOR OCP4'

It is worth creating short URLs for the console and terminal using the internal Red Hat URL shortener for ease of use by the attendees.

The terminal URL can be derived by logging on to the RHPDS cluster as the opentlc-mgr user and looking at Networking/Routes for the Terminal application. 

There are a couple of pre-requisites you need to do before starting the course:

Log on as cluster admin (opentlc-mgr)

Go to Operator Hub

Search for ‘Pipeline’

Select the Community OpenShift Pipelines Operator

Install for all namespaces in the cluster

Go to Operator Hub

Select Red Hat Service Mesh

Install for all namespaces in the cluster



