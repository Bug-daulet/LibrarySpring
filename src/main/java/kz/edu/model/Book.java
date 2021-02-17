package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BookEntity")
@Table(name = "books")
public class Book implements Serializable
{
    private long id;
    private String name;
    private String author;
    private String movieName;
    private String bookURL;
    private String movieURL;
    private String imageURL;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_Id")
    public long getId()
    {
        return this.id;
    }
    public void setId(long id)
    {
        this.id = id;
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

    @Column(name = "book_movie")
    public String getMovieName()
    {
        return this.movieName;
    }
    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    @Column(name = "book_author")
    public String getAuthor()
    {
        return this.author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    @Column(name = "book_bookreads")
    public String getBookURL() { return this.bookURL; }
    public void setBookURL(String bookURL) { this.bookURL = bookURL; }

    @Column(name = "book_imdb")
    public String getMovieURL() { return this.movieURL; }
    public void setMovieURL(String movieURL) { this.movieURL = movieURL; }

    @Column(name = "book_poster")
    public String getImageURL() { return this.imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
