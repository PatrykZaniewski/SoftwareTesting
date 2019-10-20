package Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="team")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "cups")
    private int cups;

    @Column(name = "yearOfCreation")
    private int yearOfCreation;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Driver> drivers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cups=" + cups +
                ", yearOfCreation=" + yearOfCreation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null || o.getClass()!= this.getClass()) {
            return false;
        }

        Team team = (Team) o;

        return (this.name.equals(team.name) && this.id == team.id);
    }
}
