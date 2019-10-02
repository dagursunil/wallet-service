package com.sk.wallet.service;

import com.sk.wallet.dataobject.Account;
import com.sk.wallet.exception.WalletException;

/**
 * 
 * @author sdagur
 *
 */
public interface AccountService extends  PlayerService<Account> {
   Account accountByPK(Long accountId) throws WalletException;

  void delete(Account account);
}
