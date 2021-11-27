// паттерн стратегии - поведенческий паттерн. Он определяет семейство схожих алгоритмов и помещает каждый из них в собственный класс, после чего алгоритмы можно взаимозаменять прямо во время исполнения программы.
// В данном примере есть интерфейс ShoppingStrategy, на его основе создаются похожие классы ClothesShoppingStrategy и CosmeticShoppingStrategy. Они позволяют нам разделить логику шоппинга одежды и косметики, передав в них стоимость одной позиции. После этого создается класс Customer, позволяющий посчитать общую сумму покупок. После чего, мы в основной функции main можем вызвать один и тот же класс Customer для подсчетов и посчитать суммы покупок в разных стратегиях, просто поменяв контекст этого класса.

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