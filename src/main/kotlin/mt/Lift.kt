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

/**
 * lift操作通过代理模式实现
 * 主要原理是利用原来Observable的onSubscribe(创建原始Observable的时候生成)生成新的Observable,
 * 在新Observable执行subscribe的时候,借助Operator生成新的Subscriber替换原始Subscriber
 * 代理Subscriber收到新Observable执行onSubscribe发送的事件时,会转换为自己的数据类型,并通过原始Subscriber把转换后的数据发送出去
 * 这样下游的Subscriber就可以收到转换后的结果
 *
 * lift可以理解为是RxJava的核心,线程切换,map等都使用了map
 */
fun main(args: Array<String>) {
    Observable.create<Int> {
        it.onStart()
        it.onNext(11)
        it.onCompleted()
    }
        .lift(Observable.Operator<String, Int> { t -> SubscriberWrapper(t) })
        .subscribe { println("lift : $it") }
}
