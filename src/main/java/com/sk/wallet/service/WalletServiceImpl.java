package com.sk.wallet.service;

import com.google.common.collect.Lists;

import com.sk.wallet.dataaccessobject.WalletRepository;
import com.sk.wallet.dataobject.Wallet;
import com.sk.wallet.exception.InsufficientBalanceException;
import com.sk.wallet.exception.WalletException;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author sdagur
 *
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;
  @Autowired
  private AccountService accountService;

  /**
   * retrieve transactions by their transaction reference this operations is used to validate if a
   * transaction Id has been used previously
   */
  @Override
  public Wallet transactionById(Long txnId) throws WalletException {
    return walletRepository.getTransactionById(txnId).orElseThrow(
        () -> new WalletException(String.format("transaction with ref '%d' doesnt exist", txnId)));
  }

  /**
   * this operations registers a transaction on behalf of player debit/credits, it also validates if
   * a player has insufficient funds if a debit is to be made
   */
  @Override
  @Transactional
  public Wallet createTransaction(Wallet wallet)
      throws WalletException, InsufficientBalanceException {
    // checks for transaction id to ensure its uniqueness
    if (walletRepository.getTransactionById(wallet.getTransactionId()).isPresent()) {
      throw new WalletException("transaction Id has been used ");
    }
    if(!(wallet.getTranscationType().equalsIgnoreCase("debit") || wallet.getTranscationType().equalsIgnoreCase("credit"))) {
      throw new WalletException("Only debit and credit transactions are supported.");
    }
    BigDecimal balance = walletRepository.getBalance(wallet.getAccount().getId());
    if ("debit".equalsIgnoreCase(wallet.getTranscationType())
        && (wallet.getAmount().compareTo(BigDecimal.ZERO) == 1
            || wallet.getAmount().compareTo(BigDecimal.ZERO) == 0)) {
      wallet.setAmount(wallet.getAmount().negate());
    }
    if (balance.add(wallet.getAmount()).compareTo(BigDecimal.ZERO) == 1
        || balance.add(wallet.getAmount()).compareTo(BigDecimal.ZERO) == 0) {
      return walletRepository.save(wallet);
    }

    throw new InsufficientBalanceException(
        String.format("player's balance is %.2f and cannot perform a transaction of %.2f ",
            balance.doubleValue(), wallet.getAmount().doubleValue()));

  }

  @Override
  public Wallet update(Wallet wallet, Long id) throws WalletException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Wallet> getList() {
    return Lists.newArrayList(walletRepository.findAll());
  }

  @Override
  public List<Wallet> transactionsByAccountID(Long accountId) {
    return walletRepository.getTransactionsForPlayer(accountId);
  }

  @Override
  public BigDecimal balanceByAccountID(Long accountId) {
    return walletRepository.getBalance(accountId);
  }

  @Override
  public List<Wallet> transactions() {
    return Lists.newArrayList(walletRepository.findAll());
  }

  @Override
  public Wallet save(Wallet wallet) throws WalletException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
