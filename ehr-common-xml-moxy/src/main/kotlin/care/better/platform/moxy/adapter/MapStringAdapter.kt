package care.better.platform.moxy.adapter

import javax.xml.bind.annotation.adapters.XmlAdapter

/**
 * @author Primoz Delopst
 */
class MapStringAdapter : XmlAdapter<MapStringAdapter.AdaptedMap, MutableMap<String, String>>() {

    override fun unmarshal(adapterMap: AdaptedMap): MutableMap<String, String> = adapterMap.entry.associateTo(mutableMapOf(), { Pair(it.key, it.value) })

    override fun marshal(map: MutableMap<String, String>): AdaptedMap = AdaptedMap(map.entries.map { Entry(it.key, it.value) })

    class AdaptedMap(){
        var entry: MutableList<Entry> = mutableListOf()

        constructor(entry: List<Entry>) : this() {
            this.entry.addAll(entry)
        }
    }

    class Entry() {
        lateinit var key: String
        lateinit var value: String

        constructor(key: String, value: String) : this() {
            this.key = key
            this.value = value
        }
    }
}