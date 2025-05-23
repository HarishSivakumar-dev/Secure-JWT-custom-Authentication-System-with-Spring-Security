package com.harish.SecurityJwtEncrypt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface CredentialsDao extends JpaRepository<RegisterDetails,Integer>
{

	Optional<RegisterDetails> findByName(String name);
}
