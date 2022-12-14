package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
  @Autowired
  private ClientRepository clientRepository;

  @Test
  public void existClients(){
    List<Client> clients = clientRepository.findAll();
    assertThat(clients, is(not(empty())));
  }
  @Test
  public void existName(){
    List<Client> clients = clientRepository.findAll();
    assertThat(clients, hasItem(hasProperty("name", is("Melba"))));
  }
}
