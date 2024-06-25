FROM quay.io/strimzi/kafka:0.41.0-kafka-3.7.0

USER root
RUN mkdir /opt/kafka/logs && chown -R kafka /opt/kafka/logs
USER kafka

COPY --chmod=0500 --chown=kafka <<EOF /tmp/kraft.sh
#!/bin/bash
set -euv
/opt/kafka/bin/kafka-storage.sh format -t rjuUEA-IRxKJCcfKQovnYg -c config/kraft/server.properties
/opt/kafka/bin/kafka-server-start.sh config/kraft/server.properties
EOF

COPY --chmod=0500 --chown=kafka <<EOF /tmp/classic.sh
#!/bin/bash
set -euv
bin/zookeeper-server-start.sh config/zookeeper.properties & bin/kafka-server-start.sh config/server.properties  --override listeners=PLAINTEXT://0.0.0.0:9092 --override advertised.listeners=PLAINTEXT://localhost:9092
EOF

ENTRYPOINT ["/bin/bash"]
