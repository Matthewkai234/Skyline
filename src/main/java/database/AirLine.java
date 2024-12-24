package database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="airline")
public class AirLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airLineId")
    private int airLineId;

    @Column(name="airLineName")
    private String airLineName;

    @Column(name="startDate")
    private Date startDate;

    @Column(name = "arriveDate")
    private Date arriveDate;

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }


    public int getAirLineId() {
        return airLineId;
    }

    public void setAirLineId(int airLineId) {
        this.airLineId = airLineId;
    }

    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
