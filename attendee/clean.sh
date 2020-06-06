echo "Removing components from current namespace"

for deployment in $(oc get deployments -o jsonpath='{.items[*].metadata.name}');
do
  oc delete deployment $deployment;
done

for deploymentconfig in $(oc get dc -o jsonpath='{.items[*].metadata.name}');
do
  oc delete dc $deploymentconfig;
done

for buildconfig in $(oc get bc -o jsonpath='{.items[*].metadata.name}');
do
  oc delete bc $buildconfig;
done

for service in $(oc get svc -o jsonpath='{.items[*].metadata.name}');
do
  oc delete svc $service;
done

for route in $(oc get route -o jsonpath='{.items[*].metadata.name}');
do
  oc delete route $route;
done

for is in $(oc get is -o jsonpath='{.items[*].metadata.name}');
do
  oc delete is $is;
done

for secret in $(oc get secrets -o jsonpath='{.items[*].metadata.name}');
do 
  if [[ $secret == *"webhook"* ]]; then 
    oc delete secret $secret; 
  fi; 
done
