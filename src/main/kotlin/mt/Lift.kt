package mt

import rx.Observable
import rx.Subscriber

/**
 * 针对事件序列的处理和再发送
 */
class SubscriberWrapper(private val subscriber: Subscriber<in String>?) : Subscriber<Int>(subscriber) {
    override fun onNext(t: Int?) {
        println("mt.SubscriberWrapper onNext : $t")
        subscriber?.onNext("to String " + t.toString())
    }

    override fun onCompleted() {
        println("mt.SubscriberWrapper onCompleted")
        subscriber?.onCompleted()
    }

    override fun onError(e: Throwable?) {
        println("mt.SubscriberWrapper onError : $e")
        subscriber?.onError(e)
    }

}

fun main(args: Array<String>) {
    Observable.create<Int> {
        it.onStart()
        it.onNext(11)
        it.onCompleted()
    }
        .lift(Observable.Operator<String, Int> { t -> SubscriberWrapper(t) })
        .subscribe { println("lift : $it") }
}
