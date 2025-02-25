import java.util.*;

public class FixedDeposit extends Deposit{
	DepositDAO depositDAO;
    DepositDTO depdto;
    Scanner sc = new Scanner(System.in);
    double finalRate; // 최종 이자율
    double totalInterest; // 총 이자액
    double totalAmount; // 총 수령액

    public FixedDeposit(DepositDAO depositDAO, String productName, double baseInterestRate, double addInterest, int money, int period) {
        super(productName, baseInterestRate, addInterest, money, period);
        this.depositDAO = depositDAO;
    }
	
	public FixedDeposit(){
        super();
    };

	// 결과 출력
	void showPrint() {
        System.out.println("납입 개월 수: " + period + "개월");
        System.out.printf("납입액 : %,d 원\n", money);
		System.out.printf("최종 이자율 : %.2f%%, (적용 우대 금리 : %.2f%%)\n", finalRate, addInterest);
        System.out.printf("총 이자 : %,d 원\n", Math.round(totalInterest));
        System.out.printf("총 수령액 (세전) : %,d 원\n", Math.round(totalAmount));
	}

	// 우대 금리 조건들 플로팅
	double getBonusInterest(String msg, double bonusInterest) {
		while (true) { 
			System.out.print(msg + " (1: 예, 0: 아니오): ");
			//2회차 이상 실행시 버퍼 문제 때문에 nextInt 에서 nextLine으로 변경
			String input = sc.nextLine().trim();
			  if (input.equals("1")) {
				return bonusInterest;
			} else if (input.equals("0")) {
				return 0;
			} else {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	// 신한 my 플러스 정기예금
	double myPlusfixedDeposit(){
		List<DepositDTO> myPlusfixedDeposit = depositDAO.getDepositsByName("신한 My플러스 정기예금");
		while(true) {
            try {
                System.out.print("선택 (번호 입력): ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= myPlusfixedDeposit.size()) {
                    depdto = myPlusfixedDeposit.get(choice-1);
                    break;
                } else {
                    System.out.println("올바른 번호를 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }
		period = depdto.period;
        baseInterestRate = depdto.getInterest();
        addInterest = 0.0;  // 우대 금리 초기화
		
		while (true) {
            // 2. 월 납입액 (최소 500,000원, 최대 100,000,000원)
            try {
                System.out.print("납입액 (500,000원 이상, 100,000,000원 이하): ");
				// 빈칸으로 입력받았을 때 에러메세지를 내보내기 위해 스트링 형 변수를 하나 추가
				String moneyStr = sc.nextLine().trim(); 
				if(moneyStr.isEmpty()){
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				}
				money = Integer.parseInt(moneyStr); // 스트링 변수를 Int형으로....

                if (money < 500000) {
                    System.out.println("최소 500,000원 이상으로 입력해주세요.");
				} else if (money > 100000000) {
                    System.out.println("최대 100,000,000원까지 가능합니다.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {  
                System.out.println("숫자만 입력해주세요."); 
			}
        }

		addInterest += getBonusInterest("① 예금 신규 직전 6개월간 정기예금 미보유 고객 입니까? (+0.1%)", 0.1);
        addInterest += getBonusInterest("② 예금 보유기간중 50만원 이상 소득입금 발생시? (+0.1%)", 0.1);
		
		finalRate = baseInterestRate + addInterest;

		totalInterest = 0;
		totalInterest = money * (finalRate/100.0) * (period/12.0);
		totalAmount = money + totalInterest;
		
		System.out.println("\n===== 신한 My플러스 정기예금 이자 계산 결과 =====");
        showPrint();
        return totalAmount;
	}
		//쏠 편한 정기예금
    double solEasyfixedDeposit(){
		List<DepositDTO> myPlusfixedDeposit = depositDAO.getDepositsByName("쏠 편한 정기예금");

		while(true) {
            try {
                System.out.print("선택 (번호 입력): ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= myPlusfixedDeposit.size()) {
                    depdto = myPlusfixedDeposit.get(choice-1);
                    break;
                } else {
                    System.out.println("올바른 번호를 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }
		period = depdto.period;
        baseInterestRate = depdto.getInterest();
        addInterest = 0.0;  // 우대 금리 초기화
		
		while (true) {
            // 2. 월 납입액 (최소 500,000원, 최대 100,000,000원)
            try {
                System.out.print("납입액 (10,000원 이상): ");
				// 빈칸으로 입력받았을 때 에러메세지를 내보내기 위해 스트링 형 변수를 하나 추가
				String moneyStr = sc.nextLine().trim(); 
				if(moneyStr.isEmpty()){
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				}
				money = Integer.parseInt(moneyStr); // 스트링 변수를 Int형으로....

                if (money < 10000) {
                    System.out.println("최소 10,000원 이상으로 입력해주세요.");
				} else {
                    break;
                }
            } catch (NumberFormatException e) {  
                System.out.println("숫자만 입력해주세요."); 
			}
        }
		
		finalRate = baseInterestRate;

		totalInterest = 0;
		totalInterest = money * (finalRate/100.0) * (period/12.0);
		totalAmount = money + totalInterest;
		
		System.out.println("\n===== 쏠 편한 정기예금 이자 계산 결과 =====");
        showPrint();
        return totalAmount;
	}
	
	double youthLeafDeposit() { return 0; }
    double youthSavingDeposit() { return 0; }
    double greenGrapesDeposit() { return 0; }
    double alSolDeposit() { return 0; }
    double smartDeposit() { return 0; }
    double sDreamDeposit() { return 0; }
}

