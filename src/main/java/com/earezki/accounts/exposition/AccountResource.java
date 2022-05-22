package com.earezki.accounts.exposition;

import com.earezki.accounts.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Clock;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts/")
class AccountResource {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private Clock clock;

    @PostMapping(
            path = "/{accountId}/transactions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> executeTransaction(@PathVariable("accountId") UUID accountId,
                                         @RequestBody ExecuteTransactionCommand command) {
        Account account = accountRepository.accountOfId(accountId);

        switch (command.type()) {
            case DEPOSIT -> account.deposit(command.amount(), clock);
            case WITHDRAW -> account.withdrawal(command.amount(), clock);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            path = "/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountDto> accountOfId(@PathVariable("accountId") UUID accountId) {
        Account account = accountRepository.accountOfId(accountId);
        Statement statement = account.statement();
        Receipt receipt = statement.receipt();

        AccountDto dto = new AccountDto(accountId,
                account.currentBalance(),
                receipt.lines()
                        .stream()
                        .map(line -> new ReceiptDto(line.date(), line.transactionAmount(), line.balance()))
                        .toList()
                );
        return ResponseEntity.ok(dto);
    }

    @ExceptionHandler
    ResponseEntity<?> handleFundsExceededException(FundsExceededException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .build();
    }

}
