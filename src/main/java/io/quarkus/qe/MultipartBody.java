package io.quarkus.qe;

import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.File;
import java.util.List;

public class MultipartBody {

    @RestForm("text")
    @PartType(MediaType.TEXT_PLAIN)
    public String text;

    @RestForm("image")
    @PartType("image/png")
    public File image;

    @RestForm("data")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File data;

    @RestForm
    public File plainTextFile;

    @RestForm(FileUpload.ALL)
    public List<FileUpload> allFiles;

}
