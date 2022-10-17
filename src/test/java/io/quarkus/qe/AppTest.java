package io.quarkus.qe;

import junit.framework.TestCase;
import org.testcontainers.containers.NginxContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    public void testName() {
        DockerImageName imageName = DockerImageName.parse("localstack/localstack").withTag("0.12.17");
        LocalStackContainer container = new LocalStackContainer(imageName).withServices(LocalStackContainer.Service.DYNAMODB);
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
