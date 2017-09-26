package judge.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static judge.Utils.STUDENTS_TEMPLATE_1_NAME;
import static judge.Utils.TEMPLATES_DIR_NAME;

@Entity
@Table(name = "Problem")
public class Problem
{

    private Integer id;
    private User author;
    private String title;
    private String description;
    private String structures;
    private String solution;
    private String signature;
    private String template;


    public Problem()
    {
        Path students_file = Paths.get(TEMPLATES_DIR_NAME + STUDENTS_TEMPLATE_1_NAME);
        try {
            List<String> lines = Files.readAllLines(students_file);
            signature = String.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    @ManyToOne
    @JsonBackReference
    public User getAuthor()
    {
        return author;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    @Column(length = 2048)
    public String getStructures() {
        return structures;
    }

    @Column(length = 2048)
    public String getSolution() {
        return solution;
    }

    public String getSignature() {
        return signature;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setStructures(String structures) {
        this.structures = structures;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
