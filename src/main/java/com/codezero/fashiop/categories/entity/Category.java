package com.codezero.fashiop.categories.entity;

import com.codezero.fashiop.products.entity.Product;
import com.codezero.fashiop.shared.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Set<Product> products;
}
