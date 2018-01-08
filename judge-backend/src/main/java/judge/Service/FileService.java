package judge.Service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(FileService.class);

    public void removeFile(String filename) {
        try {
            File file = new File(filename);
            if (file.delete()) {
                logger.info(file.getName() + " is deleted!");
            } else {
                logger.warn("Delete operation is failed for " + filename);
            }
        } catch (Exception e){
            logger.error(e);
        }
    }
}
