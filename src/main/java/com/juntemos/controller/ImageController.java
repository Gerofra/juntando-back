package com.juntemos.controller;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.juntemos.models.User;
import com.juntemos.repository.UserRepository;
import com.juntemos.service.ImageService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/images")
public class ImageController {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	UserRepository userRepository;

	@Async
    @PostMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file){
    	
    	if (!userRepository.existsById(id)) {
    		return new ResponseEntity<>("El usuario no existe.", HttpStatus.BAD_REQUEST);
		} else {
			if (file.getSize() > 4200000) {
				return new ResponseEntity<>("Imagen demasiado pesada.", HttpStatus.BAD_REQUEST);
			} else {
				imageService.saveImageFile(id, file);
				return new ResponseEntity<>("Se ha guardado con éxito.", HttpStatus.OK);
			}
		}       
    }
    
    
	@Async
    @GetMapping(value = "/{id}/profile", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable Long id) throws IOException {

	        User user = userRepository.findById(id).get();
	
	        if (user.getImage() != null) {
	            byte[] byteArray = new byte[user.getImage().length];
	            int i = 0;
	
	            for (Byte wrappedByte : user.getImage()){
	                byteArray[i++] = wrappedByte; //auto unboxing
	            }
	
	            InputStream in = new ByteArrayInputStream(byteArray);
    	
    		    return IOUtils.toByteArray(in);
    		}
	        
			return null;
	   }
      
	
}
