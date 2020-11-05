/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
