import Entity.Driver;
import Entity.DriverDetails;
import Entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import Config.*;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class App {

    public static void main(String[] args){

        //CREATE
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

        Driver driver2 = new Driver();
        driver2.setName("Romek");
        driver2.setSurname("Grozaaaa");
        driver2.setPoints(0);
        driver2.setTeam(team);

        DriverDetails driverDetails = new DriverDetails();
        driverDetails.setAge(34);
        driverDetails.setCountry("Gekonownia");
        driverDetails.setStartingNumber(34);
        driverDetails.setNumberOfStarts(33);
        driverDetails.setDriver(driver);
        driver.setDriverDet(driverDetails);

        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(driver);
        drivers.add(driver2);
        team.setDrivers(drivers);

        session.persist(team);
        transaction.commit();
        session.close();

        //READ
        session = sessionFactory.openSession();
        session.beginTransaction();
        team = session.load(Team.class, 1);
        System.out.println(team.getDrivers().get(0).getName());

    }
}
