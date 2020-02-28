buildah --name builder from registry.access.redhat.com/ubi7/ubi
buildah run builder -- /usr/bin/yum install httpd -y
buildah run builder -- /usr/bin/yum clean all
buildah copy builder src/index.html /var/www/html/index.html
buildah config --port 80 builder
buildah config --cmd "/usr/sbin/httpd -DFOREGROUND" builder
buildah commit builder quay.io/marrober/http-server
buildah images
buildah push quay.io/marrober/http-server:latest