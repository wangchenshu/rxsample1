import javafx.print.Collation;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by chenshuwang on 2016/7/11.
 */
public class Main {
    public static void println(String... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
        }
        System.out.println("");
    }

    public static void println() {
        System.out.println("");
    }

    public static void main(String[] args) {
        /*
          Observable.from()
          Creates an observable from an Iterable, a Future, or an Array.
        */
        Observable.from(Stream.of(1, 2, 3, 4, 5).collect(Collectors.toSet()));
        Observable.from(Arrays.asList(1, 2, 3, 4, 5));

        println();

        //create
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                IntStream.range(1, 6).forEach(it -> subscriber.onNext(it));
                subscriber.onCompleted();
            }
        })
        .map(i -> String.valueOf(i+2))
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                println("Error encountered: " + error.getMessage());
            }

            @Override
            public void onNext(String item) {
                println("(create) Next: " + item);
            }
        });
        println();

        // from
        /*
            Integer[] items = {1, 2, 3, 4, 5};
            Observable myObservable = Observable.from(items);
            myObservable.subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer item) {
                        println("(from) Next: " + item);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        println("Error encountered: " + error.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        println("Sequence complete");
                    }
                }
            );
         */

        Integer[] items = {1, 2, 3, 4, 5};
        Observable myObservable = Observable.from(items);
        myObservable
        .map(i -> String.valueOf((int)i*2))
        .subscribe(
            item -> println("(from) Next:" + item),
            error -> println("Error encountered: " + error),
            () -> println("Sequence complete.")
        );
        println();

        // just
        Observable.just(1, 2, 3, 4, 5)
        .map(i -> String.valueOf(i+1))
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                println("Error: " + error.getMessage());
            }

            @Override
            public void onNext(String item) {
                println("(just) Next: " + item);
            }
        });
        println();

        // Map-1
        Observable.just(1, 2, 3, 4, 5)
        .map(it -> it+3)
        .subscribe(it -> println("map-1: " + it));
        println();

        // Map-2
        Observable.just(1, 2, 3, 4, 5)
        .map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer it) {
                return it+3;
            }
        })
        .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer it) {
                println("map-2: " + it);
            }
        });
        println();

        // FlatMap-1
        Observable.from(Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList()))
        .flatMap(it -> Observable.just(it+4))
        .subscribe(
            it -> println("flatMap-1: " + it)
        );
        println();

        // FlatMap-2
        Observable.from(Arrays.asList(1, 2, 3, 4, 5))
        .flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer it) {
                return Observable.just(it+4);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer it) {
                println("flatMap-2: " + it);
            }
        });
        println();

        // Range
        Observable.range(1, 5).subscribe(
            it -> println("Range: " + it)
        );
        println();

        // Repeat
        Observable.range(6, 10).repeat(2).subscribe(
            it -> println("Repeat: " + it)
        );
        println();

        // Buffer-1
        Observable.range(1, 20).buffer(5)
        .flatMap(it -> Observable.from(it))
        .subscribe(it -> println("Buffer-1: " + it));
        println();

        // Buffer-2
        Observable.range(1, 100).buffer(20)
        .subscribe(it -> println("Buffer-2: " + it));
        println();

        // GroupBy
        Observable.range(1, 10).groupBy(it -> it%2 ==0)
        .flatMap(it->it.toList())
        .subscribe(it -> println("GroupBy: " + it));
        println();

        // Scan
        Observable.range(1, 10).scan((sum, it) -> sum += it)
        .subscribe(it -> println("Scan: " + it));
        println();

        // Window
        Observable.range(1, 100).window(20)
        .flatMap(it -> it.toList())
        .subscribe(it -> println("Window: " + it));
        println();

        // Distinct
        Observable.just(1, 1, 1, 2, 2, 3, 4, 4, 5).distinct()
        .subscribe(it -> println("Distinct: " + it));
        println();

        // ElementAt
        Observable.range(1, 10).elementAt(3)
        .subscribe(it -> println("ElementAt: " + it));
        println();

        // Filter
        Observable.range(1, 10).filter(it -> it%2 ==0)
        .subscribe(it -> println("Filter: " + it));
        println();

        // First
        Observable.range(1, 10).first()
        .subscribe(it -> println("First: " + it));
        println();

        // Last
        Observable.range(1, 10).last()
        .subscribe(it -> println("Last: " + it));
        println();

        // Skip
        Observable.range(1, 10).skip(5)
        .subscribe(it -> println("Skip: " + it));
        println();

        // SkipLast
        Observable.range(1, 10).skipLast(5)
        .subscribe(it -> println("SkipLast: " + it));
        println();

        // Take
        Observable.range(1, 10).take(3)
        .subscribe(it -> println("Take: " + it));
        println();

        // TakeLast
        Observable.range(1, 10).takeLast(3)
        .subscribe(it -> println("TakeLast: " + it));
        println();

        // Merge
        Observable<Integer> odds = Observable.range(1, 20).filter(it -> it%2 !=0);
        Observable<Integer> evens = Observable.range(1, 20).filter(it -> it%2 ==0);
        Observable.merge(odds, evens).subscribe(it -> println("Merge: " + it));
        println();
    }
}
