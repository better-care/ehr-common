/* Copyright 2020-2025 Better Ltd (www.better.care)
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

import care.better.platform.annotation.Opened

/**
 * @author Primoz Delopst
 */

@Opened
class CallbackWait : EventWait<CallbackNotification> {

    var failAction: EventAction? = null
    var customActions: MutableMap<String, EventAction> = mutableMapOf()

    constructor()

    constructor(event: CallbackNotification?) : super(event)

    constructor(event: CallbackNotification?, successAction: EventAction?, timeout: TimerWait?, failAction: EventAction?) : super(event, successAction, timeout) {
        this.failAction = failAction
    }

    override fun toString(): String =
            "CallbackWait{" +
                    "failAction=$failAction" +
                    ", customActions=$customActions" +
                    "} ${super.toString()}"
}