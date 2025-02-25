public class DepositDTO {
    protected String productName;	// 상품명
    protected int period;			// 상품 기간
    protected double interest;      // 금리


    public DepositDTO(String productName, int period, double interest){
        this.productName = productName;
        this.period = period;
        this.interest = interest;
    }
    DepositDTO(){}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
		this.period = period;
	}

};