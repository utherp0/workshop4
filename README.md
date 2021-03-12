# Red Hat UKI DSA DevEx (Developer Experience) OpenShift Workshop4
Developer Experience Workshops for OCP 4.6

This repo contains all the collateral and material for running the DevEx Workshops for OCP 4.6

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
Introduction to Quarkus
Introduction to Kogito
Camel-k on OpenShift  
Serverless Eventing with Camel-k on OpenShift  
Pipelines with Tekton on OpenShift  
Service Mesh on OpenShift  
Quay, Buildah and Podman
OpenShift Serverless Serving

To run a workshop request an OpenShift 4.6 Workshop via RHPDS. If you do not wish to use RHPDS, create a 4.6 Cluster and ensure you create users for the attendees (`userX` with password `openshift`).

This is a dynamic version of the Workshop and allows creators to define and setup workshops with any combination of the labs provided. 

To build the documentation for the workshop you need to do the following:

1. Navigate to the `cluster` file in the setup/playbook/group_vars/all directory
2. Edit the variables in setup/playbooks/group_vars/all directory
   * `cluster` - insert cluster URLs and API token
   * `manifest` - defines the labs that will be included in the workshop
   * `links` - the hyperlinks that will appear at the bottom of the username distribution app
   * `extra_vars` - password for the username distribution admin interface, password for attendees to be given a username, and number of usernames to create in the username distribution app
2. Change to the setup/playbooks directory
3. Run the Deploy_Docs.yml playbook - `ansible-playbook Deploy_Docs.yml`
4. The last task in the playbook will give you the starting point URL

If you would like to contribute labs there is a guide on how to write them and what to be aware of at https://docs.google.com/document/d/1DwSFGP1SO-1MOfEck6AGDjjU4d2k8YGYmpcMtxhaEZo/edit

All of the Google docs are available in PDF format in the docs/ directory of the repo for those who don't have access to the originals.