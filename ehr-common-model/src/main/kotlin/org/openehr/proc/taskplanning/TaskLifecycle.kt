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

package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
enum class TaskLifecycle(val code: Int) : EnumerationInteger, EnumerationString {
    PLANNED(0),
    AVAILABLE(1),
    CANCELLED(2),
    ABANDONED(4),
    COMPLETED(8),
    SUSPENDED(6),
    UNDERWAY(5);

    override fun integerValue(): Int = code
}
