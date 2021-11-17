package com.otp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.otp.model.User;
import com.otp.repository.UserRepo;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class OtpAppApplicationTests {

	@Autowired
	UserRepo repo;
	
	
	/*DAO Layer Testing*/
	@Test
	@Order(1)
	public void testCreate()	 {
		User u = new User();
		u.setEmailAddress("singhanjali7992@gmail.com");
		u.setOtp("908765");
		u.setStartTime(System.currentTimeMillis());
		repo.save(u);
		assertNotNull(repo.findById("singhanjali7992@gmail.com").get());
		
	}
	
//	@Test
//	@Order(2)
//	public void testReadAll() {
//		List<User> list = repo.findAll();
//		assertThat(list).size();
//	}
	
	@Test
	@Order(2)
	public void testUser() {
		User user = repo.findById("singhanjali7992@gmail.com").get();
		assertEquals("908765",user.getOtp());
	}

	@Test
	@Order(3)
	public void testUpdate() {
		User u = repo.findById("singhanjali7992@gmail.com").get();
		u.setOtp("987651");
		repo.save(u);
		assertNotNull("908765",repo.findById("singhanjali7992@gmail.com").get().getOtp());
		}
	
	@Test
	@Order(4)
	public void testDelete() {
		repo.deleteById("anjali04112001@gmail.com");
		assertThat(repo.existsById("anjali04112001@gmail.com")).isFalse();
	}
	
	
	
	
}
