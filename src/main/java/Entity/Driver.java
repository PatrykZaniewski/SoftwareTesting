package Entity;

import javax.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @Column(name = "driver_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "points")
    private int points;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private DriverDetails driverDet;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public DriverDetails getDriverDet() {
        return driverDet;
    }

    public void setDriverDet(DriverDetails driverDet) {
        this.driverDet = driverDet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null || o.getClass()!= this.getClass()) {
            return false;
        }

        Driver driver = (Driver) o;

        return (this.name.equals(driver.name) && this.surname.equals(driver.surname));
    }
}
