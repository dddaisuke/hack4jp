package jp.hack4jp.donatter.model.vo;

import java.io.Serializable;

public class BloodAmount implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1109651907878996342L;

    private String type = "A";
    private long amount = 0;

    private static final String[] TYPES = new String[] { "A", "B", "O", "AB" };

    public BloodAmount() {
    }

    public BloodAmount(int type, long amount) {
        this.type = TYPES[type];
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void add(long a) {
        this.amount += a;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

}
