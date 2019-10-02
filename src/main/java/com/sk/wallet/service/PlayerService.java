package com.sk.wallet.service;

import com.sk.wallet.exception.WalletException;

import java.util.List;

/**
 *
 * @author sdagur
 * @param <T>
 * This is a unity interface implemented by all factory interfaces
 * it defines abstract common method operations
 */
public interface PlayerService<T> {
    T save(T t) throws WalletException;
    T update(T t,Long id) throws WalletException ;
    List<T> getList();
}
