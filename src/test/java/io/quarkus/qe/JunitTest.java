package io.quarkus.qe;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class JunitTest extends TestCase {

    @Test
    public void testNamed() {
        DockerImageName imageName = DockerImageName.parse("docker.io/library/mariadb:10.6").withTag("latest");
        GenericContainer container = new GenericContainer<>(imageName);
        container.start();
    }
}
