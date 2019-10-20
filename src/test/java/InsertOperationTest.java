import Config.SessionConfig;
import Entity.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertOperationTest {

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
    public void testCreateTeam() {
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
        session.close();

        assertEquals(team, testTeam);
    }
}