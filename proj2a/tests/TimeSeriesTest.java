import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Unit Tests for the TimeSeries class.
 *
 * @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>(Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testDividedBy() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 10.0);
        ts1.put(2001, 20.0);
        ts1.put(2002, 30.0);
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 2.0);
        ts2.put(2001, 4.0);
        ts2.put(2002, 5.0);

        TimeSeries result = ts1.dividedBy(ts2);
        assertThat(result.get(2000)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2001)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2002)).isWithin(1E-10).of(6.0);
    }

    @Test
    public void testDividedByMissingYearInTs2() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 10.0);
        ts1.put(2001, 20.0);
        ts1.put(2002, 30.0);
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 2.0);
        ts2.put(2001, 4.0);
        // 缺少 2002 年的数据

        assertThrows(IllegalArgumentException.class, () -> ts1.dividedBy(ts2));
    }

    @Test
    public void testDividedByExtraYearInTs2() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 10.0);
        ts1.put(2001, 20.0);
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 2.0);
        ts2.put(2001, 4.0);
        ts2.put(2002, 5.0);  // 额外的年份
        TimeSeries result = ts1.dividedBy(ts2);
        assertThat(result.get(2000)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2001)).isWithin(1E-10).of(5.0);
        assertThat(result.containsKey(2002)).isFalse();
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }
} 