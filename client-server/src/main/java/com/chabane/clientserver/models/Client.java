package com.chabane.clientserver.models;

import com.chabane.clientserver.enums.ClientSex;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "nom et prénom est rquis")
    @Column(name="full_name")
    private String fullName;

    @NotBlank(message = "adresse est rquis")
    private String address;

    @NotBlank(message = "téléphone est rquis")
    @Column(name="phone", length=20, unique=true)
    private String phone;

    @NotEmpty(message = "email est rquis")
    @Email(message = "entrez un valide email")
    @Column(name="email", length=50, unique=true)
    private String email;

    @NotBlank(message = "mot de pass est rquis")
    private String password;

    @Min(value = 18, message = "age doit etre +18")
    private int age;

    //    @NotBlank(message = "sexe est rquis")
    @Enumerated(EnumType.ORDINAL)
    private ClientSex sex = ClientSex.MALE;
}
