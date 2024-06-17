package com.huzaifa.cafe.sollydz.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "SELECT u from User u WHERE u.email = :email")
@NamedQuery(name = "User.getAllUsers", query = "SELECT new com.huzaifa.cafe.sollydz.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")
//@NamedQuery(name = "User.updateStatus", query = "update User u set  u.status=:status where u.id=:id")

@Entity
@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "contactNumber")
    private String contactNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;

}
