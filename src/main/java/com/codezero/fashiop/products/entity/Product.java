package com.codezero.fashiop.products.entity;

import com.codezero.fashiop.categories.entity.Category;
import com.codezero.fashiop.shared.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "location")
    private String location;

    @Column(name = "product_picture")
    private String productPicture;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
}
