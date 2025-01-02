package database.repositories;

import database.Users;
import database.interfaces.UsersDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class UsersDAOImpl implements UsersDAO {
    private final SessionFactory sessionFactory;
    HibernateUtil hibernateUtil;
    public UsersDAOImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    public UsersDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Users> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Users", Users.class).list();
        }
    }

    @Override
    public Users findUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Users.class, userId);
        }
    }

    @Override
    public void saveUser(Users user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateUser(Users user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteUser(Users user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Users> getUsersByRole(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Users> query = session.createQuery(
                    "FROM Users u WHERE u.role.name = :roleName", Users.class);
            query.setParameter("roleName", roleName);
            return query.list();
        }
    }
    @Override
    public Users findByEmail(String email) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Users WHERE email = :email";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
