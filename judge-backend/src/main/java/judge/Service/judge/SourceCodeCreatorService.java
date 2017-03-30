package judge.Service.judge;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
class SourceCodeCreatorService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SourceCodeCreatorService.class);

    String createSourceCodeFile(String code) {
        List<String> lines = Collections.singletonList(code);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        /*
            Math.random() will be replaced by user ID.
            It is used to avoid overwriting generated source files during parallel tests.
         */
        String fileName = "source_code" + timestamp + Math.random()  + ".c";
        Path file = Paths.get(fileName);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Exception happened while generating the source code file.", e);
        }
        return fileName;
    }
}
