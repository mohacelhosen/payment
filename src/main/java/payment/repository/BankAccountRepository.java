package payment.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import payment.model.BankAccount;

public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

	@Query("{'accountEmail':?0}")
	Optional<BankAccount> findByEmail(String email);
	
	@Query("{'accountNumber':?0}")
	Optional<BankAccount> findByAccountNumber(String accountNumber);
}
