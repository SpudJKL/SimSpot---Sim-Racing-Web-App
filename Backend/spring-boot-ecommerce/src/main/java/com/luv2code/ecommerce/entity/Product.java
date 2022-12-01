package com.luv2code.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    // Relationship
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl = "assets/images/products/placeholderListing.jpg";

    @Column(name = "status")
    private boolean status;

    @Column(name = "delivery_method")
    private boolean deliveryMethod;

    @Column(name = "orginalboxes")
    private boolean ogBoxes;

    @Column(name = "date_created")
    @CreationTimestamp // Hibernate manages these timestamps
    private Date dateCreated;

    @Column(name = "date_updated")
    @UpdateTimestamp // Hibernate will update these timestamps
    private Date dateUpdated;

    public Product() {

    }

    public Product(String name, String description, String location, String imageUrl, BigDecimal price, Boolean ogBoxes, Boolean deliveryMethod, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.price = price;
        this.ogBoxes = ogBoxes;
        this.deliveryMethod = deliveryMethod;
        this.category = category;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(boolean deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public boolean isOgBoxes() {
        return ogBoxes;
    }

    public void setOgBoxes(boolean ogBoxes) {
        this.ogBoxes = ogBoxes;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", deliveryMethod=" + deliveryMethod +
                ", ogBoxes=" + ogBoxes +
                '}';
    }
}

