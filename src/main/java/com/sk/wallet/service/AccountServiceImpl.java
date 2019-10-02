package com.sk.wallet.service;

import com.google.common.collect.Lists;

import com.sk.wallet.dataaccessobject.AccountRepository;
import com.sk.wallet.dataobject.Account;
import com.sk.wallet.exception.WalletException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sdagur this is an implementation of the account/player service interface since it is the
 *         only implementation no qualifier is defined for the service bean
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public Account accountByPK(Long accountId) throws WalletException {
    return accountRepository.findById(accountId).orElseThrow(
        () -> new WalletException(String.format("account with '%d' not found ", accountId)));
  }

  /**
   * this operations registers a player and creates and account for him/her with minimal details
   */
  @Override
  @Transactional
  public Account save(Account account) throws WalletException {
    if (account.getPlayerName() != null) {
      if (account.getPlayerName().length() < 5) {
        throw new WalletException("player name is should be 5 characters of more");
      }
      return accountRepository.save(account);
    }
    throw new WalletException("player name is mandatory");
  }

  /**
   * this operation updates a players account information and checks for concurrent player
   * modification
   */
  @Override
  @Transactional
  public Account update(Account account, Long accountId) throws WalletException {

    Account accountToupdate = accountRepository.findById(accountId).orElseThrow(
        () -> new WalletException(String.format("account with '%d' not found ", accountId)));
    if (account.getPlayerName() == null || account.getPlayerName().isEmpty()) {
      throw new WalletException("player name is mandatory");
    }
    if (account.getPlayerName().length() < 5) {
      throw new WalletException("player name is should be 5 characters of more");
    }
    accountToupdate.setPlayerName(account.getPlayerName());
    accountToupdate.setSex(account.getSex());
    try {
      return accountRepository.save(accountToupdate);
    } catch (ObjectOptimisticLockingFailureException e) {
      throw new WalletException("refresh your page to get updated players");
    }
  }

  /**
   * this operation gets all account lists and their respective wallet transactions
   */
  @Override
  public List<Account> getList() {
    return Lists.newArrayList(accountRepository.findAll());
  }

  /**
   * Delete player account
   */
  @Override
  public void delete(Account account) {
    accountRepository.delete(account);

  }

}
