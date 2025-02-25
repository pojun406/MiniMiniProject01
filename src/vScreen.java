import java.util.*;

public class vScreen {
    UserAPI uv = new UserAPI();
    Scanner sc = new Scanner(System.in);
    DepositDAO depositDAO = new DepositDAO();
    SavingDeposit saved = new SavingDeposit(depositDAO, "", 0.0, 0.0, 0, 0);
    FixedDeposit fixed = new FixedDeposit(depositDAO, "", 0.0, 0.0, 0, 0);

    void showMenu() {
        while (true) {
            uv.mLine('=', 30);
            System.out.println(" ## BankDepositCalculator ##");
            uv.mLine('=', 30);

            System.out.println("1. 적금 상품 전체 조회");
            System.out.println("2. 예금 상품 전체 조회");

            System.out.println("\nQ. 종료");

            uv.mLine('-', 30);

            System.out.print("  ^ 메뉴를 선택 하세요 : ");

            String choice = sc.nextLine();

            if (choice.isEmpty()) {
                System.out.println("^Error Chk : Enter 입력됨 확인");
                continue;
            }

            if (choice.equalsIgnoreCase("Q")) {
                System.out.println("^ 시스템을 종료합니다");
                System.exit(0);
            }

            try {
                int option = Integer.parseInt(choice);
                if (option == 1) {  // 적금 상품 전체 기재
                    saveDeposit();
                } else if (option == 2) {   // 예금 상품 전체 기재
                    fixedDeposit();
                } else {
                    System.out.println("^ 1 ~ 2 사이의 메뉴번호 선택 하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("^ 1 ~ 2 사이의 메뉴번호 선택 하세요.");
            }
        }
    }

    void saveDeposit () { // 적금 선택 화면
        List<DepositDTO> productList = depositDAO.getAllDeposits();
        while (true) {
            uv.mLine('=', 30);
            /* 상품명 다 불러오기
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getProductName() +
                        " (기간 : " + productList.get(i).getPeriod() + "개월, 금리 : " + productList.get(i).getInterest() + "%)");
            }
             */
            System.out.println("적금 상품 선택");
            System.out.println("1. 청년도약계좌");
            System.out.println("2. 청년우대적금");
            System.out.println("3. 청포도 청년적금");
            System.out.println("4. 알쏠적금");
            System.out.println("5. 신한 스마트적금");
            System.out.println("6. 신한 S드림적금");
            System.out.println("Q. 이전 메뉴");
            uv.mLine('=', 30);
            System.out.print("원하는 상품을 선택하세요: ");

            String choice2 = sc.nextLine();

            if (choice2.equalsIgnoreCase("Q")) {
                return;
            }

            switch (choice2) {
                case "1":
                    System.out.println("청년도약계좌 가입 적금 가입 정보..");
                    List<DepositDTO> youthLeafDeposits = depositDAO.getDepositsByName("청년도약계좌");

                    if (youthLeafDeposits != null) {
                        for(int i = 0; i < youthLeafDeposits.size(); i++) {
                            DepositDTO depdto = youthLeafDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.youthLeafDeposit();
                    showMenu();
                    break;
                case "2":
                    System.out.println("청년우대적금 가입 적금 가입 정보.");
                    List<DepositDTO> youthSavingDeposits = depositDAO.getDepositsByName("청년우대적금");

                    if (youthSavingDeposits != null) {
                        for(int i = 0; i < youthSavingDeposits.size(); i++) {
                            DepositDTO depdto = youthSavingDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.youthSavingDeposit();
                    showMenu();
                    break;
                case "3":
                    System.out.println("청포도 청년적금 가입 희망 번호를 선택하세요.");
                    List<DepositDTO> greenGrapesDeposits = depositDAO.getDepositsByName("청포도 청년적금");

                    if (greenGrapesDeposits != null) {
                        for(int i = 0; i < greenGrapesDeposits.size(); i++) {
                            DepositDTO depdto = greenGrapesDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.greenGrapesDeposit();
                    showMenu();
                    break;
                case "4":
                    System.out.println("알.쏠.적금 가입 희망 번호를 선택하세요.");
                    List<DepositDTO> alSolDeposits = depositDAO.getDepositsByName("알.쏠.적금");

                    if (alSolDeposits != null) {
                        for(int i = 0; i < alSolDeposits.size(); i++) {
                            DepositDTO depdto = alSolDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.alSolDeposit();
                    showMenu();
                    break;
                case "5":
                    System.out.println("신한 스마트 적금 가입 정보.");
                    List<DepositDTO> smartDeposits = depositDAO.getDepositsByName("신한 스마트적금");

                    if (smartDeposits != null) {
                        for(int i = 0; i < smartDeposits.size(); i++) {
                            DepositDTO depdto = smartDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.smartDeposit();
                    showMenu();
                    break;
                case "6":
                    System.out.println("신한 S드림 적금 가입 희망 번호를 선택하세요.");
                    List<DepositDTO> sDreamDeposits = depositDAO.getDepositsByName("신한 S드림적금");

                    if (sDreamDeposits != null) {
                        for(int i = 0; i < sDreamDeposits.size(); i++) {
                            DepositDTO depdto = sDreamDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    saved.sDreamDeposit();
                    showMenu();
                    break;
                default:
                    System.out.println("^ 1 ~ 6 사이의 메뉴번호 선택 하세요.");
            }
        }
    }

    void fixedDeposit() { // 예금 선택 화면
        while (true) {
            uv.mLine('=', 30);
            System.out.println("예금 상품 선택");
            System.out.println("1. 신한 My플러스 정기예금");
            System.out.println("2. 쏠 편한 정기예금");
            uv.mLine('=', 30);
            System.out.print("원하는 상품을 선택하세요: ");

            String choice3 = sc.nextLine();

            if (choice3.equalsIgnoreCase("Q")) {
                return;
            }
            switch (choice3) {
                case "1":
					List<DepositDTO> myPlusfixedDeposits = depositDAO.getDepositsByName("신한 My플러스 정기예금");

                    if (myPlusfixedDeposits != null) {
                        for(int i = 0; i < myPlusfixedDeposits.size(); i++) {
                            DepositDTO depdto = myPlusfixedDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    fixed.myPlusfixedDeposit();
                    showMenu();
                    break;
                case "2":
					System.out.println("쏠 편한 정기예금");
                    List<DepositDTO> solEasyfixedDeposits = depositDAO.getDepositsByName("쏠 편한 정기예금");

                    if (solEasyfixedDeposits != null) {
                        for(int i = 0; i < solEasyfixedDeposits.size(); i++) {
                            DepositDTO depdto = solEasyfixedDeposits.get(i);    // deposits 리스트에서 i번째 상품 가져옴
                            System.out.println((i + 1) + ". " + depdto.getProductName() + " 기간: " + depdto.getPeriod() + "개월, 금리: " + depdto.getInterest() + "%");
                        }
                    } else {
                        System.out.println("해당 상품이 없습니다.");
                    }
                    fixed.solEasyfixedDeposit();
                    showMenu();
                    break;
                default:
                    System.out.println("^ 1 ~ 2 사이의 메뉴번호 선택 하세요.");
            }
        }
    }  
}
