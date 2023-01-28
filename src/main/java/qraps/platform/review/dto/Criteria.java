package qraps.platform.review.dto;

public enum Criteria {
    NA(0),
    MIN(1),
    EQUAL(2),
    MAX(3),
    TOLERANCE(4),
    RESERVED(5); // 의미 없는 값

    private final int criteria;

    Criteria(int criteriaValue) {
        this.criteria = criteriaValue;
    }

    public int getCriteria() {
        return criteria;
    }

    /**
     * TOLERANCE 옵션 허용 오차
     * 현재 설정값 -20% ~ 20% 오차 허용
     */
    public double getToleranceMinRate() {
        return 0.2;
    }

    /**
     * TOLERANCE 옵션 허용 오차
     * 현재 설정값 -20% ~ 20% 오차 허용
     */
    public double getToleranceMaxRate() {
        return 0.2;
    }
}
