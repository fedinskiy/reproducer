mvn clean compile verify \
	-Dmaven.repo.local=$REPO \
	-Dquarkus-plugin.version=3.8.3.temporary-redhat-00008 \
	-Dquarkus.platform.version=3.8.3.temporary-redhat-00008 \
	-Dquarkus.platform.group-id=com.redhat.quarkus.platform
