package http.filesystem;

import http.exceptions.TemplateNotFoundException;
import http.utils.Logger;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Stream;

public class RetrieveTemplate {
    private final Logger logger = new Logger(org.apache.log4j.Logger.getLogger(RetrieveTemplate.class));
    private final String templateName;

    public RetrieveTemplate(String templateName) {
        this.templateName = templateName;
    }

    public byte[] inBytes() {
        Optional<InputStream> streamedResource = getStreamedResource();
        try {
            if (streamedResource.isPresent()) {
                return IOUtils.toByteArray(streamedResource.get());
            }
        } catch (IOException e) {
            logger.logError(e);
        }
        throw new TemplateNotFoundException("Template does not exist");
    }

    private Optional<InputStream> getStreamedResource() {
        Stream<InputStream> resourceStream = Stream.of(getClass().getResourceAsStream(templateName));
        return resourceStream.filter(stream -> stream != null).findFirst();
    }
}
