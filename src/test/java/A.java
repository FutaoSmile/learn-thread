import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author futao
 * @date 2020/7/17
 */
public class A {
    public static void main(String[] args) {
        Duration duration = Duration.ofSeconds(0);
        System.out.println(duration);
        Duration plus = duration.plus(1, ChronoUnit.SECONDS);
        Duration plus1 = plus.plus(1, ChronoUnit.SECONDS);

        System.out.println(plus);
        System.out.println(plus1);
        System.out.println(duration);
    }
}
