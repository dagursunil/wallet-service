package com.sk.wallet.service;

import static org.junit.Assert.assertNotNull;

import com.sk.wallet.dataobject.Account;
import com.sk.wallet.exception.WalletException;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author sdagur
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    /**
     * quickly setup a test account to do
     * out test with 
     */
    @Before
    public  void setupAccount() throws WalletException {
        accountService.save(new Account(1L, "Sunil", "M", new Date()));
    }

    @Test
    public void findPlayerByValidPrimaryKey() throws WalletException {
        Account account = accountService.accountByPK(1L);
        assertNotNull(account);
        Assert.assertEquals("Sunil", account.getPlayerName());
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithEmptyPlayerName() throws WalletException {
        accountService.save(new Account(1L, "", "M", new Date()));
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithPlayerNameLessThan5Characters() throws WalletException {
        accountService.save(new Account(1L, "", "M", new Date()));
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithNullPlayerName() throws WalletException {
        accountService.save(new Account(1L, null, "M", new Date()));
    }

    @Test
    public void createPlayerWithValidDetails() throws WalletException {
        accountService.save(new Account(2L, "John Doe", "M", new Date()));
    }

}
