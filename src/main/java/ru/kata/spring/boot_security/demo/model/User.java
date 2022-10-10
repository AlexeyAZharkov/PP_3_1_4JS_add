package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "age")
   private int age;

   @Column(name = "email")
   private String email;

   @Column(name = "password")
   private String password;

   @Transient
   private String role;

   @ManyToMany(fetch = FetchType.LAZY)
   @Fetch(FetchMode.JOIN)
   @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "roles_id")
   )
   private Set<Role> roles;

   public User() {}

   public User(String firstName, String lastName, int age, String email, String password, String role) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
      this.email = email;
      this.password = password;
      this.role = role;
   }

   public void addRole(Role role) {
      if (roles == null) {
         roles = new HashSet<>();
      }
      this.roles.add(role);
   }

   public void addRoleForm(Role role) {
      roles = new HashSet<>();
      this.roles.add(role);
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public String getStringRoles() {
      List<String> list = new ArrayList<>();
      for (Role n : roles) {
         list.add(n.getName().replace("ROLE_", ""));
      }
      Collections.sort(list);
      return String.join(" ", list);
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getUserPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", age=" + age +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              ", role='" + role + '\'' +
              '}';
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles();
   }

   @Override
   public String getPassword()  {
      return password;
   }

   @Override
   public String getUsername()  {
      return firstName;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
