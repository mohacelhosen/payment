package payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import payment.model.BankAccount;
import payment.repository.BankAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Transactional
    public BankAccount createBankAccount(BankAccount bankAccount) {
        if (!valid(bankAccount.getAccountEmail())) {
            String accountNumber = String.valueOf(UUID.randomUUID().getMostSignificantBits()).replace("-", "");
            bankAccount.setAccountNumber(accountNumber);
            return bankAccountRepository.save(bankAccount);
        } else {
            throw new RuntimeException("This user already registered");
        }
    }

    public boolean valid(String email) {
        return bankAccountRepository.findByEmail(email).isPresent();
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(String accountId) {
        return bankAccountRepository.findById(accountId);
    }

    public Optional<BankAccount> getBankAccountByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber);
    }

    public void updateBalance(String accountNumber, Double amount) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        optionalBankAccount.ifPresent(account -> {
            account.setBalance(account.getBalance()+amount);
            bankAccountRepository.save(account);
        });
    }

    public String deleteBankAccount(String accountNumber) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (optionalBankAccount.isEmpty()){
            throw new RuntimeException("Account Not Found!");
        }
        bankAccountRepository.delete(optionalBankAccount.get());
        return "Bank account :"+accountNumber+"  delete successfully ";
    }

    @Transactional
    public void transferMoney(String sourceAccountNumber, String targetAccountNumber, Double amount) {
        Optional<BankAccount> sourceAccountOptional = bankAccountRepository.findByAccountNumber(sourceAccountNumber);
        Optional<BankAccount> targetAccountOptional = bankAccountRepository.findByAccountNumber(targetAccountNumber);

        if (sourceAccountOptional.isPresent() && targetAccountOptional.isPresent()) {
            BankAccount sourceAccount = sourceAccountOptional.get();
            BankAccount targetAccount = targetAccountOptional.get();

            if (sourceAccount.getBalance() >= amount) {
                sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                targetAccount.setBalance(targetAccount.getBalance() + amount);

                bankAccountRepository.save(sourceAccount);
                bankAccountRepository.save(targetAccount);
            } else {
                throw new RuntimeException("Insufficient funds in the source account");
            }
        } else {
            throw new RuntimeException("Invalid source or target account");
        }
    }
}
