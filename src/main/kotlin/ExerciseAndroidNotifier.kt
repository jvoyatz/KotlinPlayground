import java.lang.IllegalStateException


const val SMS = 1;
const val PUSH = 2;

fun main() {
    //1st question
    val notifier = Notifier()
//    notifier.notify("test", 1)
//    notifier.notify("push-test", 2)

    //2nd question
    val smsNotifier: INotifier = SmsNotifier()
    val pushNotifier: INotifier = PushNotifier()
    val listener = object : PushNotifier.Listener{
        override fun pushNotification(message: String?) {
            val msg2print = message?:"not provided"
            println("PushNotification: $msg2print")
        }
    }
    if(pushNotifier is PushNotifier){
        pushNotifier.registerListener(listener)
    }
    notifier.notify("sms-test", smsNotifier)
    notifier.notify("push-test", pushNotifier)

    //3rd question
    //3rd party notification systems
    val mysticNotifier = object : INotifier {
        val mysticNotifier: MysticNotifier = MysticNotifier()

        override fun notify(message: String?) {
            mysticNotifier.someMysticNotification(msg = message)
        }
    }
    notifier.notify("mystic-test", mysticNotifier)
    //this way we are able to support many many types of notifications

    //last question : how to support synchronous and asynchronous notification
    //for instance sms could be synchronous, however mystic notifications could be asynchronous
    //and we want to know if the notifications has been sent successfully
    //for instance INotifier:notify could return a boolean value
    //for smsNotification would be result = smsNotifier.notify(..) and we are okay
    //for mysticNotifications would be something different and we cant have a synchronous result of the method execution
    //since it is being executed in a thread
    //the solution here is liveData as defined in Android Framework, no code here
    //we re missing the Android api
    //otherwise callbacks through custom Interfaces that would be passed as an argument in INotifier:notify(...) method.

    if(pushNotifier is PushNotifier){
        pushNotifier.unregisterListener()
    }
}

/**
 * Whatever that is commented out is the 1st phase of the question.
 * You just want to extend the central notifier to support a push notifier as well.
 *
 * Next question is to support multiple notifier by keeping the Notifier class simple
 * The answer is polymorphism.
 * Declare an interface where is going to be implemented by our custom notifiers and using
 * polymorphism we won't be hard dependent inside notifier class, when we want to send
 * a notification.
 *
 * The notifier class is just responsible to invoke the overriden from INotifier interfacde method
 */
interface INotifier{
    fun notify(message: String?)
}

class Notifier {
    //not maintainable in case you many different types of notifications
    //val smsNotifier: SmsNotifier = SmsNotifier()
    //val pushNotifier: PushNotifier = PushNotifier()

//    init {
//        pushNotifier.registerListener(this)
//    }
//    fun notify(message:String?, code:Int){
//        when(code){
//            SMS -> smsNotifier.notify(message)
//            PUSH -> message?.let { pushNotifier.notify(it) }
//        }
//    }

    //polymorphism
    fun notify(message: String?, notifier: INotifier){
        if(!message.isNullOrBlank())
            notifier.notify(message)
        else
            println("not a valid string")
    }

//    fun destroy(){
//        pushNotifier.unregisterListener()
//    }
//
//    override fun pushNotification(message: String?) {
//        val msg2print = message?:"not provided"
//        println("PushNotification: $msg2print")
//    }
}
class SmsNotifier(): INotifier{
//    fun notify(message: String?){
//        println(message)
//    }

    override fun notify(message: String?) {
        message?.let { println("smsNotification: $it") }
    }
}

class PushNotifier(): INotifier{
    var listener: Listener ? = null

    fun registerListener(_listener: Listener){
        listener = _listener
    }
    fun unregisterListener(){
        listener = null
    }

    override fun notify(message: String?) {
        if(listener != null){
            listener?.pushNotification(message)
        }else{
            throw IllegalStateException("not initialized listener")
        }
    }

    interface Listener{
        fun pushNotification(message: String?)
    }
}

/**
 * Lets suppose that we have a 3rd party notification framework and we are not able to customize it
 * using our INotifier interface directly, since we do not own the code
 */

internal class MysticNotifier{
    public fun someMysticNotification(msg: String?){
        println("mystic spells travel through notifications: $msg")
    }
}