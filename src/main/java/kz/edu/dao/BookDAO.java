package kz.edu.dao;

import com.google.gson.Gson;
import kz.edu.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class BookDAO
{
    private SessionFactory sessionFactory;
    Session session;
    List<Book> booksList;
    @Autowired
    public BookDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    public List<Book> getBookList()
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
            Root<Book> root = criteria.from(Book.class);
            criteria.select(root);
            Query<Book> query = session.createQuery(criteria);
            booksList = query.getResultList();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return booksList;
    }
    public String getBook(String name)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        String json;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> q1 = builder.createQuery(Book.class);
            Root<Book> root = q1.from(Book.class);

            Predicate predicateBook = builder.equal(root.get("name"), name);
            Predicate predicateMovie = builder.equal(root.get("movieName"), name);
            Predicate predicateSearch = builder.or(predicateBook, predicateMovie);
            Book book = session.createQuery(q1.where(predicateSearch)).getSingleResult();

            session.getTransaction().commit();
            json = new Gson().toJson(book);
        }
        finally
        {
            session.close();
        }
        return json;
    }
    public Book getBook(long id)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Book book;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> q1 = builder.createQuery(Book.class);
            Root<Book> root = q1.from(Book.class);

            Predicate predicateBook = builder.equal(root.get("id"), id);
            book = session.createQuery(q1.where(predicateBook)).getSingleResult();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return book;
    }
    public void addBook(Book book)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(book);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
    public void updateBook(Book book)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(book);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
    public void deleteBook(long bookId)
    {
        System.out.println("delete " + bookId);
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Book book = session.find(Book.class, bookId);
            session.remove(book);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
}