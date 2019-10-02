package com.sk.wallet.controller;

import com.sk.wallet.dataobject.Account;
import com.sk.wallet.dataobject.Wallet;
import com.sk.wallet.datatransferobject.AccountDTO;
import com.sk.wallet.datatransferobject.WalletDTO;
import com.sk.wallet.datatransferobject.mapper.AccountMapper;
import com.sk.wallet.datatransferobject.mapper.WalletMapper;
import com.sk.wallet.exception.InsufficientBalanceException;
import com.sk.wallet.exception.WalletException;
import com.sk.wallet.service.AccountService;
import com.sk.wallet.service.WalletService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author sdagur
 *
 */
@RestController
@RequestMapping("v1/players")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private WalletService walletService;

  @GetMapping
  public ResponseEntity getPlayers() {
    List<Account> accounts = accountService.getList();
    return new ResponseEntity<List<AccountDTO>>(AccountMapper.doToDTOList(accounts), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity getPlayer(@PathVariable("id") Long id) {
    Account account;
    try {
      account = accountService.accountByPK(id);
    } catch (WalletException ex) {
      Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<AccountDTO>(AccountMapper.doToDTO(account), HttpStatus.OK);

  }

  @PostMapping
  public ResponseEntity createPlayer(@Valid @RequestBody AccountDTO accountDTO) {
    Account saved;
    try {
      saved = accountService.save(AccountMapper.dtoToDO(accountDTO));
    } catch (WalletException ex) {
      Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<AccountDTO>(AccountMapper.doToDTO(saved), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity updatePlayer(@PathVariable("id") Long accountId,
      @Valid @RequestBody AccountDTO accountDTO) {
    Account saved;
    try {
      saved = accountService.update(AccountMapper.dtoToDO(accountDTO), accountId);
    } catch (WalletException ex) {
      Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<AccountDTO>(AccountMapper.doToDTO(saved), HttpStatus.OK);
  }

  @PostMapping("/{id}/transactions")
  public ResponseEntity createTransactionForPlayer(@PathVariable("id") Long accountId,
      @Valid @RequestBody WalletDTO walletDTO) {
    Wallet saved;
    try {
      walletDTO.setAccountId(accountId);
      saved = walletService.createTransaction(WalletMapper.dtoToDO(walletDTO));
    } catch (WalletException | InsufficientBalanceException ex) {
      Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<WalletDTO>(WalletMapper.doToDTO(saved), HttpStatus.CREATED);
  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity getTransactionsHistoryForPlayer(@PathVariable("id") Long id)
      throws WalletException {
    List<Wallet> wallets = walletService.transactionsByAccountID(id);
    return new ResponseEntity<List<WalletDTO>>(WalletMapper.doToDTOList(wallets), HttpStatus.OK);


  }

  @GetMapping("/transactions/{txnId}")
  public ResponseEntity getTransactionDetailForTxnId(@PathVariable("txnId") Long txnId)
      throws WalletException {
    Wallet wallet;
    try {
      wallet = walletService.transactionById(txnId);
    } catch (WalletException ex) {
      Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
      return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<WalletDTO>(WalletMapper.doToDTO(wallet), HttpStatus.CREATED);

  }

  // Delete a Player
  @DeleteMapping("/{id}")
  public ResponseEntity deletePlayer(@PathVariable(value = "id") Long id) throws WalletException {
    Account account = accountService.accountByPK(id);

    accountService.delete(account);

    return ResponseEntity.ok().build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

}
