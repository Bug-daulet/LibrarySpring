package kz.edu.dao;

import com.google.gson.Gson;
import kz.edu.model.Role;
import kz.edu.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class StudentDAO {
    private SessionFactory sessionFactory;
    Session session;
    List<User> studentsList;
    @Autowired
    public StudentDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    public List<User> getStudentList()
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            Query<User> query=session.createQuery("select U from UserEntity  U where U.role.id=:studentRole");
            query.setParameter("studentRole", (long)2);
            studentsList = query.getResultList();

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<User> criteria = builder.createQuery(User.class);
//            EntityManager entityManager =
//            Metamodel metamodel =
//            Root<User> root = criteria.from(User.class);
//            criteria.select(root);
//            Query<User> query = session.createQuery(criteria);
//            studentsList = query.getResultList();
//            session.getTransaction().commit();
        } catch (NoResultException noResultException) {

        }
        finally
        {
            session.close();
        }
        return studentsList;
    }
    public String getStudent(String email)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        String json;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = builder.createQuery(User.class);
            Root<User> root = q1.from(User.class);

            Predicate predicateUser = builder.equal(root.get("email"), email);
            Predicate predicateStudent = builder.equal(root.get("role").get("name"), "ROLE_USER");
            Predicate predicateSearch = builder.and(predicateUser, predicateStudent);
            User user = session.createQuery(q1.where(predicateSearch)).getSingleResult();

            session.getTransaction().commit();
            json = new Gson().toJson(user);
        } catch (NoResultException noResultException) {
            return null;
        }
        finally
        {
            session.close();
        }
        return json;
    }
    public User getStudent(long id)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try
        {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = builder.createQuery(User.class);
            Root<User> root = q1.from(User.class);

            Predicate predicateUser = builder.equal(root.get("id"), id);
            Predicate predicateStudent = builder.equal(root.get("role").get("name"), "ROLE_USER");
            Predicate predicateSearch = builder.and(predicateUser, predicateStudent);
            user = session.createQuery(q1.where(predicateSearch)).getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException noResultException) {
            return null;
        }
        finally
        {
            session.close();
        }
        return user;
    }
    public void addStudent(User user)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<Role> q1 = builder1.createQuery(Role.class);
            Root<Role> root1 = q1.from(Role.class);

            Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_USER");
            Role role = session.createQuery(q1.where(predicateRole)).getSingleResult();
            user.setRole(role);

            session.persist(user);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
    public void updateStudent(User user)
    {
        session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<Role> q1 = builder1.createQuery(Role.class);
            Root<Role> root1 = q1.from(Role.class);

            Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_USER");
            Role role = session.createQuery(q1.where(predicateRole)).getSingleResult();
            user.setRole(role);

            session.merge(user);
            session.getTransaction().commit();
        } catch (NoResultException noResultException) {

        }
        finally
        {
            session.close();
        }
    }
    public void deleteStudent(long userId)
    {
        System.out.println("delete " + userId);
        session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            User user = session.find(User.class, userId);
            user.setRole(null);
            session.remove(user);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
}
