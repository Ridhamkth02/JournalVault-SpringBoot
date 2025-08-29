package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //once security is added we cant access all users. we can though create this function for admin
//    @GetMapping
//    public List<User> getAllUser(){
//         return userService.getAll();
//    }



    // createuser can be public but delete and updatte user cant be public it should require username and passward

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        //jb koi bhi user authenticate hota hae  to uski details store hoti hae securitycontextholder mae ,waha se le lenge hum.
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
         userService.saveEntry(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
