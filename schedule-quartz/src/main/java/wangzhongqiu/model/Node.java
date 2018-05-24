package wangzhongqiu.model;

/**
 * 
 */
public class Node {
    private String key;
    private Integer count;
    private String str;
    private String amount;
    private String detailId;
    private String loanId;
    private String subPointId;
    private String notes;
    private String toUserId;

    public Node(String key, Integer count, String str, String amount) {
        this.key = key;
        this.count = count;
        this.str = str;
        this.amount = amount;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getSubPointId() {
        return subPointId;
    }

    public void setSubPointId(String subPointId) {
        this.subPointId = subPointId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public boolean equals(Object other) {
        if(!(other instanceof Node)) {
            return false;
        }

        Node o = (Node) other;

        if(key == null) {
            return false;
        }

        return key.equals(o.getKey());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.detailId).append("\t")
                .append(this.amount).append("\t")
                .append(this.loanId).append("\t")
                .append(this.notes).append("\t")
                .append(this.subPointId).append("\t")
                .append(this.toUserId).append("\t")
                .append(this.count).append("\t");
        return str.toString();
    }
}
