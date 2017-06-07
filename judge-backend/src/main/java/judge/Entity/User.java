package judge.Entity;

import javax.persistence.*;
import java.math.BigInteger;

@MappedSuperclass
public class User
{

    private BigInteger id;
    private String username;
    private String password;
    private String email;

    public User()
    {
    }

    @Id
    public BigInteger getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setId(BigInteger id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
