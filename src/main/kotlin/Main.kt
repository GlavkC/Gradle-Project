fun main(args: Array<String>) {
    println("Задание - 1 (Денежные переводы): ")
    val discount = 0.0075
    var accountOne = 1000
    var accountTwo = 0
    var amount = 300

    val commission = if (amount * discount >= 35) (amount * discount).toInt() else 35
    val totalAmount = amount + commission

    if (totalAmount <= accountOne) {
        accountOne -= totalAmount
        accountTwo += amount
        println("Сумма перевода составила: $amount")
        println("Комиссия составила: $commission")
        println("Счет 1: $accountOne")
        println("Счет 2: $accountTwo")
    } else {
        println("Недостаточно средств на счете для перевода")
    }


    print("Задание - 2 (Людишки): ")
    val likes = 2601
    var people = ""
    if (1 == likes % 10 && 11 !== likes && 11 !== likes % 100) {
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