package LocalDate;


import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Java处理日期、日历和时间的不足之处：将 java.util.Date 设定为可变类型，以及 SimpleDateFormat 的非线程安全使其应用非常受限。然后就在 java8 上面增加新的特性。
 */
public class LocalDateTest {
    //获取当前日期/时间
    @Test
    public void Test1() {
        LocalDate today = LocalDate.now();
        System.out.println(today);//2022-02-21
        LocalTime time = LocalTime.now();//默认的格式是hh:mm:ss:nnn。
        System.out.println(time);//09:34:39.387
        Instant timestamp = Instant.now();///毫秒时间戳
        System.out.println(timestamp.toEpochMilli());//
    }

    //获取年、月、日
    @Test
    public void Test2() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year : %d  Month : %d  day : %d ", year, month, day);
    }

    //创建日期
    @Test
    public void Test3() {
        LocalDate dateOfBirth = LocalDate.of(2018, 01, 21);
        System.out.println(dateOfBirth);
    }

    //日期是否相等
    @Test
    public void Test4() {
        LocalDate date1 = LocalDate.of(2018, 01, 21);
        LocalDate date2 = LocalDate.of(2018, 01, 21);
        System.out.println(date1.equals(date2));
    }

    //判断周期性日期
    @Test
    public void Test5() {
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(2018, 02, 21);
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);
        if (currentMonthDay.equals(birthday)) {
            System.out.println("生日快乐");
        }
    }

    //时间加减
    @Test
    public void Test6() {
        LocalTime time = LocalTime.now();
        LocalTime newTime = time.plusHours(2); // 增加两小时
        System.out.println(newTime);
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);    //增加1星期
        System.out.println(nextWeek);
        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);//减少1年
        System.out.println(previousYear);
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);//增加1年
        System.out.println(nextYear);
    }

    //Clock类
    @Test
    public void Test7() {
        // 根据系统时间返回当前时间并设置为UTC。
        Clock clock = Clock.systemUTC();
        Instant instant = clock.instant();
        System.out.println("Clock : " + clock);
        System.out.println("毫秒时间戳: " + clock.millis());
        // 根据系统时钟区域返回时间
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock : " + defaultClock);
    }

    //日期比较
    @Test
    public void Test8() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.of(2022, 02, 22);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today");
        }
        //减去一天
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
        if (yesterday.isBefore(today)) {
            System.out.println("Yesterday is day before today");
        }
    }

    //设置时区
    @Test
    public void Test9() {
        //设置时区
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime localtDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america);
        System.out.println("现在的日期和时间在特定的时区 : " + dateAndTimeInNewYork);
    }

    //YearMonth(年月类)
    @Test
    public void Test10() {
        //获取月份天数
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());//获取月份天数
        //设置年月份
        YearMonth creditCardExpiry = YearMonth.of(2028, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    //判断闰年
    @Test
    public void Test11() {
        LocalDate today = LocalDate.now();
        if (today.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("This year is not a Leap year");
        }
    }

    //获取两个日期之间的天数和月数
    @Test
    public void Test12() {
        LocalDate date1 = LocalDate.of(2022, 1, 1);
        LocalDate date2 = LocalDate.of(2022, Month.MAY, 14);
        Period period = Period.between(date1, date2);
        System.out.println("months: " + period.getMonths());
        System.out.println("days: " + period.getDays());
    }

    //日期格式化
    @Test
    public void Test13() {
        //固定格式
        String day1 = "20180210";
        LocalDate format1 = LocalDate.parse(day1, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", day1, format1);//2018-02-10
        //自定义格式
        LocalDateTime day2 = LocalDateTime.now();
        String format2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(day2);//yyyy-MM-dd HH:mm:ss
        System.out.printf("Date generated from String %s is %s %n", day2, format2);
    }
}
