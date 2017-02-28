package judge.Controller;

import judge.Service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/judge")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void insertStudent(@RequestBody String code) throws IOException {
        this.judgeService.compile(code);
    }

}

