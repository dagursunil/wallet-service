package com.sk.wallet.dataaccessobject;

import static org.junit.Assert.assertEquals;

import com.sk.wallet.dataobject.Account;
import com.sk.wallet.datatransferobject.AccountDTO;
import com.sk.wallet.datatransferobject.mapper.AccountMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 
 * @author sdagur
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testConcurrentAccountNameWriting() {
        // concurrent users access same resource at same time
        Account accountForUser1 = accountRepository.findById(1000L).get();
        Account accountForUser2 = accountRepository.findById(1000L).get();

        
        //transforming data object to dto on the view 
        AccountDTO accountDTOForUser1 = AccountMapper.doToDTO(accountForUser1);
        AccountDTO accountDTOForUser2 = AccountMapper.doToDTO(accountForUser2);
        
        
        //concurrent users modifying a field name concurrently 
        accountDTOForUser1.setPlayerName("jane doe");
        accountDTOForUser2.setPlayerName("jon doe");
        
        //transforming data access object back to a data object
        Account preparedForUpdate1 = AccountMapper.dtoToDO(accountDTOForUser1);
        Account preparedForUpdate2 = AccountMapper.dtoToDO(accountDTOForUser2);

        assertEquals(0, accountForUser1.getVersion());
        assertEquals(0, accountForUser2.getVersion());

        accountRepository.save(preparedForUpdate1);
        //ObjectOptimisticLockingFailureException expected here 
        accountRepository.save(preparedForUpdate2);

    }

}
