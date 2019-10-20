package Entity;

import javax.persistence.*;

@Entity
@Table(name = "driver_details")
public class DriverDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driverdet_id")
    private int id;

    @Column(name = "age")
    private int age;

    @Column(name = "startingNumber")
    private int startingNumber;

    @Column(name = "numberOfStarts")
    private int numberOfStarts;

    @Column(name = "country")
    private String country;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(int startingNumber) {
        this.startingNumber = startingNumber;
    }

    public int getNumberOfStarts() {
        return numberOfStarts;
    }

    public void setNumberOfStarts(int numberOfStarts) {
        this.numberOfStarts = numberOfStarts;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null || o.getClass()!= this.getClass()) {
            return false;
        }

        DriverDetails driverDetails = (DriverDetails) o;

        return (this.id == driverDetails.id);
    }
}
