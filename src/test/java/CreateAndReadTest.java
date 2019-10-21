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
    public void testReadManyObjects() {
        //GIVEN
        Driver testDriver1 = new Driver();
        testDriver1.setName("Robert");
        testDriver1.setSurname("Kubica");
        testDriver1.setPoints(100);

        Driver testDriver2 = new Driver();
        testDriver2.setName("Sebastian");
        testDriver2.setSurname("Vettel");
        testDriver2.setPoints(50);

        Driver testDriver3 = new Driver();
        testDriver3.setName("Lewis");
        testDriver3.setSurname("Hamilton");
        testDriver3.setPoints(0);

        //WHEN
        session.persist(testDriver1);
        session.persist(testDriver2);
        session.persist(testDriver3);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver driver1 = session.get(Driver.class, 5);
        Driver driver2 = session.get(Driver.class, 6);
        Driver driver3 = session.get(Driver.class, 7);

        transaction.commit();
        session.close();

        assertEquals(testDriver1, driver1);
        assertEquals(testDriver2, driver2);
        assertEquals(testDriver3, driver3);
    }

    @Test
    public void testReadNotExistingObjects() {
        //GIVEN WHEN
        Team nullTeam = session.get(Team.class, 1);
        Driver nullDriver = session.get(Driver.class, 1);
        DriverDetails nullDetails = session.get(DriverDetails.class, 100);

        //THEN
        assertNull(nullTeam);
        assertNull(nullDriver);
        assertNull(nullDetails);
    }

    @Test
    public void testCreateAndReadTwoDrivers() { //dwa razy ten sam kierowca
        //GIVEN
        Driver testDriver = new Driver();
        testDriver.setName("Robert");
        testDriver.setSurname("Kubica");
        testDriver.setPoints(0);

        //WHEN
        session.persist(testDriver);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, 5);

        Driver newTestDriver = new Driver();
        newTestDriver.setName("Robert");
        newTestDriver.setSurname("Kubica");
        newTestDriver.setPoints(0);

        transaction.commit();

        assertEquals(driver, newTestDriver);
        session.close();

    }

    @Test
    public void testCreateAndReadTwoTeams() { // dwa razy ten sam zespol
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
        Team team = session.get(Team.class, 6);

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
        testTeam.setName("Williams");
        testTeam.setCups(6);
        testTeam.setYearOfCreation(2010);

        Driver testDriver = new Driver();
        testDriver.setName("Robert");
        testDriver.setSurname("Kubica");
        testDriver.setPoints(0);
        testDriver.setTeam(testTeam);

        DriverDetails testDriverDetails = new DriverDetails();
        testDriverDetails.setAge(35);
        testDriverDetails.setCountry("Polska");
        testDriverDetails.setStartingNumber(88);
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
        Team team = session.get(Team.class, 8);
        Driver driver = team.getDrivers().get(0);
        DriverDetails details = driver.getDriverDet();
        transaction.commit();
        session.close();

        assertEquals(team, testTeam);
        assertEquals(driver, testDriver);
        assertEquals(details, testDriverDetails);
    }

    @Test
    public void testReadWrongData() {
        //GIVEN
        Team testRightTeam = new Team();
        testRightTeam.setName("McLaren");
        testRightTeam.setCups(12);
        testRightTeam.setYearOfCreation(1966);

        Driver testRightDriver = new Driver();
        testRightDriver.setName("Lando");
        testRightDriver.setSurname("Norris");
        testRightDriver.setTeam(testRightTeam);

        DriverDetails testRightDriverDetails = new DriverDetails();
        testRightDriverDetails.setAge(20);
        testRightDriverDetails.setCountry("Wielka Brytania");
        testRightDriver.setDriverDet(testRightDriverDetails);

        ArrayList<Driver> rightDriverList = new ArrayList<>();
        rightDriverList.add(testRightDriver);
        testRightTeam.setDrivers(rightDriverList);


        Team testWrongTeam = new Team();
        testWrongTeam.setName("Not F1 Team");
        testWrongTeam.setCups(0);
        testWrongTeam.setYearOfCreation(3000);

        Driver testWrongDriver = new Driver();
        testWrongDriver.setName("Some");
        testWrongDriver.setSurname("Driver");
        testWrongDriver.setTeam(testWrongTeam);

        DriverDetails testWrongDriverDetails = new DriverDetails();
        testWrongDriverDetails.setAge(200);
        testWrongDriverDetails.setCountry("Country");
        testWrongDriver.setDriverDet(testWrongDriverDetails);

        ArrayList<Driver> wrongDriverList = new ArrayList<>();
        wrongDriverList.add(testWrongDriver);
        testWrongTeam.setDrivers(wrongDriverList);

        //WHEN
        session.persist(testRightTeam);
        session.persist(testWrongTeam);
        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team teamWrong = session.get(Team.class, 7);
        transaction.commit();

        assertNotEquals(teamWrong, testRightTeam);
        assertNotEquals(teamWrong.getDrivers().get(0), testRightTeam.getDrivers().get(0));
        assertNotEquals(teamWrong.getDrivers().get(0).getDriverDet(), testRightTeam.getDrivers().get(0).getDriverDet());
        session.close();
    }
}