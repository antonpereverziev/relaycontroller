package home.automation;

import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.IOException;

//@Configuration
//@EnableFilesystemStores
//@Import("org.springframework.content.rest.config.RestConfiguration.class")
public class ContentConfig {

    /*@Bean
    FileSystemResourceLoader fileSystemResourceLoader() throws IOException {
        return new FileSystemResourceLoader(new File("/path/to/uploaded/files").getAbsolutePath());
    }*/
}