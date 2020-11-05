package org.openehr.rm.datastructures

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createCluster(): Cluster = Cluster()
    fun createElement(): Element = Element()
    fun createHistory(): History = History()
    fun createIntervalEvent(): IntervalEvent = IntervalEvent()
    fun createItemList(): ItemList = ItemList()
    fun createItemSingle(): ItemSingle = ItemSingle()
    fun createItemTable(): ItemTable = ItemTable()
    fun createItemTree(): ItemTree = ItemTree()
    fun createPointEvent(): PointEvent = PointEvent()
}