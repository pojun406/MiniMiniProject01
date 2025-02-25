public abstract class Deposit {
    protected String productName;   // 상품명
    protected double baseInterestRate;  // 기본 금리
    protected int period;           // 기간
    protected double[] dInterest;   // 기간별 기본 금리
    protected double addInterest;   // 우대 금리
    protected int money;

    public Deposit(String productName, double baseInterestRate, double addInterest, int money, int period) {
    }

    public Deposit() {
    }


    // 각 금융 상품에 대한 이율 계산 메서드들
    abstract double youthLeafDeposit(); // 청년도약계좌 이율 계산
    abstract double youthSavingDeposit(); // 청년 처음 적금 이율 계산
    abstract double greenGrapesDeposit(); // 청포도 청년적금 이율 계산
    abstract double alSolDeposit(); // 알.쏠.적금 이율 계산
    abstract double smartDeposit(); // 신한 스마트 적금 이율 계산
    abstract double sDreamDeposit(); // 신한 S드림 적금 이율 계산
    abstract double myPlusfixedDeposit();// 신한 My플러스 정기예금 이율 계산 예시
    abstract double solEasyfixedDeposit(); // 쏠 편한 정기예금 이율 계산 예시
}