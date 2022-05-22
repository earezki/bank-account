package com.earezki.accounts;

import com.earezki.accounts.domain.Account;
import com.earezki.accounts.domain.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Logger LOG = LoggerFactory.getLogger(InMemoryAccountRepository.class);

    private final ConcurrentMap<UUID, Account> data = new ConcurrentHashMap<>();

    public InMemoryAccountRepository() {
        UUID id = UUID.fromString("a872313d-4f7e-4c24-92a4-01688668431d");
        data.put(id, new Account(id));

        LOG.info("Account id [{}]", id);
    }

    @Override
    public void save(Account account) {
        data.put(account.id(), account);
    }

    @Override
    public Account accountOfId(UUID id) {
        return data.get(id);
    }

}
