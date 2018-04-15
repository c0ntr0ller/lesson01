package spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ConfigClass {
    @Value("${xml.filename:'data/RU-NVS.osm.bz2'}")
    private String xmlFileName;

    @Value("${read.count:1000}")
    private int readCount;

    public ConfigClass() {
    }

    public String getFileName() {
        return xmlFileName;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }
}
