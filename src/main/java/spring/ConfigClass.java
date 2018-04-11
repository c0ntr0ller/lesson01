package spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ConfigClass {
    private String fileName;

    public String getFileName() {
        return fileName;
    }
}
