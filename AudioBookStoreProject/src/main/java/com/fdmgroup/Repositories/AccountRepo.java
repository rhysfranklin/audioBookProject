package com.fdmgroup.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.Entities.Account;

public interface AccountRepo extends JpaRepository<Account, String> {
	
	public List<String> findByPassword(String password);

}
