package com.emilie.ClimbingWebApp.service;

import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save (User user){
        userRepository.save(user);
    }

    public List<User> listAll(){
        return (List<User>) userRepository.findAll();
    }

    public User get(Long id){
        return userRepository.findById( id ).get();
    }


    public void delete(Long id){
        userRepository.deleteById( id );
    }
 /* public List<User> search(String keyword){
        return userRepository.search(keyword);
    }*/

}
