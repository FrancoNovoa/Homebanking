package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {
  @Autowired
  private TransactionRepository transactionRepository;
  @Test
  public void existTransfer(){
    List<Transaction> transactions = transactionRepository.findAll();
    assertThat(transactions, is(not(empty())));
  }
  @Test
  public void existTypeTransfer(){
    List<Transaction> transactions = transactionRepository.findAll();
    assertThat(transactions,hasItem(hasProperty("type", is(CREDIT))));
  }
}
