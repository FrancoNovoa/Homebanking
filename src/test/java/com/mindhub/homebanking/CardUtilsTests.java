package com.mindhub.homebanking;

import com.mindhub.homebanking.utilities.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardUtilsTests {
  @Test
  public void cardNumbersCreated(){
    String cardNumber = CardUtils.getCardNumber();
    assertThat(cardNumber,is(not(emptyOrNullString())));
  }
  @Test
  public void cardNumberIsString(){
    String cardNumber = CardUtils.getCardNumber();
    assertThat(cardNumber, any(String.class));
  }
  @Test
  public void cardCVVCreated(){
    int cvvNumber = CardUtils.getCvvNumber();
    assertThat(cvvNumber,is(not(nullValue())));
  }
  @Test
  public void cardCVVNumberIsNumber(){
    int cvvNumber = CardUtils.getCvvNumber();
    assertThat(cvvNumber, any(int.class));
  }
  @Test
  public void cardCVVRange(){
    int cvvNumber= CardUtils.getCvvNumber();
    assertThat(cvvNumber, lessThan(1000));
    assertThat(cvvNumber, greaterThan(99));
  }
}
