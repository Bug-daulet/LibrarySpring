package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "UserEntity")
@Table(name = "users")
public class User implements Serializable
{
    private long user_id;
    private String email;
    private String password;
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public long getId()
    {
        return this.user_id;
    }
    public void setId(long user_id)
    {
        this.user_id = user_id;
    }

    @Column(name = "email")
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {this.email = email;}

    @Column(name = "password")
    public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    public Role getRole()
    {
        return role;
    }
    public void setRole(Role role)
    {
        this.role = role;
    }

}