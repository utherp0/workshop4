# Parameter check
#
# To customise the tekton yaml correctly you need to provide:
# $1 - user *number*
# $2 - The cluster *API* address which looks like:
#   https://api.cluster-london2-16fb.london2-16fb.example.opentlc.com:6443


if [ -z "$1" ]; then
  echo "Usage: ./setup.sh USERNAME APIURL";
  exit 0
fi

if [ -z "$2" ]; then
  echo "Usage: ./setup.sh USERNAME APIURL";
  exit 0
fi

curl https://raw.githubusercontent.com/utherp0/workshop4/master/attendee/tekton/watch.sh > watch.sh

sed -i 's#USER#'"$1"'#g' tasktest1.yaml

sed -i 's#SERVER#'"$2"'#g' tasktest1.yaml
