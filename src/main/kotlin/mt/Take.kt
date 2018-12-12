package mt

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * 取前n个事件,然后解绑Subscriber
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    Observable.interval(1000, TimeUnit.MILLISECONDS).take(5).subscribe {
        println(it)
    }
    countDownLatch.await()
}