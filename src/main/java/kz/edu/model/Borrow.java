package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "BorrowEntity")
@Table(name = "borrows")
public class Borrow implements Serializable {
    private long borrow_id;
    private User user;
    private Book book;
    private Date borrow_date;
    private Date return_date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId()
    {
        return this.borrow_id;
    }
    public void setId(long borrow_id)
    {
        this.borrow_id = borrow_id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn")
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Column(name = "borrow_date")
    public Date getBorrow_date() {
        return borrow_date;
    }
    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    @Column(name = "return_date")
    public Date getReturn_date() {
        return return_date;
    }
    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }
}
