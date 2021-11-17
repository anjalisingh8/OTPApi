package com.otp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.otp.model.User;

@Repository
public interface UserRepo extends MongoRepository<User,String>{
	
	

}
