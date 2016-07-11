import javafx.print.Collation;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by chenshuwang on 2016/7/11.
 */
public class Main {
    public static void main(String[] args) {
        /*
          Observable.from()
          Creates an observable from an Iterable, a Future, or an Array.
        */
        Observable.from(Stream.of(1, 2, 3, 4, 5).collect(Collectors.toSet()));
        Observable.from(Arrays.asList(1, 2, 3, 4, 5));

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
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("Error encountered: " + error.getMessage());
            }

            @Override
            public void onNext(String item) {
                System.out.println("(create) Next: " + item);
            }
        });

        // from
        /*
            Integer[] items = {1, 2, 3, 4, 5};
            Observable myObservable = Observable.from(items);
            myObservable.subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer item) {
                        System.out.println("(from) Next: " + item);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        System.out.println("Error encountered: " + error.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        System.out.println("Sequence complete");
                    }
                }
            );
         */
        Integer[] items = {1, 2, 3, 4, 5};
        Observable myObservable = Observable.from(items);
        myObservable
        .map(i -> String.valueOf((int)i*2))
        .subscribe(
            item -> System.out.println("(from) Next:" + item),
            error -> System.out.println("Error encountered: " + error),
            () -> System.out.println("Sequence complete.")
        );

        // just
        Observable.just(1, 2, 3, 4, 5)
        .map(i -> String.valueOf(i+1))
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onNext(String item) {
                System.out.println("(just) Next: " + item);
            }
        });
    }
}
