package com.juntemos.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.juntemos.models.User;
import com.juntemos.repository.UserRepository;


@Service
public class ImageService {


	@Autowired
    private UserRepository userRepository;
    
    
    @Transactional
    public void saveImageFile(Long userId, MultipartFile file) {

        try { 	
            User user = userRepository.findById(userId).get();
            
            user.setImage(file.getBytes());          
            userRepository.save(user);
            
        } catch (IOException e) {
            //todo handle better
            System.out.println("Error occurred" + e);

            e.printStackTrace();
        }
    }
    
    

    
}