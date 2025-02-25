import java.util.*;

public class DepositDAO {
    List<DepositDTO> depositList = new ArrayList<>();

    public DepositDAO() {   // dao 생성자, dao 객체 생성될 때 initializeDeposits 호출 (상품 추가)
        initializeDeposits();
    }

    // 초기 적금 상품 데이터 추가
    private void initializeDeposits() {
        depositList.add(new DepositDTO("청년도약계좌", 60, 4.5));
        depositList.add(new DepositDTO("청년우대적금", 12, 3.3));

        depositList.add(new DepositDTO("청포도 청년적금", 6, 4.0));
        depositList.add(new DepositDTO("청포도 청년적금", 12, 4.0));

        depositList.add(new DepositDTO("알.쏠.적금", 12, 3.0));
        depositList.add(new DepositDTO("알.쏠.적금", 24, 3.1));
        depositList.add(new DepositDTO("알.쏠.적금", 36, 3.2));

        depositList.add(new DepositDTO("신한 스마트적금", 12, 3.1));

        depositList.add(new DepositDTO("신한 S드림적금", 6, 2.3));
        depositList.add(new DepositDTO("신한 S드림적금", 12, 2.4));
        depositList.add(new DepositDTO("신한 S드림적금", 24, 2.5));
        depositList.add(new DepositDTO("신한 S드림적금", 36, 2.5));
        depositList.add(new DepositDTO("신한 S드림적금", 48, 2.6));
        depositList.add(new DepositDTO("신한 S드림적금", 60, 2.6));

		depositList.add(new DepositDTO("신한 My플러스 정기예금", 1, 2.65));
		depositList.add(new DepositDTO("신한 My플러스 정기예금", 3, 2.75));
		depositList.add(new DepositDTO("신한 My플러스 정기예금", 6, 2.8));
		depositList.add(new DepositDTO("신한 My플러스 정기예금", 12, 2.85));

		depositList.add(new DepositDTO("쏠 편한 정기예금", 1, 2.75));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 3, 2.90));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 6, 2.90));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 9, 2.90));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 12, 3.00));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 24, 2.65));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 36, 2.65));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 48, 2.65));
		depositList.add(new DepositDTO("쏠 편한 정기예금", 60, 2.65));
    }

    // 전체 상품 목록 가져오기
    public List<DepositDTO> getAllDeposits() {
        return depositList;
    }

    // 상품명으로 검색
    public List<DepositDTO> getDepositsByName(String name) {
        List<DepositDTO> result = new ArrayList<>();        // name에 맞는 해당 리스트만 따로 result 리스트에 추가됨
        for (DepositDTO deposit : depositList) {
            if (deposit.getProductName().equals(name)) {
                result.add(deposit);
            }
        }
        return result.isEmpty() ? null : result;
    }
}
