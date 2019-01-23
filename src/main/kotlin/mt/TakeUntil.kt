package mt

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * 在timeEnd事件到来之前, 执行interval事件
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    var timeEnd = Observable.timer(5000, TimeUnit.MILLISECONDS)
    Observable.interval(1000, TimeUnit.MILLISECONDS).takeUntil(timeEnd).subscribe {
        println(it)
    }
    countDownLatch.await()
}