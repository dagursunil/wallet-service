package com.sk.wallet.dataaccessobject;

import com.sk.wallet.dataobject.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 
 * @author sdagur
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getByPlayerName(String name);
}
