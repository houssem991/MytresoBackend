package com.bezkoder.springjwt.models;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;
  private String firstname;
  private String lastname;
  private String image;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;
  @Column(name = "reset_token")
  private String resetToken;

  @Column()
  private Date expiryDate;


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  @ManyToOne()
  @JoinColumn(name = "Enterprise_id")
  @JsonIgnore
  private Entreprise entreprise;
  public User() {
  }

  public void setExpiryDate(int minutes){
    Calendar now = Calendar.getInstance();
    now.add(Calendar.MINUTE, minutes);
    this.expiryDate = now.getTime();
  }

  public boolean isExpired() {
    return new Date().after(this.expiryDate);
  }
}
