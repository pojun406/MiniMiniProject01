import java.util.*;

public class SavingDeposit extends Deposit {
    DepositDAO depositDAO;
    DepositDTO depdto;
    Scanner sc = new Scanner(System.in);
    double finalRate; // 최종 이자율
    double totalInterest; // 총 이자액
    double totalAmount; // 총 수령액
    public SavingDeposit(DepositDAO depositDAO, String productName, double baseInterestRate, double addInterest, int money, int period) {
        super(productName, baseInterestRate, addInterest, money, period);
        this.depositDAO = depositDAO;
    }

    public SavingDeposit() {
        super();
    };

	// 청년 적금들 나이 체크
    boolean chkAge(int minAge, int maxAge) {
        while(true) {
            System.out.print("당신의 나이를 입력해 주세요 : ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("나이를 입력해주세요. 공란일 수 없습니다.");
                continue;
            }

            try {
                int age = Integer.parseInt(input);
                if (age < minAge || age > maxAge) {
                    System.out.println("가입조건에 부합하지 않습니다.");
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력해주세요.");
            }
        }
    }

	// 결과 출력
    void showPrint() {
        System.out.println("납입 개월 수: " + period + "개월");
        System.out.printf("월 납입액 : %,d 원\n", money);
        System.out.println("최종 이자율 : " + finalRate + "%, (적용 우대 금리 : " + addInterest + "%)");
        System.out.printf("총 이자 : %,d 원\n", Math.round(totalInterest));
        System.out.printf("총 수령액 (세전) : %,d 원\n", Math.round(totalAmount));
    }

	// 우대 금리 조건들 플로팅
	double getBonusInterest(String msg, double bonusInterest) {
		while (true) { 
			System.out.print(msg + " (1: 예, 0: 아니오): ");
			//2회차 이상 실행시 버퍼 문제 때문에 nextInt 에서 nextLine으로 변경
			String condition = sc.nextLine().trim();
			  if (condition.equals("1")) {
				return bonusInterest;
			} else if (condition.equals("0")) {
				return 0;
			} else {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}
	
	// 청년도약계좌
    double youthLeafDeposit() {
        List<DepositDTO> youthLeafDeposits = depositDAO.getDepositsByName("청년도약계좌");

        depdto = youthLeafDeposits.get(0);

        period = depdto.period;
        baseInterestRate = depdto.getInterest();
        addInterest = 0.0;  // 우대 금리 초기화

        System.out.println("청년도약계좌 가입 조건을 확인합니다.");

        if (!chkAge(19, 34)){ return 0; }

        System.out.println("청년도약계좌 가입 정보를 입력하세요.");
        System.out.println("납입 개월 수는 60개월 입니다: ");
        System.out.print("월 납입액 (최대 70만원): ");
        
		while (true) {
            // 2. 월 납입액 (최소 1천 원, 최대 700,000)
            try {
                System.out.print("월 납입액 (1,000원 이상, 700,000원 이하): ");
				// 빈칸으로 입력받았을 때 에러메세지를 내보내기 위해 스트링 형 변수를 하나 추가
				String moneyStr = sc.nextLine().trim(); 
				if(moneyStr.isEmpty()){
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				}
				money = Integer.parseInt(moneyStr); // 스트링 변수를 Int형으로....

                if (money < 1000) {
                    System.out.println("최소 1,000원 이상으로 입력해주세요.");
				} else if (money > 700000) {
                    System.out.println("최대 700,000원까지 가능합니다.");
                } else {
                    break;
                }
			// 최초 입력을 스트링 형으로 받아서 숫자가 입력이 안되었을 때를 거르기 위해. 원래 코드로 복귀
            } catch (NumberFormatException e) {  
                System.out.println("숫자만 입력해주세요."); 
			}
        }


        // 우대 조건 입력 받기 (1, 0 이외에 값을 넣으면 다시 질문하도록 수정하기) 최종.이걸로 교체
        addInterest += getBonusInterest("① 급여이체 실적이 있습니까?(건당 50만원, 30개월 이상) (+0.3%)", 0.3);
        addInterest += getBonusInterest("② 신한카드 결제 실적이 있습니까? (30개월 이상) (+0.3%)", 0.3);
        addInterest += getBonusInterest("③ 저소득 청년(연 소득2400만원 이하)입니까? (+0.5%)", 0.5);
        addInterest += getBonusInterest("④ 최근 1년간 신한은행 정기예금, 정기적금, 청약 보유 이력이 없습니까? (+0.4%)", 0.4);

        finalRate = baseInterestRate + addInterest;

        totalInterest = 0;
        for (int i = 1; i <= period; i++) {
            double monthlyInterest = money * (finalRate / 100 / 12) * (period - i + 1);
            totalInterest += monthlyInterest;
        }

        totalAmount = (money * period) + totalInterest;

        System.out.println("\n===== 청년도약계좌 이자 계산 결과 =====");
        showPrint();
        return totalAmount;
    }
	
	// 청년우대적금
    double youthSavingDeposit() {
       List<DepositDTO> youthSavingDeposits = depositDAO.getDepositsByName("청년우대적금");

        depdto = youthSavingDeposits.get(0);  // result 리스트에 인덱스값을 지정해서 그에 따른 개월 수, 금리를 반환받음

        period = depdto.period;
        baseInterestRate = depdto.getInterest();
        addInterest = 0.0; // 우대 금리 초기화

        System.out.println("청년우대적금 가입 조건을 확인합니다.");
        if (!chkAge(19, 39)) { return 0; }

        System.out.println("청년우대적금 가입 정보를 입력하세요.");

        while (true) {
            // 2. 월 납입액 (최대 300,000)
            try {
                System.out.print("월 납입액 (300,000원 이하): ");
				// 빈칸으로 입력받았을 때 에러메세지를 내보내기 위해 스트링 형 변수를 하나 추가
				String moneyStr = sc.nextLine().trim(); 
				if(moneyStr.isEmpty()){
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				}
				money = Integer.parseInt(moneyStr); // 스트링 변수를 Int형으로....

                if (money > 300000) {
                    System.out.println("최대 300,000원까지 가능합니다.");
		        } else {
                    break;
                }
			// 최초 입력을 스트링 형으로 받아서 숫자가 입력이 안되었을 때를 거르기 위해. 원래 코드로 복귀
            } catch (NumberFormatException e) {  
                System.out.println("숫자만 입력해주세요."); 
			}
        }

        // 우대 조건 입력 받기 (1, 0 이외에 값을 넣으면 다시 질문하도록 수정하기) 최종.이걸로 교체
        addInterest += getBonusInterest("① 급여이체 실적이 있습니까? (+1.0%)", 1.0);
        addInterest += getBonusInterest("② 신한카드 결제 실적이 있습니까? (+0.5%)", 0.5);
        addInterest += getBonusInterest("③ 신한 슈퍼SOL 앱 회원가입 했습니까? (+0.5%)", 0.5);
        addInterest += getBonusInterest("④ 최근 1년간 신한은행 정기예금, 정기적금, 청약 보유 이력이 없습니까? (+1.0%)", 1.0);

        finalRate = baseInterestRate + addInterest;
        if (finalRate > 6.3) finalRate = 6.3; // 최대 금리 제한

        totalInterest = 0;
        // 총 이자 계산 (월복리 X, 단순 합산)
        for (int i = 1; i <= period; i++) {
            totalInterest += (money * (finalRate / 100) / 12 * (period - i + 1));
        }

        totalAmount = (money * period) + totalInterest;

        System.out.println("\n===== 청년우대적금 이자 계산 결과 =====");
        showPrint();

        return totalAmount;
    }
   
    double greenGrapesDeposit() {
        List<DepositDTO> greenGrapesDeposits = depositDAO.getDepositsByName("청포도 청년적금");

        while(true) {
            try {
                System.out.print("선택 (번호 입력): ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= greenGrapesDeposits.size()) {
                    depdto = greenGrapesDeposits.get(choice-1);
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
        addInterest = 0.0; // 우대 금리 초기화

        System.out.println("청포도 청년적금 가입 조건을 확인합니다.");

        if (!chkAge(19, 39)) { return 0; }

        while (true) {
            // 2. 월 납입액 (최대 300,000)
            try {
                System.out.print("월 납입액 (300,000원 이하): ");
				// 빈칸으로 입력받았을 때 에러메세지를 내보내기 위해 스트링 형 변수를 하나 추가
				String moneyStr = sc.nextLine().trim(); 
				if(moneyStr.isEmpty()){
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				}
				money = Integer.parseInt(moneyStr); // 스트링 변수를 Int형으로....

                if (money > 300000) {
                    System.out.println("최대 300,000원까지 가능합니다.");
		 
                } else {
                    break;
                }
			// 최초 입력을 스트링 형으로 받아서 숫자가 입력이 안되었을 때를 거르기 위해. 원래 코드로 복귀
            } catch (NumberFormatException e) {  
                System.out.println("숫자만 입력해주세요."); 
			}
        }

        // 우대 조건 입력 받기 (1, 0 이외에 값을 넣으면 다시 질문하도록 수정하기) 최종.이걸로 교체
        addInterest += getBonusInterest("① 성품 서비스 안내 수단 전체 동의여부(가입시)? (+0.1%)", 0.1);
        addInterest += getBonusInterest("② 계약 기간의 1/2 이상 당사 계좌에서 이체로 불입여부 (+0.3%)", 0.3);
        addInterest += getBonusInterest("③ 통장 미발행 (ESG 실천) (+0.6%)", 0.6);

        finalRate = baseInterestRate + addInterest;
        if (finalRate > 5.0) finalRate = 5.0; // 최대 금리 제한

        totalInterest = 0;
        // 총 이자 계산 (월복리 X, 단순 합산)
        for (int i = 1; i <= period; i++) {
            totalInterest += (money * (finalRate / 100) / 12 * (period - i + 1));
        }

        totalAmount = (money * period) + totalInterest;

        // 결과 출력
        System.out.println("\n===== 청포도 청년적금 이자 계산 결과 =====");
        showPrint();

        return totalAmount;
    }
 
    double alSolDeposit() {
        List<DepositDTO> alSolDeposits = depositDAO.getDepositsByName("알.쏠.적금");

        while (true) {
            try {
                System.out.print("선택 (번호 입력): ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= alSolDeposits.size()) {
                    depdto = alSolDeposits.get(choice - 1);  // result 리스트에 인덱스값을 지정해서 그에 따른 개월 수, 금리를 반환받음
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
        addInterest = 0.0; // 우대 금리 초기화

        while (true) {
            // 2. 월 납입액 (최소 1천 원, 최대 300만원)
            try {
                System.out.print("월 납입액 (1,000원 이상 3,000,000원 이하): ");
                String moneyStr = sc.nextLine().trim();
				if(moneyStr.isEmpty()) {
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				} 
				money = Integer.parseInt(moneyStr);
				if (money < 1000) {
                    System.out.println("최소 1,000원 이상으로 입력해주세요.");	
                } else if (money > 3000000) {
                    System.out.println("최대 3,000,000원까지 가능합니다.");
                } else {
                    break;
                }
				
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }

        System.out.println("최대 우대 금리 : 1.3%");
        // 우대 조건 입력 받기 (1, 0 이외에 값을 넣으면 다시 질문하도록 수정하기) 최종.이걸로 교체
        addInterest += getBonusInterest("① 소득이체 신한은행 입출금 통장으로 50만원 이상 입금 발생하신적 있습니까? (+0.6%)", 0.6);
        addInterest += getBonusInterest("② 신한카드 카드 이용 내역이 있습니까? (+0.3%)", 0.3);
        addInterest += getBonusInterest("③ 오픈뱅킹 (오픈뱅킹 출금이체를 통해 다른 은행 계좌에서 입금한 경우)을 한 적이 있습니까? (+0.6%)", 0.6);
        addInterest += getBonusInterest("④ 청약을 보유(신한은행 주택청약 상품 보유)하고 계십니까? (+0.3%)", 0.3);
        addInterest += getBonusInterest("⑤ 마케팅 동의 하십니까? (+0.1%)", 0.1);

        // 최종 이자율 계산
        if (addInterest > 1.3) { addInterest = 1.3; }// 우대 금리 최대 제한
        finalRate = baseInterestRate + addInterest;

        totalInterest = 0;
        // 총 이자 계산 (월복리 X, 단순 합산) / (월 납입 액 * (최종 이자율 / 100) / 12개월 기준 * (남은 기간))
        for (int i = 1; i <= period; i++) {
            totalInterest += (money * (finalRate / 100) / 12 * (period - i + 1));
        }

        // 총 수령액 계산
        totalAmount = (money * period) + totalInterest;

        // 결과 출력
        System.out.println("\n===== 알.쏠.적금 이자 계산 결과 =====");
        showPrint();

        return totalAmount;
    }

	// 스마트적금
	double smartDeposit () {	
        List<DepositDTO> smartDeposits = depositDAO.getDepositsByName("신한 스마트적금");

        depdto = smartDeposits.get(0);  // result 리스트에 인덱스값을 지정해서 그에 따른 개월 수, 금리를 반환받음

        period = depdto.period;
        baseInterestRate = depdto.getInterest();
        addInterest = 0.0;

        while (true) {
            // 2. 월 납입액 (최소 1천 원, 최대 100만원)
            try {
                System.out.print("월 납입액 (1,000원 이상 1,000,000원 이하): ");
                String moneyStr = sc.nextLine().trim();
				if(moneyStr.isEmpty()) {
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				} 
				money = Integer.parseInt(moneyStr);
                if (money < 1000) {
                    System.out.println("최소 1,000원 이상으로 입력해주세요.");
                } else if (money > 1000000) {
                    System.out.println("최대 1,000,000원까지 가능합니다.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }

        // 우대 조건 입력 받기 (우대 조건 없음)
        System.out.println("우대 조건 X");

        // 최종 이자율 계산
        finalRate = baseInterestRate + addInterest;

        totalInterest = 0;
        // 총 이자 계산 (월복리 X, 단순 합산) / (월 납입 액 * (최종 이자율 / 100) / 12개월 기준 * (남은 기간))
        for (int i = 1; i <= period; i++) {
            totalInterest += (money * (finalRate / 100) / 12 * (period - i + 1));
        }

        // 총 수령액 계산
        totalAmount = (money * period) + totalInterest;
        System.out.println("\n===== 신한 스마트적금 이자 계산 결과 =====");
        showPrint();
        return totalAmount;
	}
	

	// 신한 s드림 적금
	double sDreamDeposit () {
		List<DepositDTO> sDreamDeposits = depositDAO.getDepositsByName("신한 S드림적금");

        while (true) {
            try {
                System.out.print("선택 (번호 입력): ");
                int choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= sDreamDeposits.size()) {
                    depdto = sDreamDeposits.get(choice - 1);  // result 리스트에 인덱스값을 지정해서 그에 따른 개월 수, 금리를 반환받음
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
        addInterest = 0.0;      // 우대 금리 초기화

        while (true) {
            // 2. 월 납입액 (최소 1천 원, 최대 제한 X)
            try {
                System.out.print("월 납입액 (1,000원 이상): ");
                String moneyStr = sc.nextLine().trim();
				if(moneyStr.isEmpty()) {
					System.out.println("다시 입력해 주세요");
					continue; // 공란 포함 빈칸 입력 받았을 때를 위한 if문
				} 
				money = Integer.parseInt(moneyStr);
                if (money < 1000) {
                    System.out.println("최소 1,000원 이상으로 입력해주세요.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
            }
        }

        System.out.println("최대 우대 금리 : 0.4%, 가입기간 12개월 미만일 경우 우대 금리 X");
        if (period >= 12) {
            // 우대 조건 입력 받기 (1, 0 이외에 값을 넣으면 다시 질문하도록 수정하기) 최종.이걸로 교체
            addInterest += getBonusInterest("① 월말일자로 정기예금 잔액 3백만원 이상의 보유실적이 \n" +
                    "1회 이상인 경우(마이홈플랜 청약예금은 제외) 있습니까? (+0.2%)", 0.2);
            addInterest += getBonusInterest("② 마이홈플랜청약부금,청약저축,주택청약종합저축, 청약예금 \n" +
                    "월말잔액 30만원 이상보유실적 1회 이상인 적이 있습니까? (+0.2%)", 0.2);
            addInterest += getBonusInterest("③ 적금상품(청약제외) 만기해지 후 3개월 내 이 적금이 신규가 맞습니까? (+0.2%)", 0.2);
            if (money >= 300000) {
                System.out.println("④ 30만원 이상 납입자 예정");
                addInterest += getBonusInterest("④ 30만원 이상 신규하는 경우십니까? (+0.1%)", 0.1);
            }
            addInterest += getBonusInterest("⑤ 비대면 채널을 통해 신규 하십니까? (+0.1%)", 0.1);
            addInterest += getBonusInterest("⑥ 모범납세자 및 마을 세무사 십니까? (+0.2%)", 0.2);
        }
        
        // 가입 기간이 12개월 미만인 경우 우대 금리 X
        if (period < 12) { addInterest = 0; }

        // 최종 이자율 계산
        if (addInterest > 0.4) { addInterest = 0.4; } // 우대 금리 최대 제한
        finalRate = baseInterestRate + addInterest;

        totalInterest = 0;
        // 총 이자 계산 (월복리 X, 단순 합산) / (월 납입 액 * (최종 이자율 / 100) / 12개월 기준 * (남은 기간))
        for (int i = 1; i <= period; i++) {
            totalInterest += (money * (finalRate / 100) / 12 * (period - i + 1));
        }

        // 총 수령액 계산
        totalAmount = (money * period) + totalInterest;

        // 결과 출력
        System.out.println("\n===== 신한 S드림적금 이자 계산 결과 =====");
        showPrint();

        return totalAmount;
	}

	double myPlusfixedDeposit () { return 0; } // 예금 상품    

	double solEasyfixedDeposit () { return 0; } // 예금 상품

}



