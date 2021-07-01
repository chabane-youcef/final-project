package com.chabane.productserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "le nom est requis")
    private String name;

    @Min(value = 1, message = "le prix doit etre superieur a 1")
    private int price;

    @NotBlank(message = "l'image est requis")
    private String image;

    @NotBlank(message = "categories est requis")
    private String categories;

    @Transient
    private List<Object> categoriesObjects;
}
