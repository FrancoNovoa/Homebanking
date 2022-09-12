package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.mindhub.homebanking.models.AccountType.CURRENT;
import static com.mindhub.homebanking.models.AccountType.SAVINGS;
import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.CardType.CREDIT;
import static com.mindhub.homebanking.models.CardType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
//			Client cliente1 = new Client("Melba", "Lorenzo","melbita@mindhub.com",passwordEncoder.encode("melbagordita"));
//			Client cliente2 = new Client("Juan Sebastian", "Veron", "brujita7-0@hotmail.com",passwordEncoder.encode("tecomiste7") );
//			Account cuenta1 = new Account("VIN001", LocalDateTime.now(), 5000.0, CURRENT, true);
//			Account cuenta2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.0, SAVINGS, true);
//			Account cuenta3 = new Account("VIN777", LocalDateTime.now(),7070.0, CURRENT, true);
//			Transaction transaccion1 = new Transaction(TransactionType.CREDIT, 1000.0, "You're a good person", LocalDateTime.now());
//			Transaction transaccion2 = new Transaction(TransactionType.DEBIT, -500.0, "You're a bad person", LocalDateTime.now());
//			Loan prestamo1 = new Loan("Mortgage", 500000.00, Arrays.asList(12,24,36,48,60));
//			Loan prestamo2 = new Loan("Personal", 100000.00, Arrays.asList(6,12,24));
//			Loan prestamo3 = new Loan("Car", 300000.00, Arrays.asList(6,12,24,36));
//			ClientLoan clientePrestamo1 = new ClientLoan(400000.00, prestamo1.getPayment().get(4), prestamo1, cliente1);
//			ClientLoan clientePrestamo2 = new ClientLoan(50000.00, prestamo2.getPayment().get(1), prestamo2,cliente1);
//			ClientLoan clientePrestamo3 = new ClientLoan(100000.00, prestamo2.getPayment().get(2), prestamo2,cliente2);
//			ClientLoan clientePrestamo4 = new ClientLoan(200000.00, prestamo3.getPayment().get(3), prestamo3,cliente2);
//			Card cartaSilver1 = new Card(cliente2.getName() + " " + cliente1.getLastName(), SILVER, CREDIT, 700, "0700-7077-0700-0707", LocalDateTime.now(), LocalDateTime.now().plusYears(7), cliente2, true);
//			Card cartaGold = new Card(cliente1.getName() + " " + cliente1.getLastName(), GOLD, DEBIT, 666, "8576-3678-1423-0065", LocalDateTime.now(), LocalDateTime.now().plusYears(5), cliente1, true);
//			Card cartaTitanium = new Card(cliente1.getName() + " " + cliente1.getLastName(), TITANIUM, CREDIT, 834, "0134-8239-2404-7432", LocalDateTime.now(), LocalDateTime.now().plusYears(5), cliente1, true);
//
//			cliente1.addAccount(cuenta1);
//			cliente1.addAccount(cuenta2);
//			cliente2.addAccount(cuenta3);
//			cuenta1.addTransaction(transaccion1);
//			cuenta1.addTransaction(transaccion2);
//			clientRepository.save(cliente1);
//			clientRepository.save(cliente2);
//			accountRepository.save(cuenta1);
//			accountRepository.save(cuenta2);
//			accountRepository.save(cuenta3);
//			transactionRepository.save(transaccion1);
//			transactionRepository.save(transaccion2);
//			loanRepository.save(prestamo1);
//			loanRepository.save(prestamo2);
//			loanRepository.save(prestamo3);
//			clientLoanRepository.save(clientePrestamo1);
//			clientLoanRepository.save(clientePrestamo2);
//			clientLoanRepository.save(clientePrestamo3);
//			clientLoanRepository.save(clientePrestamo4);
//			cardRepository.save(cartaSilver1);
//			cardRepository.save(cartaGold);
//			cardRepository.save(cartaTitanium);

		};
	}
//	@Autowired
//	private PasswordEncoder passwordEncoder;
}

