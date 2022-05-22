package com.earezki.accounts.domain;

import java.util.UUID;

public interface AccountRepository {

    void save(Account account);

    Account accountOfId(UUID id);

}
