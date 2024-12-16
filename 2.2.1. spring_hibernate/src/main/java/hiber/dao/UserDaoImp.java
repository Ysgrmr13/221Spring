package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteAllUsers() {
        List<User> users = listUsers();
        for (User user : users) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Override
    public User findOwner(String car_name, String car_series) { // Измените тип параметра car_series на String
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "select u from User u join u.car c where c.model = :model and c.series = :series", User.class);
        query.setParameter("model", car_name);
        query.setParameter("series", car_series); // Измените тип параметра car_series на String
        return query.getSingleResult();
    }
}