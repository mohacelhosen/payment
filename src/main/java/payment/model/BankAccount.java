package payment.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class BankAccount {
	@Id
	private String id;
	
    private String accountHolderName;
    private String accountEmail;
    private String accountNumber;
    private Double balance;
    private String currency;
    private LocalDateTime lastTransactionDate;

}
