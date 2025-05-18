fun main(args: Array<String>) {
    print("Задание - 1 (Денежные переводы): ")
    val discount = 0.75
    var accountOne = 1000
    var accountTwo = 0
    var amount = 200
    if (amount >= 35) {
        accountOne = accountOne - amount
        amount = (amount * discount).toInt()
    } else {
        accountOne = accountOne - amount
    }
    accountTwo = accountTwo + amount
    println("Сумма перевода составила: " + amount)
    println("Счет 1: " + accountOne)
    println("Счет 2: " + accountTwo)

    print("Задание - 2 (Людишки): ")
    val likes = 41
    var people = ""
    if (1 == likes % 10) {
        people = "человеку"
    } else {
        people = "людям"
    }
    println("Понравилось $likes $people")

    print("Задание - 3 (Меломан): ")
    val specalСustomer = true //false
    val buySumm = 10_001
    var result = 0
    if (buySumm <= 1000) { result = buySumm }
    if (buySumm <= 10000) { result = buySumm - 100 }
    if (buySumm > 10000) { result = (buySumm * 0.95).toInt() }
    if (specalСustomer == true) {
        result = (result * 0.99).toInt()
        print("Скидка Особому! покупателю дополнительно 1% - ")
    }
    print("стоймость покупки составила - $result")
}