package stream.api.adapter.com.unibank.request;

import stream.api.adapter.com.unibank.mhm.Loan;

/**
 * Created by AzarM on 5/4/2018.
 */
public class MHMStatus {
    Loan loan;

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
