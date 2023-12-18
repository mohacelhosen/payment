package payment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PaymentModel {
	@Id
	private String id;

	private String orderId; // Unique identifier for the order
	private String paymentMethod; // Credit card, PayPal, etc.
	private double amount; // Transaction amount
	private String currency; // Currency code (USD, EUR, etc.)
	private String status; // Payment status (pending, completed, failed, etc.)
	private String customerEmail; // Email address of the customer
	private String transactionId; // Unique identifier for the payment transaction

}
