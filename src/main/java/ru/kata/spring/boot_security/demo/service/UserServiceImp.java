package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
   private UserDao userDaoImp;

   public UserServiceImp(UserDao userDaoImp) {
      this.userDaoImp = userDaoImp;
   }

   @Transactional
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public void addUser(User user) {
      userDaoImp.addUser(user);
   }

   @Transactional
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public void updateUser(Long id, User updatedUser) {
      userDaoImp.updateUser(id, updatedUser);
   }

   @Transactional
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public void deleteUser(Long id) {
      userDaoImp.deleteUser(id);
   }

   @Transactional(readOnly = true)
   public User getUserById(Long id) {
      return userDaoImp.getUserById(id);
   }

   @Transactional(readOnly = true)
   public User getUserByName(String name) {
      return userDaoImp.getUserByName(name);
   }

   @Transactional(readOnly = true)
   public List<User> listUsers() {
      return userDaoImp.listUsers();
   }

   @Override
   @Transactional(readOnly = true)
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Optional<User> user = Optional.ofNullable(userDaoImp.getUserByEmail(email));
      if (user.isEmpty()) {
         throw new UsernameNotFoundException("User not found");
      }
      return user.get();
   }
}
