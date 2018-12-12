package mt

import rx.Observable

/**
 * 过滤数据
 */
fun main(args: Array<String>) {
    Observable.just(1, 2, 3, 4, 5).filter {
        it % 2 != 0
    }.subscribe {
        println(it)
    }
}