package com.lilly.gss.mdit.reese.internal.util;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by C232018 on 8/8/2017.
 */
public final class ReeseUtil {

    public static final Date getCurrentDateUtc() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return cal.getTime();

    }

    public static final Date getCurrentDateUTCByAddingMinutes(int minutes) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    public static Date getCurrentDateUTCByRemovingMinutes(Long minutes) {
        Instant instant = Instant.EPOCH;
        LocalDateTime localDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        localDate.minusMinutes(minutes);
        Date dt = Date.from(localDate.toInstant(ZoneOffset.UTC));
        System.out.println("Removing Minutes "+ dt);
        return dt;
    }
}
