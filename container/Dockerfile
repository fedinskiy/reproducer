FROM ubuntu:23.10

WORKDIR /opt
ADD theapp-runner /opt
ADD --chmod 777 *pem /opt
CMD /opt/theapp-runner
