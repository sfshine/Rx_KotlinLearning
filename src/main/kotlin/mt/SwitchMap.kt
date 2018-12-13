package mt

import rx.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * https://segmentfault.com/a/1190000015232015
 *
 *
 */
fun main(args: Array<String>) {
    var countDownLatch = CountDownLatch(1)
    /**
     * 0,1,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1...
     * 0,1,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1,4,2,0,3,1...
     * flatMap/mergeMap会即使响应主流中发射的每一个数据，它既不会忽略也不会缓存，这就导致主流中数据对应的从流产生了叠加。
     */
    Observable.interval(500, TimeUnit.MILLISECONDS)
        .flatMap {
            Observable.interval(200, TimeUnit.MILLISECONDS).take(5)
        }
        .subscribe {
            print("$it,")
        }
    /**
     * 发送结果
     * 0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,
     * 虽然在从流还没有结束的时候，主流还在发射数据，主流会先把发射的数据缓存起来，等从流结束后立即响应主流的数据从而引发新一轮的从流发射，
     * 这有些类似与js的消息队列机制。所以我们看到它的输出流响应是连续的。
     */
    Observable.interval(500, TimeUnit.MILLISECONDS)
        .concatMap {
            Observable.interval(200, TimeUnit.MILLISECONDS).take(5)
        }
        .subscribe {
            print("$it,")
        }
    /**
     * 发送结果: 0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1
     * 用switchMap的时候，从流每次只能发射2个数据0-1，这是因为主流每发射一次触发了从流的发射，但是在从流发射的过程中，
     * 如果主流又一次发射了数据，switchMap会截断上一次的从流，响应本次的主流，从而开启新的一段的从流发射。
     */
    Observable.interval(500, TimeUnit.MILLISECONDS)
        .switchMap {
            Observable.interval(200, TimeUnit.MILLISECONDS).take(5)
        }
        .subscribe {
            print("$it,")
        }
    countDownLatch.await()
}