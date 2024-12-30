package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "listing_type")
    private String listingType;

    @Column(name = "name")

    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "promo_offer")
    private String promoOffer;
    @Column(name = "price")
    private Double price;
    @Column(name = "location")
    private String location;

    @Column(name = "take_off_date")
    private LocalDate takeOffDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "image_path")
    private String imagePath;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromoOffer() {
        return promoOffer;
    }

    public void setPromoOffer(String promoOffer) {
        this.promoOffer = promoOffer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getTakeOffDate() {
        return takeOffDate;
    }

    public void setTakeOffDate(LocalDate takeOffDate) {
        this.takeOffDate = takeOffDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
