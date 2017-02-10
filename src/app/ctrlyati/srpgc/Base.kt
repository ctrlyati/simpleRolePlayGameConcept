package app.ctrlyati.srpgc

import java.util.*

/**
 * Created by Yati on 29/11/2559.
 */

enum class EquipmentSlot {
    HEAD,
    FACE,
    BODY,
    ARMS,
    HANDS,
    LEGS,
    FEET,
    ACCESSORIES_1,
    ACCESSORIES_2,
    ACCESSORIES_3,
    WEAPON_PRIMARY,
    WEAPON_SECONDARY
}

// LOCATION

class Location constructor(val tile: Tile, val position: Position)

class World

class Tile {
    // TODO : add details (much detail)
}

class Position constructor(val x: Double, val y: Double)

// CHARACTER

abstract class Character {

    val BASE_BEARABLE_WEIGHT: Double = 100.0 // 100g
    val BASE_MOVEMENT_SPEED: Double = 2.0 // m/s

    val BASE_HP = 100
    val BASE_SP = 100
    val BASE_MP = 100

    val BASE_ATTACK = 10
    val BASE_MAGICAL_ATTACK = 10
    val BASE_ATTACK_SPEED = 1 // 1 hit/s
    val BASE_SIGHT_RADIUS = 50 // meter
    val BASE_ACCURACY = 20

    var inventory: Container = DefaultCharacterInventory()
    var equipments: HashMap<EquipmentSlot, Item> = HashMap()

    // attributes

    var experience: Int = 0

    var strength: Int = 1// atk bearWeight--
    var intelligence: Int = 1// m.atk
    var vitality: Int = 1// hp  bearWeight-
    var stamina: Int = 1// sp
    var wisdom: Int = 1// mp
    var agility: Int = 1// moveSpeed atkSpeed evade
    var dexterity: Int = 1// criticalRate accuracy
    var perception: Int = 1// sightRadius
    var charisma: Int = 1// talking trading


    fun getLevel(): Int {
        return experience / 100
    }

    // statistic

    fun getAttack(): Int {
        return BASE_ATTACK + strength * 2
    }

    fun getMagicalAttack(): Int {
        return BASE_MAGICAL_ATTACK + intelligence * 2
    }

    fun getHp(): Int {
        return BASE_HP + vitality * 10
    }

    fun getSp(): Int {
        return BASE_SP + stamina * 10
    }

    fun getMp(): Int {
        return BASE_MP + wisdom * 10
    }

    fun getAttackSpeed(): Double {
        return BASE_ATTACK_SPEED + agility * 0.05
    }

    fun getMoveSpeed(): Double {
        var speed = BASE_MOVEMENT_SPEED + agility * 0.1
        if (isOverweight()) speed /= 2
        return speed
    }

    fun getEvade(): Double {
        return (agility + dexterity) * 1.0
    }

    fun getCriticalRate(): Double {
        return dexterity * 0.01
    }

    fun getAccuracy(): Double {
        return BASE_ACCURACY + dexterity * 1.0
    }

    fun getSightRadius(): Double {
        return BASE_SIGHT_RADIUS + perception * 1.0
    }

    fun getBearingWeight(): Double {
        val equipmentsWeight: Double = equipments.values.sumByDouble(Item::getWeight)
        return inventory.getWeight() + equipmentsWeight
    }

    fun getBearableWeight(): Double {
        return BASE_BEARABLE_WEIGHT + (strength * 2) + (vitality * 4)
    }

    fun isOverweight():Boolean {
        return getBearableWeight() < getBearingWeight()
    }

    // items

    abstract fun consume(item: ConsumableItem, target: Character): Boolean
    abstract fun equip(equipment: Equipment): Boolean
    abstract fun drop(item: Item): Boolean


    // life cycle

    abstract fun onSpawn(at: Location)
    abstract fun onDeath(by: Any)
    abstract fun onReceiveDamage(by: Any, damage: Int)

}

class Player : Character() {

    override fun consume(item: ConsumableItem, target: Character): Boolean {
        return item.onConsume(this, this)
    }

    override fun equip(equipment: Equipment): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drop(item: Item): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSpawn(at: Location) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeath(by: Any) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReceiveDamage(by: Any, damage: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// CONTAINER

interface Container {
    fun getWeight(): Double
    fun push(item: Item): Boolean
    fun pop(item: Item): Boolean
}

class DefaultCharacterInventory : Container {

    val items = ArrayList<Item>()

    override fun getWeight(): Double {
        return items.sumByDouble(Item::getWeight)
    }

    override fun push(item: Item): Boolean {
        return items.add(item)
    }

    override fun pop(item: Item): Boolean {
        return items.remove(item)
    }

}

// ITEMS

interface Item {
    fun getWeight(): Double
}

abstract class Equipment : Item {
    abstract fun getSlotTaking(): Array<EquipmentSlot>
}

abstract class ConsumableItem : Item {
    abstract fun onConsume(user: Character, target: Character): Boolean
}

