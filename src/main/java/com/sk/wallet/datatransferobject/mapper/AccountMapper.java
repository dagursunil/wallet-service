package com.sk.wallet.datatransferobject.mapper;
import com.sk.wallet.dataobject.Account;
import com.sk.wallet.datatransferobject.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 * @author sdagur
 *
 */
public class AccountMapper {

    public static Account dtoToDO(AccountDTO a) {
        Account account = new Account.AccountBuilder()
                .setDateCreated(new Date())
                .setId(a.getId())
                .setPlayerName(a.getPlayerName())
                .setSex(a.getSex())
                .build();
        return account;
    }

    public static AccountDTO doToDTO(Account a) {
        double balance = a.getTransactions().stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        AccountDTO dto = new AccountDTO.AccountDTOBuilder().setId(a.getId())
                .setDateCreated(a.getDateCreated())
                .setPlayerName(a.getPlayerName())
                .setId(a.getId())
                .setSex(a.getSex())
                .setBalance(new BigDecimal(balance))
                .build();
        return dto;
    }

    public static List<AccountDTO> doToDTOList(List<Account> account) {
        return account.stream()
                .map(AccountMapper::doToDTO)
                .collect(Collectors.toList());
    }

}
