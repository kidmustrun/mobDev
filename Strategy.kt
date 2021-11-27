interface ShoppingStrategy {
    val price: Int
}

class СlothesShoppingStrategy : ShoppingStrategy {
    override val price: Int = 40
    override fun toString(): String {
        return "СlothesShoppingStrategy"
    }
}

class СosmeticShoppingStrategy : ShoppingStrategy {
    override val price: Int = 70
    override fun toString(): String {
        return "СosmeticShoppingStrategy"
    }
}

class Customer(var shoppingStrategy: ShoppingStrategy) {
    fun calculatePrice(numOfPositions: Int): Int {
        val price = numOfPositions * shoppingStrategy.price
        println("Calculating prices using " + shoppingStrategy)
        return price
    }
}

fun main(args: Array<String>) {
    val customer = Customer(ClothesShoppingStrategy())
    var price = customer.calculatePrice(5)
    println(price)
    customer.shoppingStrategy = CosmeticShoppingStrategy()
    price = customer.calculatePrice(3)
    println(price)
}