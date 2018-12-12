package mt

import rx.Observable

fun main(args: Array<String>) {
    Observable.just(1, 2, 3).subscribe {
        println("just: print one param every time $it")
    }
    Observable.from(arrayOf(1, 2, 3)).subscribe(
        { println("fromArray: print one element every time $it") },
        { println("fromArray: error $it") },
        { println("fromArray: complete") }
    )
}