project=$(oc project -q)

echo $project

host_url=$(oc get route adventure-holiday -o jsonpath='{.spec.host}'  | cut -d"." -f2-)

sed -i "s/URL/$project.$host_url/" adventure-route.yaml
sed -i "s/URL/$project.$host_url/" cruise-route.yaml
sed -i "s/URL/$project.$host_url/" package-route.yaml
sed -i "s/URL/$project.$host_url/" short-break-route.yaml


