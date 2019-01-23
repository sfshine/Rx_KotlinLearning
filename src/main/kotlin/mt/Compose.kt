package mt

import rx.Observable

/**
 *  compose() 是针对 Observable 自身进行变换
 */
class LiftAllTransformer : Observable.Transformer<Int, String> {
    override fun call(t: Observable<Int>): Observable<String> {
        return t.map { it * 10 }.map { "map twice end: $it" }
    }
}

/**
 * 批量lift
 */
fun main(args: Array<String>) {
    Observable.create<Int> {
        it.onNext(1)
        it.onCompleted()
    }.compose(LiftAllTransformer())
        .subscribe {
            println(it)
        }
}