package judge.Entity;

import javax.persistence.*;

@Entity
public class Problem
{

    private Integer id;
    private Teacher author;
    private String title;
    private String description;
    private String path;

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

    public String getPath()
    {
        return path;
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

    public void setPath(String path)
    {
        this.path = path;
    }
}
