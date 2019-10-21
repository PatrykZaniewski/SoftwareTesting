import Config.SessionConfig;
import Entity.Driver;
import Entity.DriverDetails;
import Entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        sessionFactory = new SessionConfig().getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @Test
    public void testUpdateDriver() {
        //GIVEN
        Team testTeam = new Team();
        testTeam.setName("SPR Malysz Testing Drive");
        testTeam.setCups(68);
        testTeam.setYearOfCreation(2019);

        Driver testDriver = new Driver();
        testDriver.setName("Dani");
        testDriver.setSurname("Malysz");
        testDriver.setPoints(904);
        testDriver.setTeam(testTeam);

        DriverDetails testDriverDetails = new DriverDetails();
        testDriverDetails.setAge(21);
        testDriverDetails.setCountry("Polandia");
        testDriverDetails.setStartingNumber(9);
        testDriverDetails.setNumberOfStarts(1);
        testDriverDetails.setDriver(testDriver);
        testDriver.setDriverDet(testDriverDetails);

        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(testDriver);
        testTeam.setDrivers(drivers);

        session.persist(testTeam);
        transaction.commit();
        session.close();

        //WHEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class,3);

        String newName = "DaniUpdated";
        String newSurname = "MalyszUpdated";
        int newPoints = 99;

        driver.setName(newName);
        driver.setSurname(newSurname);
        driver.setPoints(newPoints);

        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        driver = session.get(Driver.class,3);
        assertEquals(driver.getName(), newName);
        assertEquals(driver.getSurname(), newSurname);
        assertEquals(driver.getPoints(), newPoints);

        session.close();
    }

    @Test
    public void testUpdateDriverDetails() {
        //GIVEN
        Team testTeam = new Team();
        testTeam.setName("UKT FONDA");
        testTeam.setCups(68);
        testTeam.setYearOfCreation(2019);

        Driver testDriver = new Driver();
        testDriver.setName("Mark");
        testDriver.setSurname("Febber");
        testDriver.setPoints(904);
        testDriver.setTeam(testTeam);

        DriverDetails testDriverDetails = new DriverDetails();
        testDriverDetails.setAge(21);
        testDriverDetails.setCountry("Szybkolandia");
        testDriverDetails.setStartingNumber(9);
        testDriverDetails.setNumberOfStarts(1);
        testDriverDetails.setDriver(testDriver);
        testDriver.setDriverDet(testDriverDetails);

        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(testDriver);
        testTeam.setDrivers(drivers);

        session.persist(testTeam);
        transaction.commit();
        session.close();

        //WHEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        DriverDetails driverDetails = session.get(DriverDetails.class,2);

        String newCountry = "SzybkolandiaUpdated";
        int newAge = 22;
        int newNumberOfStarts = 77;
        int newStartingNumber = 77;

        driverDetails.setCountry(newCountry);
        driverDetails.setAge(newAge);
        driverDetails.setNumberOfStarts(newNumberOfStarts);
        driverDetails.setStartingNumber(newStartingNumber);

        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        driverDetails = session.get(DriverDetails.class,2);
        assertEquals(driverDetails.getCountry(), newCountry);
        assertEquals(driverDetails.getAge(), newAge);
        assertEquals(driverDetails.getNumberOfStarts(), newNumberOfStarts);
        assertEquals(driverDetails.getStartingNumber(), newStartingNumber);

        session.close();
    }

    @Test
    public void testUpdateTeam() {
        //GIVEN
        Team testTeam = new Team();
        testTeam.setName("Fewawwi");
        testTeam.setCups(68);
        testTeam.setYearOfCreation(2018);
        ArrayList<Driver> drivers = new ArrayList<>();
        testTeam.setDrivers(drivers);

        session.persist(testTeam);
        transaction.commit();
        session.close();

        //WHEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team team = session.get(Team.class,3);

        String newName = "FewawwiUpdated";
        int newCups = 99;
        int newYearOfCreation = 1999;

        team.setName(newName);
        team.setCups(newCups);
        team.setYearOfCreation(newYearOfCreation);

        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        team = session.get(Team.class,3);
        assertEquals(team.getName(), newName);
        assertEquals(team.getCups(), newCups);
        assertEquals(team.getYearOfCreation(), newYearOfCreation);

        session.close();
    }

}
