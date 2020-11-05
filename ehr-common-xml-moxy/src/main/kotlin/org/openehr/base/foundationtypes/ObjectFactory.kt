package org.openehr.base.foundationtypes

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createIntervalOfDate(): IntervalOfDate = IntervalOfDate()
    fun createIntervalOfInteger(): IntervalOfInteger = IntervalOfInteger()
    fun createIntervalOfDateTime(): IntervalOfDateTime = IntervalOfDateTime()
    fun createIntervalOfDuration(): IntervalOfDuration = IntervalOfDuration()
    fun createIntervalOfReal(): IntervalOfReal = IntervalOfReal()
    fun createIntervalOfTime(): IntervalOfTime = IntervalOfTime()
}