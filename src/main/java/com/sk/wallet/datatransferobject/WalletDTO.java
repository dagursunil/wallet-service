package com.sk.wallet.datatransferobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author sdagur
 *
 */
public class WalletDTO {

    private Long id;
    private Long accountId;
    @NotNull(message="Amount can not be null")
    private BigDecimal amount;
    private String transcationType;
    private Date transactionDate;
    @NotNull(message="Transaction Id can not be null")
    private Long transactionId;

    public Long getTransactionId() {
      return transactionId;
    }

    public void setTransactionId(Long transactionId) {
      this.transactionId = transactionId;
    }

    public WalletDTO() {
    }

    public WalletDTO(WalletDTOBuilder builder) {
        id = builder.id;
        accountId = builder.accountId;
        amount = builder.amount;
        transcationType = builder.transcationType;
        transactionDate = builder.transactionDate;
        transactionId = builder.transactionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTranscationType() {
        return transcationType;
    }

    public void setTranscationType(String transcationType) {
        this.transcationType = transcationType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

     public static class WalletDTOBuilder {

        private Long id;
        private Long accountId;
        private BigDecimal amount;
        private String transcationType;
        private Date transactionDate;
        private Long transactionId;

        public WalletDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public WalletDTOBuilder setAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public WalletDTOBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public WalletDTOBuilder setTranscationType(String transcationType) {
            this.transcationType = transcationType;
            return this;
        }

        public WalletDTOBuilder setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public WalletDTOBuilder setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public WalletDTO build() {
            return new WalletDTO(this);
        }

    }

}
