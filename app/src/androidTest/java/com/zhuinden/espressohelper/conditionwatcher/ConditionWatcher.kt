/*
 * Copyright 2015 AzimoLabs (ConditionWatcher)
 *
 * Copyright 2018 Gabor Varadi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhuinden.espressohelper.conditionwatcher

// from https://github.com/AzimoLabs/ConditionWatcher
object ConditionWatcher {
    private const val CONDITION_NOT_MET = 0
    private const val CONDITION_MET = 1
    private const val TIMEOUT = 2

    private const val DEFAULT_TIMEOUT_LIMIT = 1000 * 60
    private const val DEFAULT_INTERVAL = 250

    private var timeoutLimit = DEFAULT_TIMEOUT_LIMIT
    private var watchInterval = DEFAULT_INTERVAL

    @Throws(Exception::class)
    fun waitForCondition(checkCondition: () -> Boolean) {
        var status = CONDITION_NOT_MET
        var elapsedTime = 0

        do {
            if (checkCondition()) {
                status = CONDITION_MET
            } else {
                elapsedTime += watchInterval
                Thread.sleep(watchInterval.toLong())
            }

            if (elapsedTime >= timeoutLimit) {
                status = TIMEOUT
                break
            }
        } while (status != CONDITION_MET)

        if (status == TIMEOUT) {
            throw Exception("Instruction took more than " + timeoutLimit / 1000 + " seconds. Test stopped.")
        }
    }
}