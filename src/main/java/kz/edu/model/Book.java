package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BookEntity")
@Table(name = "books")
public class Book implements Serializable
{
    private long isbn;
    private String name;
    private int count;
    private int availableCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn")
    public long getId()
    {
        return this.isbn;
    }
    public void setId(long isbn)
    {
        this.isbn = isbn;
    }

    @Column(name = "book_name")
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "count")
    public int getCount()
    {
        return this.count;
    }
    public void setCount(int count)
    {
        this.count = count;
    }

    @Column(name = "availableCount")
    public int getAvailableCount()
    {
        return this.availableCount;
    }
    public void setAvailableCount(int availableCount)
    {
        this.availableCount = availableCount;
    }
}
