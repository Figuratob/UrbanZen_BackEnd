package ee.urbanzen.backoffice.domain.enumeration;

/**
 * The DayOfWeek enumeration.
 */
public enum DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

    public int getValue() {
        return this.ordinal() + 1;
    }
}
