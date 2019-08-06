package mt

import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

fun main(args: Array<String>) {
    println("start test: [Current Thread] ${Thread.currentThread()}")
    var countDownLatch = CountDownLatch(1)
    Observable
        .create<Int> {
            for (i in 0..5) it.onNext(i)
            println("subscribeOn Thread ${Thread.currentThread()}")
            it.onCompleted()
        }
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.io())
        .subscribe {
            println("observeOn Thread ${Thread.currentThread()}, value $it")
        }
    countDownLatch.await()
}
