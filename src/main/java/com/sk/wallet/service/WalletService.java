package com.sk.wallet.service;

import com.sk.wallet.dataobject.Wallet;
import com.sk.wallet.exception.InsufficientBalanceException;
import com.sk.wallet.exception.WalletException;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author sdagur
 */
public interface WalletService extends PlayerService<Wallet>{
    List<Wallet> transactionsByAccountID(Long accountId)  throws WalletException;
    Wallet transactionById(Long txnId)  throws WalletException;
    /**
     *
     * @param accountId
     * @return
     * @throws com.sk.wallet.exception.WalletException
     */
    BigDecimal balanceByAccountID(Long accountId)  throws WalletException;
    List<Wallet> transactions();
    Wallet createTransaction(Wallet wallet) throws WalletException,InsufficientBalanceException;
}
