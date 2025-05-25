fun main(args: Array<String>) {
    //задание 1
    var second = (0..260700).random() //от 0 до 3 дней + 1500 секунд !в секундах!
    println(second)
    println(frend(second))
    //задание 2
    val listcards = listOf("Mastercard", "Visa", "Мир")
    val randomCard = listcards.random()
    val listdata = listOf("day", "month")
    val randomData = listdata.random()
    println(translations((0..750000).random(), randomCard, randomData))
}
    //задание 1 (функции)
fun frend(seconds: Int): String {
    return when {
        seconds <= 60 -> "был(а) только что"
        seconds <= 60 * 60 -> {
            val minutes = seconds / 60
            "был(а) ${minutes} ${minute(minutes)} назад"
        }
        seconds <= 24 * 60 * 60 -> {
            val hours = seconds / (60 * 60)
            "был(а) ${hours} ${hour(hours)} назад"
        }
        seconds <= 2 * 24 * 60 * 60 -> "был(а) вчера"
        seconds <= 3 * 24 * 60 * 60 -> "был(а) позавчера"
        else -> "был(а) давно"
    }
}

fun minute(minutes: Int): String {
    return when {
        minutes % 100 in 11..14 -> "минут"
        minutes % 10 == 1 -> "минуту"
        minutes % 10 in 2..4 -> "минуты"
        else -> "минут"
    }
}

fun hour(hours: Int): String {
    return when {
        hours % 100 in 11..20 -> "часов"
        hours % 10 == 1 -> "час"
        hours % 10 in 2..4 -> "часа"
        else -> "часов"
    }
}
    //задание 2 (функции)
    fun translations(money: Int, card: String, data: String): Any {
        var max = if (data == "day") {
              150000
        }   else {
              600000
        }
        val commission = when (card) {
            "Mastercard" -> {
                if (money > 75000) {
                    (money - 75000) * 0.006 + 20
                } else {
                    0.0
                }
            }
            "Visa" -> {
                val calculated = money * 0.0075
                if (calculated < 35) 35.0 else calculated
            }
            "Мир" -> 0.0
            else -> return "Ошибка: неизвестный тип карты"
        }
        val total = money + commission.toInt()

        return if (max > money) {
                "Сумма перевода: $money руб., комиссия: ${commission.toInt()} руб. (${card}), итого: $total руб."
            } else {
                "Слишком большая сумма"
            }
        }

