package net.plazmix.util.time;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class DateTimeUtil {

    public static final String DEFAULT_DATETIME_PATTERN = ("dd.MM.yyyy h:mm:ss a");
    public static final String DEFAULT_DATE_PATTERN = ("EEE, MMM d, yyyy");
    public static final String DEFAULT_TIME_PATTERN = ("h:mm a");

    public static String formatTime(@NonNull DateFormat dateFormat) {
        return dateFormat.format(new Date());
    }

    public static String formatTime(long millis, @NonNull DateFormat dateFormat) {
        return dateFormat.format(new Date(millis));
    }

    @SneakyThrows
    public static long parsePatternToMillis(@NonNull DateFormat dateFormat, @NonNull String formattedPattern) {
        return dateFormat.parse(formattedPattern).getTime();
    }

    @SneakyThrows
    public static Date parseDate(@NonNull DateFormat dateFormat, @NonNull String formattedDate) {
        return dateFormat.parse(formattedDate);
    }

    public static long parseTimeMillis(String timeString) {
        Calendar calendar = Calendar.getInstance();
        String[] split = timeString.split(" ");
        for (int i = 0, splitLength = split.length; i < splitLength; i++) {
            String s = split[i];
            String lower = s.toLowerCase();
            if (lower.contains("d"))
                calendar.add(Calendar.DATE, Integer.parseInt(lower.replace("d", "")));
            if (lower.contains("h"))
                calendar.add(Calendar.HOUR, Integer.parseInt(lower.replace("h", "")));
            if (lower.contains("m"))
                calendar.add(Calendar.MINUTE, Integer.parseInt(lower.replace("m", "")));
            if (lower.contains("s"))
                calendar.add(Calendar.SECOND, Integer.parseInt(lower.replace("s", "")));
        }
        return calendar.getTimeInMillis() - System.currentTimeMillis();
    }

    public static long diff(long millis) {
        Date lastDate = new Date(millis);
        Date now = new Date();

        if (now.getTime() >= lastDate.getTime())
            return now.getTime() - lastDate.getTime();
        else
            return lastDate.getTime() - now.getTime();
    }

    public static DateFormat createDefaultDateFormat(@NonNull String pattern) {
        return createDateFormat(pattern, TimeZone.getTimeZone("Europe/Moscow"));
    }

    public static DateFormat createDateFormat(@NonNull String pattern, TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("ru"));
        dateFormat.setTimeZone(timeZone);

        return dateFormat;
    }
}
