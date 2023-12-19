package payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment.model.ApiResponse;
import payment.model.BankAccount;
import payment.service.BankAccountService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/bank")
public class BankController {
    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BankAccount>> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            BankAccount createdAccount = bankAccountService.createBankAccount(bankAccount);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Bank account created successfully", createdAccount, null, "/api/v1/bank/create"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, null, "/api/v1/bank/create"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BankAccount>>> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Bank accounts retrieved successfully", bankAccounts, null, "/api/v1/bank/all"));
    }

    @GetMapping("/get-bank-account")
    public ResponseEntity<ApiResponse<BankAccount>> getBankAccountByAccountNumber(@RequestParam String accountNumber) {
        try {
            BankAccount bankAccount = bankAccountService.getBankAccountByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Bank account retrieved successfully", bankAccount, null, "/api/v1/bank/" + accountNumber));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null, null, "/api/v1/bank/" + accountNumber));
        }
    }

    @PutMapping("/update-balance")
    public ResponseEntity<ApiResponse<BankAccount>> updateBalance(@RequestParam String accountNumber, @RequestParam Double newBalance) {
        try {
            bankAccountService.updateBalance(accountNumber, newBalance);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Balance updated successfully", null, null, "/api/v1/bank/update-balance/" + accountNumber));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, null, "/api/v1/bank/update-balance/" + accountNumber));
        }
    }

    @PutMapping("/transfer-money")
    public ResponseEntity<ApiResponse<Void>> transferMoney(@RequestParam String sourceAccountNumber, @RequestParam String targetAccountNumber, @RequestParam Double amount) {
        try {
            bankAccountService.transferMoney(sourceAccountNumber, targetAccountNumber, amount);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Money transferred successfully", null, null, "/api/v1/bank/transfer-money"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, null, "/api/v1/bank/transfer-money"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteBankAccount(@RequestParam String accountNumber) {
        try {
            String account = bankAccountService.deleteBankAccount(accountNumber);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), account, null, null, "/api/v1/bank/delete/" + accountNumber));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, null, "/api/v1/bank/delete/" + accountNumber));
        }
    }
}
