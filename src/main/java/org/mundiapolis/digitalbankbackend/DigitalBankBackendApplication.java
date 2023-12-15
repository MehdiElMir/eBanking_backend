package org.mundiapolis.digitalbankbackend;

import org.mundiapolis.digitalbankbackend.entities.*;
import org.mundiapolis.digitalbankbackend.enums.AccountStatus;
import org.mundiapolis.digitalbankbackend.enums.OperationType;
import org.mundiapolis.digitalbankbackend.repositories.AccountOperationRepository;
import org.mundiapolis.digitalbankbackend.repositories.BankAccountRepository;
import org.mundiapolis.digitalbankbackend.repositories.CustomerRepository;
import org.mundiapolis.digitalbankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankBackendApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassin","Aicha").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            
            bankAccountRepository.findAll().forEach(acc ->
                    {
                        for (int i = 0; i < 5; i++) {
                            AccountOperation accountOperation = new AccountOperation();
                            accountOperation.setOperationDate(new Date());
                            accountOperation.setAmount(Math.random()*120000);
                            accountOperation.setType(Math.random()>0.5? OperationType.CREDIT : OperationType.DEBIT);
                            accountOperation.setBankAccount(acc);
                            accountOperationRepository.save(accountOperation);
                        }
                        }


                    );


        };
    }
    @Bean
    CommandLineRunner commandLineRunner(BankService bankService){
        return args -> {
            bankService.consulter();
        };
    }
}
