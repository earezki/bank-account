package com.earezki.accounts.exposition;

import com.earezki.accounts.BankAccountApplication;
import com.earezki.accounts.domain.Account;
import com.earezki.accounts.domain.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankAccountApplication.class)
@AutoConfigureMockMvc
public class AccountResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void should_retrieve_existing_account() throws Exception {
        UUID accountId = UUID.randomUUID();

        accountRepository.save(new Account(accountId));

        mvc.perform(get("/api/accounts/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_add_transactions_to_an_account() throws Exception {
        UUID accountId = UUID.randomUUID();

        accountRepository.save(new Account(accountId));

        mvc.perform(post("/api/accounts/{accountId}/transactions", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new ExecuteTransactionCommand(TransactionType.DEPOSIT, BigDecimal.valueOf(100))))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void should_fail_to_make_transaction_when_funds_are_missing() throws Exception {
        UUID accountId = UUID.randomUUID();

        accountRepository.save(new Account(accountId));

        mvc.perform(post("/api/accounts/{accountId}/transactions", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new ExecuteTransactionCommand(TransactionType.DEPOSIT, BigDecimal.valueOf(100))))
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        mvc.perform(post("/api/accounts/{accountId}/transactions", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new ExecuteTransactionCommand(TransactionType.WITHDRAW, BigDecimal.valueOf(200))))
                )
                .andDo(print())
                .andExpect(status().isNotAcceptable());

        mvc.perform(get("/api/accounts/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100))
                .andExpect(jsonPath("$.statement").isArray())
        ;

    }

}
