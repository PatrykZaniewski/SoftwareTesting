import Entity.Driver;
import Entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Config.*;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class App {

    public static void main(String[] args){
        SessionFactory sessionFactory = new SessionConfig().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Team team = new Team();
        team.setName("Williams F1 Taczka Team");
        team.setCups(0);
        team.setYearOfCreation(1977);

        Driver driver = new Driver();
        driver.setName("Dzord");
        driver.setSurname("Gekon Russel");
        driver.setPoints(0);
        driver.setTeam(team);

        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(driver);
        team.setDrivers(drivers);

        session.persist(team);
        transaction.commit();
        session.close();
    }
}
