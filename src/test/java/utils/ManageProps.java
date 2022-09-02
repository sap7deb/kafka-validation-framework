package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ManageProps {

    public static TestProps loadProperties() throws IOException {
        String filePath = "src/test/resources/" + "test.yml";
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(filePath), TestProps.class);
    }
}
