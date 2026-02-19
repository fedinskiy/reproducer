package io.quarkus.ts.mcp.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkiverse.mcp.server.Resource;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;

public class FileServer {
    private static final Logger LOG = Logger.getLogger(FileServer.class);

    String folder;

    public FileServer(@ConfigProperty(name = "working.folder", defaultValue = "target") String folder) {
        this.folder = folder;
    }

    @Tool(name = "filereader", description = "Read a file")
    String readFileContent(@ToolArg(description = "path to the file") String file) {
        Path allowed = Path.of(folder).toAbsolutePath();
        Path path = Path.of(file).toAbsolutePath();
        if (!path.startsWith(allowed)) {
            throw new IllegalArgumentException(String.format("The file %s is not inside allowed folder %s", path, allowed));
        }
        try (Stream<String> lines = Files.lines(path)) {
            LOG.info("Reading file: " + path);
            return lines.collect(StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append).toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + path + ": " + e.getMessage());
        }
    }

    @Resource(name = "fileresource", description = "Read a file", uri = "file:///hello")
    String readHelloFile() {
        Path path = Path.of("robot-readable.txt").toAbsolutePath();
        try (Stream<String> lines = Files.lines(path)) {
            LOG.info("Reading file: " + path);
            return lines.collect(StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append).toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + path + ": " + e.getMessage());
        }
    }
}
