package org.henri.endinsert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.BenchmarkType;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
public class ImmutableListJMH {

    static final int COUNT = 5_000_000;

    @State
    public static class NormalState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void up() {
            list = new ArrayList<>(COUNT);
            for (int i = 0; i < COUNT; i++) {
                list.add(i);
            }
        }
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public List<Integer> immutableMessage(NormalState s) {
        List<Integer> result = new ArrayList<>(s.list.size());
        for (int i = 0; i < s.list.size(); i++) {
            result.add(s.list.get(i) + 1);
        }
        s.list = result;
        return result;
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public List<Integer> mutableMessage(NormalState s) {
        for (int i = 0; i < s.list.size(); i++) {
            s.list.set(i, s.list.get(i) + 1);
        }
        return s.list;
    }

}
