package kz.edu.dao;

import com.google.gson.Gson;
import kz.edu.model.Book;
import kz.edu.model.Borrow;
import kz.edu.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<Borrow> borrowsList;

    @Autowired
    public BorrowDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Borrow> getBorrowList()
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Borrow> criteria = builder.createQuery(Borrow.class);
            Root<Borrow> root = criteria.from(Borrow.class);
            criteria.select(root);
            Query<Borrow> query = session.createQuery(criteria);
            borrowsList = query.getResultList();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return borrowsList;
    }

    public List<Borrow> getBorrow(User user)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Borrow> userBorrows;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Borrow> q1 = builder.createQuery(Borrow.class);
            Root<Borrow> root = q1.from(Borrow.class);

            Predicate predicateBorrow = builder.equal(root.get("user"), user);
            userBorrows = session.createQuery(q1.where(predicateBorrow)).getResultList();

            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return userBorrows;
    }

    public long getBorrow(Book book)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Borrow> bookBorrows;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Borrow> q1 = builder.createQuery(Borrow.class);
            Root<Borrow> root = q1.from(Borrow.class);

            Predicate predicateBorrow = builder.equal(root.get("book"), book);
            bookBorrows = session.createQuery(q1.where(predicateBorrow)).getResultList();

            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return bookBorrows.size();
    }

    public Borrow getBorrow(long id)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Borrow borrow;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Borrow> q1 = builder.createQuery(Borrow.class);
            Root<Borrow> root = q1.from(Borrow.class);

            Predicate predicateBorrow = builder.equal(root.get("id"), id);
            borrow = session.createQuery(q1.where(predicateBorrow)).getSingleResult();
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
        return borrow;
    }

    public void addBorrow(Borrow borrow)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            Book book = borrow.getBook();
            if (borrow.getUser() == null || book == null || book.getCount() == getBorrow(book)) return;
            session.persist(borrow);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void updateBorrow(Borrow borrow)
    {
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(borrow);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void deleteBorrow(long id)
    {
        System.out.println("delete " + id);
        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Borrow borrow = session.find(Borrow.class, id);
            borrow.setUser(null);
            borrow.setBook(null);
            session.remove(borrow);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
}
