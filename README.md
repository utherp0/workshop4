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

To run a workshop request an OpenShift4.3 Workshop via RHPDS. If you do not wish to use RHPDS create a 4.3 Cluster and ensure you create users for the attendees (userx with password openshift).

This is a dynamic version of the Workshop and allows creators to define and setup workshops with any combination of the labs provided. This is done using the
DocBuilder application provided in source and release format (see releases for the latest build).

High level design document for the DocBuilder including details on how to use it is available at https://docs.google.com/document/d/1AgcLZ5BDKxNt8eYZ_cqanPHzmubescMjCeQUdtunAcw/edit?ts=5ed63004

To build the documentation for the workshop you need to do the following:

1: Create an output directory for the documentation - this will be used to deliver the HTML, Images and PDF for the workshop  
2: Produce a manifest. This manifest defines the workshop. Examples are provided in the labs/manifest directory. The manifest contains configuration information for the course, including Facilitor name, email and title, Cluster URL (the root console address for the OpenShift Cluster) and the title required for the documentation  
3: If running this yourself you need Maven, the Asciidoc Maven libraries and JAVA (8+) installed on your machine  
4: Run the release DocBuilder using - "java -jar releases/(which version you want).jar" - the DocBuilder will then tell you what parameters you need to provide. These are workingDirectory (will be created, scaffolded and torn down), manifestFile (it is suggested you copy one of the provided manifests and edit it), gitCloneDirectory (the root directory where you have cloned this repo), outputDirectory (needs to exist in advance)  
5: Distribute the documentation appropriately  

If you would like to contribute labs there is a guide on how to write them and what to be aware of at https://docs.google.com/document/d/1DwSFGP1SO-1MOfEck6AGDjjU4d2k8YGYmpcMtxhaEZo/edit

All of the Google docs are available in PDF format in the docs/ directory of the repo for those who don't have access to the originals.

------------------------------------------------------------

When running a remote session it is highly advised to have two additional applications running on the Cluster for communications with the attendees. These are as follows, with instructions on how to set them up:

1: Etherpad

Log on as a Cluster Admin

Create a project using 'oc new-project etherpad'

oc new-app mysql-persistent --param MYSQL_USER=ether --param MYSQL_PASSWORD=ether --param MYSQL_DATABASE=ether --param VOLUME_CAPACITY=4Gi --param MYSQL_VERSION=5.7

Wait until the MySQL pod is running (watch for the deployment to finish)

oc new-app -f https://raw.githubusercontent.com/wkulhanek/docker-openshift-etherpad/master/etherpad-template.yaml -p DB_USER=ether -p DB_PASS=ether -p DB_DBID=ether -p DB_PORT=3306 -p DB_HOST=mysql -p ADMIN_PASSWORD=secret

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

In the short URL generator of your choice use this to get a short URL - this is the landing page for the attendees.
