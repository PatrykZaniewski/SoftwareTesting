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

public class DeleteTest {

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
    public void testDeleteDriver() {
        //GIVEN
        Driver toDeleteDriver = new Driver();
        toDeleteDriver.setName("Delete me");
        toDeleteDriver.setSurname("Driver");
        toDeleteDriver.setPoints(10);
        session.persist(toDeleteDriver);

        Driver testDriver = new Driver();
        testDriver.setName("Driver1");
        testDriver.setSurname("SuperDriver");
        testDriver.setPoints(50);
        session.persist(testDriver);
        
        transaction.commit();
        session.close();

        //WHEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver deleteDriver = session.get(Driver.class, 1);
        
        session.delete(deleteDriver);

        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Driver driver = session.get(Driver.class, 1);

        assertNotEquals(driver, toDeleteDriver);
        session.close();
    }

    @Test
    public void testDeleteTeam() {
        //GIVEN
        Team toDeleteTeam = new Team();
        toDeleteTeam.setName("DeleteTeam");
        toDeleteTeam.setCups(32);
        toDeleteTeam.setYearOfCreation(1994);
        session.persist(toDeleteTeam);

        Team testTeam = new Team();
        testTeam.setName("TestTeam");
        testTeam.setCups(23);
        testTeam.setYearOfCreation(1949);
        session.persist(testTeam);
        
        transaction.commit();
        session.close();

        //WHEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team deleteTeam = session.get(Team.class, 1);
       
        session.delete(deleteTeam);

        transaction.commit();
        session.close();

        //THEN
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Team team = session.get(Team.class, 1);

        assertNotEquals(team, toDeleteTeam);

        session.close();
    }
}
