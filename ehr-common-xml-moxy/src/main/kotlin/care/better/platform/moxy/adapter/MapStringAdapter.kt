package care.better.platform.moxy.adapter

import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Primoz Delopst
 */
class MapStringAdapter : XmlAdapter<MapStringAdapter.AdaptedMap, MutableMap<String, String>>() {

    override fun unmarshal(adapterMap: AdaptedMap): MutableMap<String, String> = adapterMap.entry.associateTo(mutableMapOf(), { Pair(it.key, it.value) })

    override fun marshal(map: MutableMap<String, String>): AdaptedMap = AdaptedMap(map.entries.map { Entry(it.key, it.value) })

    data class AdaptedMap(val entry: List<Entry>)

    data class Entry(val key: String, val value: String)
}