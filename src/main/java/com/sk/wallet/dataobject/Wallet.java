package com.sk.wallet.dataobject;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author sdagur
 *
 */
@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account account;
    @NotNull
    private BigDecimal amount;
    private String transcationType;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date transactionDate;
    @NotNull
    @Column(unique = true)
    private Long transactionId;;
    public Long getTransactionId() {
      return transactionId;
    }

    public void setTransactionId(Long transactionId) {
      this.transactionId = transactionId;
    }

    @Version //for concurrency
    private int version;

    public Wallet() {
    }

    public Wallet(Account account, BigDecimal amount, String transcationType, Date transactionDate, Long transactionId) {
        this.account = account;
        this.amount = amount;
        this.transcationType = transcationType;
        this.transactionDate = transactionDate;
        this.transactionId = transactionId;
    }
    
    

    public Wallet(WalletBuilder builder) {
        id = builder.id;
        account = new Account(builder.accountId);
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wallet other = (Wallet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public static class WalletBuilder {

        private Long id;
        private Long accountId;
        private BigDecimal amount;
        private String transcationType;
        private Date transactionDate;
        private Long transactionId;

        public WalletBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public WalletBuilder setAccount(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public WalletBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public WalletBuilder setTranscationType(String transcationType) {
            this.transcationType = transcationType;
            return this;
        }

        public WalletBuilder setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public WalletBuilder setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }

    }

}
