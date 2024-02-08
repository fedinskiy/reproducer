
project=${1}

if [[ -z $project ]]; then
  echo "Project name is empty!"
  exit 1
fi

set -euo pipefail
oc new-project $project
mvn -Popenshift package -Dquarkus.container-image.group=$project -Dquarkus.platform.version=3.6.4

#mvn -e -Dprofile.id=jvm -Popenshift package \
#-Dquarkus.kubernetes.deploy=true -Dquarkus.openshift.route.expose=true \
#-Dquarkus.build.skip=false -Dquarkus.application.name=app \
#-Dquarkus.kubernetes-client.trust-certs=true \
#-Dquarkus.kubernetes-client.namespace=$project -Dquarkus.container-image.group=$project \
#-Dquarkus.kubernetes.deployment-target=openshift

oc expose svc/app --port=9000 --name=app-management

attempt=0
while [[ ! $(curl --fail http://app-management-${project}.apps.ocp4-14.dynamic.quarkus) && attempt -le 5 ]] ; do
    sleep 5;
    attempt=$((attempt+1))
done

oc get events

oc delete project $project
