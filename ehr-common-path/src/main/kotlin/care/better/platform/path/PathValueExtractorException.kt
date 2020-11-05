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

package care.better.platform.path

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Exception that is thrown during value extracting.
 */
class PathValueExtractorException : RuntimeException {
    companion object {
        const val serialVersionUID: Long = 1L
    }

    /**
     * Creates a new instance of [PathValueExtractorException].
     *
     * @param message Exception message
     */
    constructor(message: String) : super(message)

    /**
     * Creates a new instance of [PathValueExtractorException].
     *
     * @param message Exception message
     * @param cause [Throwable]
     */
    constructor(message: String, cause: Throwable) : super(message, cause)

    /**
     * Creates a new instance of [PathValueExtractorException].
     *
     * @param cause [Throwable]
     */
    constructor(cause: Throwable) : super(cause)
}
