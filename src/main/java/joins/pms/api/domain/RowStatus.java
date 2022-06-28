package joins.pms.api.domain;

/**
 * Entity의 상태를 나타내는 Enum 객체
 *
 * <p>
 * Entity가 생성되고, 사용이 가능한 상태인 경우 NORMAL로 존재하며,
 * Entity에 대해 삭제가 발생한 경우 Entity 자체가 삭제되지 않고, RowStatus값이 DELETED로 갱신된다.
 * </p>
 *
 * @author lee.byunghoon
 * @version 1.0
 */
public enum RowStatus {
    NORMAL,
    DELETED
}
