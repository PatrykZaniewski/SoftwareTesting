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

import static org.junit.jupiter.api.Assertions.*;

public class CreateAndReadTest {

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
    public void testCreateAndReadDriver() {
        //GIVEN
        Driver testDriver = new Driver();
        testDriver.setName("Lewis");
        testDriver.setSurname("Hamilton");
        testDriver.setPoints(0);

        //WHEN
        session.persist(testDriver);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, 1);

        Driver newTestDriver = new Driver();
        newTestDriver.setName("Lewis");
        newTestDriver.setSurname("Hamilton");
        newTestDriver.setPoints(0);
        newTestDriver.setId(driver.getId());

        transaction.commit();
        session.close();

        assertEquals(driver, testDriver);
        assertEquals(driver, newTestDriver);
    }

    @Test
    public void testCreateAndReadTeam() {
        //GIVEN
        Team testTeam = new Team();
        testTeam.setName("McLaren");
        testTeam.setCups(12);
        testTeam.setYearOfCreation(1966);

        //WHEN
        session.persist(testTeam);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team team = session.get(Team.class, 1);

        Team newTestTeam = new Team();
        newTestTeam.setName("McLaren");
        newTestTeam.setCups(12);
        newTestTeam.setYearOfCreation(1966);
        newTestTeam.setId(team.getId());

        transaction.commit();
        session.close();

        assertEquals(team, testTeam);
        assertEquals(team, newTestTeam);
    }

    @Test
    public void testCreateTeamAndDrivers() {
        //GIVEN
        Team testTeam = new Team();
        testTeam.setName("Mercedes AMG Petronas");
        testTeam.setCups(6);
        testTeam.setYearOfCreation(2010);

        Driver testDriver = new Driver();
        testDriver.setName("Lewis");
        testDriver.setSurname("Hamilton");
        testDriver.setPoints(0);
        testDriver.setTeam(testTeam);

        DriverDetails testDriverDetails = new DriverDetails();
        testDriverDetails.setAge(34);
        testDriverDetails.setCountry("Wielka Brytania");
        testDriverDetails.setStartingNumber(44);
        testDriverDetails.setNumberOfStarts(33);
        testDriverDetails.setDriver(testDriver);
        testDriver.setDriverDet(testDriverDetails);

        ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(testDriver);
        testTeam.setDrivers(drivers);

        //WHEN
        session.persist(testTeam);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team team = session.get(Team.class, 4);
        Driver driver = team.getDrivers().get(0);
        DriverDetails details = driver.getDriverDet();
        transaction.commit();
        session.close();

        assertEquals(team, testTeam);
        assertEquals(driver, testDriver);
        assertEquals(details, testDriverDetails);
    }

    @Test
    public void testReadWrongTeam() {
        //GIVEN
        Team testRightTeam = new Team();
        testRightTeam.setName("McLaren");
        testRightTeam.setCups(12);
        testRightTeam.setYearOfCreation(1966);

        Team testWrongTeam = new Team();
        testWrongTeam.setName("Not F1 Team");
        testWrongTeam.setCups(0);
        testWrongTeam.setYearOfCreation(3000);

        //WHEN
        session.persist(testRightTeam);
        session.persist(testWrongTeam);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team teamRight = session.get(Team.class, 2);
        Team teamWrong = session.get(Team.class, 1);
        transaction.commit();
        session.close();

        assertEquals(teamRight, testRightTeam);
        assertNotEquals(teamWrong, testRightTeam);
    }
}