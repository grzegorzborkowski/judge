package judge.Service.judge;

import judge.Component.JudgeResult;
import judge.Dao.ProblemDao;
import judge.Dao.SubmissionDao;
import judge.Entity.Problem;
import judge.Entity.User;
import judge.Entity.Submission;
import judge.Service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static judge.Utils.PROCESSING_ERROR_CODE;

@Service
@Configurable
@EnableTransactionManagement
public class JudgeService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(JudgeService.class);

    @Autowired
    SourceCodeService sourceCodeService;
    @Autowired
    FileService fileService;
    @Autowired
    AgentService agentService;
    @Autowired
    SubmissionDao submissionDao;
    @Autowired
    ProblemDao problemDao;
    /**
     * Takes user's input, produces a source code file, pass it to the external runner,
     * produces a Submission.
     * @param code  user's input (now: program, target: function)
     * @returns Submission object based on examination results
     */
    public Submission compileAndRun(String code, User author, Integer problemID) {

        Submission submission = new Submission();
        logger.info("Processing new submission.");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Problem problem = problemDao.findById(problemID);

        submission.setDate(date);
        submission.setCode(code);
        submission.setAuthor(author);
        submission.setProblem(problem);

        String sourceCodeFilename = sourceCodeService.createSourceCodeFile(code, problem);

        try{
            JudgeResult externalExaminationResult = agentService.uploadFileToExamine(sourceCodeFilename);
            submission.fillWithResult(externalExaminationResult);
        } catch (Exception e) {
            submission.setCompilationCode(PROCESSING_ERROR_CODE);
            submission.setRunCode(PROCESSING_ERROR_CODE);
            //TODO: DELETE this line!!! Left only for testing
            this.submissionDao.save(submission);
            logger.error("Error while passing examination request to the external runner (server may be unreachable)." +
                    " Submission can't be processed.");
            logger.error(e.toString());
        }

        fileService.removeFile(sourceCodeFilename);

        this.submissionDao.save(submission);
        logger.info("Processing of the submission has finished.");
        return submission;
    }

    public void setSourceCodeService(SourceCodeService sourceCodeService) {
        this.sourceCodeService = sourceCodeService;
    }

    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
    }
}
