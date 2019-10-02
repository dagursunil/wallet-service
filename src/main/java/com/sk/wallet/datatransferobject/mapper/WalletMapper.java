package com.sk.wallet.datatransferobject.mapper;

import com.sk.wallet.dataobject.Wallet;
import com.sk.wallet.datatransferobject.WalletDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author sdagur
 *
 */
public class WalletMapper {

    public static Wallet dtoToDO(WalletDTO w) {
        Wallet wallet = new Wallet.WalletBuilder()
                .setAccount(w.getAccountId())
                .setAmount(w.getAmount())
                .setId(w.getId())
                .setTranscationType(w.getTranscationType())
                .setTransactionDate(w.getTransactionDate())
                .setTransactionId(w.getTransactionId()).build();
        return wallet;
    }

    public static WalletDTO doToDTO(Wallet w) {
        WalletDTO walletDTO = new WalletDTO.WalletDTOBuilder()
                .setAccountId(w.getAccount().getId())
                .setAmount(w.getAmount())
                .setId(w.getId())
                .setTranscationType(w.getTranscationType())
                .setTransactionDate(w.getTransactionDate())
                .setTransactionId(w.getTransactionId()).build();
        return walletDTO;
    }

    public static List<WalletDTO> doToDTOList(List<Wallet> txns) {
        return txns.stream()
                .map(WalletMapper::doToDTO)
                .collect(Collectors.toList());
    }
}
