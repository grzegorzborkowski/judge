package judge.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category {

    private Integer id;
    private String name;

    public Category (String name) {
        this.name = name;
    }

    public Category() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
