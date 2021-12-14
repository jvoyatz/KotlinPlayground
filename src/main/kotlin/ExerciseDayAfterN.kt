val DAYS = arrayOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
fun findDayAfterN(day:String, n:Int){
    DAYS.forEachIndexed { index, s ->
        if(DAYS[index] == day){
            println("Day $day found in index:$index")
            var newIndex = index;
            if(index + n < 7 ){
                newIndex = index + n
            }else{
                newIndex = (index+n) % 7;
                println("(index+n)%7= ($index + $n)%7 = $newIndex")
            }

            var nDay = DAYS[newIndex]
            println("Day[$newIndex] is $nDay")
            return
        }
    }
}

fun findDayAfterN2(day: String, n:Int){
    val map = (0 until DAYS.size).map { it ->
        DAYS[it] to it
    }.toMap()

    var dayIndex = map[day]
    if(dayIndex != null){
        dayIndex = (dayIndex + n) % 7
        var fDay = DAYS[dayIndex]
        println("Index is $dayIndex and day $fDay")
    }
}


fun main() {
    findDayAfterN2("MON", 11)
//    findDayAfterN("MON", 1)
//    findDayAfterN("MON", 3)
//    findDayAfterN("MON", 5)
//    findDayAfterN("MON", 6)
//
//    findDayAfterN("TUE", 1)
//    findDayAfterN("TUE", 3)
//    findDayAfterN("TUE", 5)
//    findDayAfterN("TUE", 6)
//    findDayAfterN("TUE", 7)
//    findDayAfterN("TUE", 8)
//    findDayAfterN("TUE", 13)

//    findDayAfterN("TUE", 1)
//    findDayAfterN("TUE", 3)
//    findDayAfterN("TUE", 1001)
//    findDayAfterN("TUE", 6)
    // findDayAfterN("SUN", 3)
    //findDayAfterN("MON", 4)
//    findDayAfterN("MON", 167)


}