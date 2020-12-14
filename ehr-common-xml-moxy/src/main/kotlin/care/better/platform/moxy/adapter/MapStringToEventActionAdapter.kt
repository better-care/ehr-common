package care.better.platform.moxy.adapter

import org.openehr.proc.taskplanning.EventAction
import javax.xml.bind.annotation.adapters.XmlAdapter


/**
 * @author Primoz Delopst
 */

class MapStringToEventActionAdapter : XmlAdapter<MapStringToEventActionAdapter.AdaptedMap, Map<String, EventAction>>() {

    override fun unmarshal(adaptedMap: AdaptedMap): MutableMap<String, EventAction> =
            adaptedMap.entry.associate { Pair(it.key, it.value) }.toMutableMap()

    override fun marshal(map: Map<String, EventAction>): AdaptedMap =
            AdaptedMap().apply {
                map.forEach { this.entry.add(Entry(it.key, it.value)) }
            }

    data class AdaptedMap(val entry: MutableList<Entry> = mutableListOf())

    data class Entry(val key: String, val value: EventAction)
}