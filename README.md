# Red Hat UKI DSA DevEx (Developer Experience) OpenShift Workshop4
Developer Experience Workshops for OCP4.x

This repo contains all the collateral and material for running the DevEx Workshops for OCP4.x

This workshop consists of standalone labs that introduce developer concepts to users in a nice, easy and informative way. These labs can be grouped together using a manifest file and used to generate a customer/technology specific workshop guide using the DocBuilder app provided in the app (source and release).

Link to a single sheet (double sided) document that describes the DevEx workshop and can be used as a flyer at events is available at the following URL:

https://docs.google.com/document/d/1gNINFJ599AqQM19082ac2n7VrIMrSURwXFLiDTIoB54/edit?usp=sharing

Link to DevEx promotional slides for use with sellers and customers is available at the following URL:

https://docs.google.com/presentation/d/1Pq0vo95mGE9ZFppdLr44nVf7S-5-hqTzC0qFpo2HpG8/edit?usp=sharing

We also provide a Dockerfile (images/terminal) and hosted image (quay.io/ilawson/devex4) for running a terminal window in a browser, which allows the entire course (cli's such as oc, tkn, kn) to be run from a browser with no installation on attendees machine.

Each lab is a completely standalone interactive experience that aims to express OpenShift concepts directly for developers. 

Currently we have labs for the following, but the open nature of this project is that we hope to have labs for *everything* OpenShift developer focused going forward:

Introduction to 'oc' and the object model  
Introduction to Application basics in OpenShift  
Introduction to Deployments and Builds in OpenShift  
Introduction to DevOps Approaches  
Introduction to the Software Defined Network from a dev perspective  
Introduction to the OpenShift RBAC model from a dev perspective  
Introduction to Persistent Volumes  
Camel-k on OpenShift  
Serverless Eventing with Camel-k on OpenShift  
Pipelines with Tekton on OpenShift  
Service Mesh on OpenShift  
Quay, Buildah and Podman
Building supersonic subatomic Java applications with Quarkus
OpenShift Serverless Serving

To run a workshop request an OpenShift4.3 Workshop via RHPDS. If you do not wish to use RHPDS create a 4.3 Cluster and ensure you create users for the attendees (userx with password openshift).

This is a dynamic version of the Workshop and allows creators to define and setup workshops with any combination of the labs provided. 

To build the documentation for the workshop you need to do the following:

1: Edit the variables in setup/playbooks/group_vars/all to setup cluster access for Ansible, choose links for the workshop (e.g. Rocket Chat link, see below), and choose which labs you want to be in the workshop
2: Change to the setup/playbooks directory
3: Run the Deploy_Docs.yml playbook - `ansible-playbook Deploy_Docs.yml`
4: The last task in the playbook will give you the starting point URL

If you would like to contribute labs there is a guide on how to write them and what to be aware of at https://docs.google.com/document/d/1DwSFGP1SO-1MOfEck6AGDjjU4d2k8YGYmpcMtxhaEZo/edit

All of the Google docs are available in PDF format in the docs/ directory of the repo for those who don't have access to the originals.

------------------------------------------------------------

When running a remote session it is highly advised to have Rocket Chat running on the Cluster for communication with the attendees. Instructions on how to set them up are as follows:

Log onto the Cluster as a Cluster Admin user

In the Administration view go to 'Projects'

Hit Create Project and create a project called 'chat'

Click on 'Operators/OperatorHub'

In the search box type 'rocket'

Click on the Rocketchat Operator

Click 'Install'

In Installation Mode change the target to 'A specific namespace on the cluster'

Select 'chat' in the project pulldown

Hit 'Subscribe'

Once the Operator status changes to 'Succeeded' click on the Rocketchat Operator

In the Operator Overview click on the subheading 'Rocketchat'

Click on 'Create Rocketchat'

Leave the YAML as is and hit 'Create'

Switch back to the topology view of the Developer viewpoint

Wait until all the Pods report blue and running (three of them)

Click on the URL icon (top right) of the Quick Start Pod

When Rocketchat renders create an admin user of your choice

Enter organisation information of your choice

On step 3 set the Server name to 'devex' and the type to 'Community'

On step 4 choose 'Keep standalone'

You will now get a Rocketchat with the channel #general

Select the icon for 'Create New' and create a channel called #support - set it to public, not private

Add some text for the welcome to the #general and #support

Copy the URL for the Route (minus the subdirectories) and go back to the Etherpad and paste it in there

3: Create a Short URL for the landing page on Etherpad

Go back to the UI for the Cluster (as Cluster Admin)

In Projects choose etherpad

Go to Networking/Routes

Hit 'Create Route'

Set the name to secureetherpad

Set the service to 'etherpad' (not MySQL)

Set the target port to 9001

Click the 'Secure Route'

Set TLS Termination to 'Edge'

Scroll down and hit create

Copy the 'Location' field (with the https://whatever)

Copy this into the relevant variable in setup/playbooks/group_vars/all (before you run the playbook)
