package io.quarkus.qe;

import junit.framework.TestCase;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.NginxContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    public void testName() {
        GenericContainer container = new GenericContainer<>("postgres:9.6.12");
        container.start();
        container.close();
    }

    public void testFullName() {
        GenericContainer container = new GenericContainer<>("docker.io/postgres:9.6.10");
        container.start();
        container.close();
    }

    public void testNginx() {
        DockerImageName imageName = DockerImageName.parse("nginx").withTag("latest");
        NginxContainer container = new NginxContainer(imageName);
        container.start();
        container.close();
    }
}
