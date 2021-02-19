package care.better.platform.utils;

import org.junit.jupiter.api.Test;
import org.openehr.rm.datatypes.DvBoolean;
import org.openehr.rm.datatypes.DvDate;
import org.openehr.rm.datatypes.DvDuration;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaInteropTest {

    @Test
    public void testCtor() {
        DvDate date1 = new DvDate();
        assertThat(date1.getValue()).isNull();

        DvDate date2 = new DvDate("2020-01-01", new DvDuration("PT1H"));
        assertThat(date2.getValue()).isEqualTo("2020-01-01");
        assertThat(date2.getAccuracy()).isNotNull();
        assertThat(date2.getAccuracy().getValue()).isEqualTo("PT1H");

        DvBoolean bool = new DvBoolean();
        assertThat(bool.getValue()).isFalse();
    }
}
