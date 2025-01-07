package model;


import javax.persistence.*;

@Entity
@Table(name = "latest_booking")
public class LatestBookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;

    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "type")
    private String type;
    @Column(name = "details")
    private String details;

    public LatestBookingModel() {}

    public LatestBookingModel(int booking_id, String customerName, String type, String details) {
        this.booking_id = booking_id;
        this.customerName = customerName;
        this.type = type;
        this.details = details;
    }

    public LatestBookingModel(String customerName, String type, String details) {
        this.customerName = customerName;
        this.type = type;
        this.details = details;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
