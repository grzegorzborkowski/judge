package judge.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Problem")
public class Problem
{

    private Integer id;
    private Teacher author;
    private String title;
    private String description;
    private String signature;
    private String template;

    public Problem()
    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    @ManyToOne
    public Teacher getAuthor()
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

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setAuthor(Teacher author)
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

    public String getSignature() {
        return signature;
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
