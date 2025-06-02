package utils;

import utils.base.StaticClass;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

public class DateUtil extends StaticClass {
    static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * @param dateString date in yyyy-mm-dd format
     * @return corresponding date string in UTC format at start of the day
     * <p>EXAMPLE</p>
     * <pre>
     * {@code
     * String utcDate = DateUtil.toUtc("2025-05-25");
     * // utcDate = "2025-05-25T00:00Z"
     * }
     * </pre>
     */
    public static String toUtc(String dateString) {

        LocalDate localDate = LocalDate.parse(
                dateString,
                DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD));

        ZonedDateTime utcDateTime = localDate.atStartOfDay(UTC);

        return utcDateTime.toString();
    }
}
