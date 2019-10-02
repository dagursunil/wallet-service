package com.sk.wallet.dataaccessobject;

import com.sk.wallet.dataobject.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * @author sdagur
 *
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(nativeQuery = true, value = "select * from wallet where transaction_id = ?")
    Optional<Wallet> getTransactionById(Long txnId);

    @Query(nativeQuery = true, value = "select ifnull(sum(amount),0.00) from wallet where account_id = ?")
    BigDecimal getBalance(Long accountId);

    @Query(nativeQuery = true, value = "select * from wallet where account_id = ?")
    List<Wallet> getTransactionsForPlayer(Long accountId);
    
}
