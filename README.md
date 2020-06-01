# workshop4
Developer Experience Workshops for OCP4.3

This repo contains all the collateral and material for running the DevEx Workshops for OCP4.3

Link to a single sheet (double sided) document that describes the DevEx workshop and can be used as a flyer at events is available at the following URL:

https://docs.google.com/document/d/1gNINFJ599AqQM19082ac2n7VrIMrSURwXFLiDTIoB54/edit?usp=sharing

Link to DevEx promotional slides for use with sellers and customers is available at the following URL:

https://docs.google.com/presentation/d/1Pq0vo95mGE9ZFppdLr44nVf7S-5-hqTzC0qFpo2HpG8/edit?usp=sharing

**To run a workshop request an OpenShift4.3 Workshop via RHPDS. If you do not wish to use RHPDS create a 4.3 Cluster and ensure you create users for the attendees (userx with password openshift).**

The workshop has been split into two components, a 'basics' and an 'advanced' module. These are built separately as documents and can be given independently - the course takes about 6-8 hours to do them both depending on attendee level.

Generate the documentations for the attendees using Maven - clone this repo, go to the documentation directories (documentationbasic and documentationadvanced) and run:

mvn clean package -DfacilitatorName='YOUR NAME' -DfacilitatorEmail='YOUR EMAIL' -DfacilitatorTitle='YOUR JOB TITLE' -DwebConsoleUrl='CONSOLE URL FOR OCP4'

It is worth creating short URLs for the following URLs to be provided to the attendees:

1: The Console URL (the console login point for the attendees)

2: The API Endpoint URL (the URL used to make direct calls, primarily for the Tekton chapter)

For the advanced course there are some Operator pre-requisites that are installed using an Ansible playbook - please check the documentation and the playbooks directory of the repo for details

When running a remote session it is highly advised to have two additional applications running on the Cluster for communications with the attendees. These are as follows, with instructions on how to set them up:

1: Etherpad

Log on as a Cluster Admin

Create a project using 'oc new-project etherpad'

oc new-app --template=postgresql-persistent --param POSTGRESQL_USER=ether --param POSTGRESQL_PASSWORD=ether --param POSTGRESQL_DATABASE=etherpad --param POSTGRESQL_VERSION=10 --param VOLUME_CAPACITY=10Gi --labels=app=etherpad_db

Wait until the PostGres pod is running (watch for the deployment to finish)

oc new-app -f https://raw.githubusercontent.com/wkulhanek/docker-openshift-etherpad/master/etherpad-template.yaml -p DB_TYPE=postgres -p DB_HOST=postgresql -p DB_PORT=5432 -p DB_DATABASE=etherpad -p DB_USER=ether -p DB_PASS=ether -p ETHERPAD_IMAGE=quay.io/wkulhanek/etherpad:1.8.4 -p ADMIN_PASSWORD=secret

Once the etherpad Pod is running, switch back to the UI - go to the project etherpad, Networking/Routes

Click on the Route

Click on 'New Pad'

Remove all text from the Pad

Copy and paste the text below into the pad:

-------------------------- (not this line)  
Welcome to OpenShift DevEx Labs

Please choose an untaken username from below and type your name next to it to claim it. Once you have a username you can use the labs.

The labs are reachable at INSERTCLUSTERURL

Documentation for the labs is at INSERTDOCSHERE (add an extra line for the Innovation docs if needed)

Also please join the Rocketchat at INSERTROCKETCHATURL - you will have to create an account. Once there join the #support channel where you can raise any issues or problems with the labs.

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

[Red Hat]  
user80  
user81  
user82  
user83  
user84  

--------------------- (not this line)

Change the cluster URL in the Pad to the Cluster URL you are running on.

2: Rocketchat

Log onto the Cluster as a Cluster Admin user

In the Administration view go to 'Projects'

Hit Create Project and create a project called 'chat'

Click on 'Operators/OperatorHub'

In the search box type 'rocket'

Click on the Rocketchat Operator

Click 'Install'

In Installation Mode chamge the target to 'A specific namespace on the cluster'

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

3: Create a Short URL for the landing page on Rocketchat

Go back to the UI for the Cluster (as Cluster Admin)

In Projects choose chat

Go to Networking/Routes

Hit 'Create Route'

Set the name to securechat

Set the service to 'rocketchat-quickstart-rocketchat'

Set the target port to 3000

Click the 'Secure Route'

Set TLS Termination to 'Edge'

Scroll down and hit create

Copy the 'Location' field (with the https://whatever)

In the short URL generator of your choice use this to get a short URL - this is the landing page for the attendees.
