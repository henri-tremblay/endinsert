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
import org.openjdk.jmh.logic.BlackHole;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
public class ImmutableJMH {

    static final int COUNT = 5_000_000;

    @State
    public static class NormalState {
        List<Integer> list;

        @Setup(Level.Trial)
        public void up() {
            list = new ArrayList<Integer>(COUNT);
            for (int i = 0; i < COUNT; i++) {
                list.add(i);
            }
        }
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public List<Integer> immutableMessage(NormalState s, BlackHole hole) {
        List<Integer> result = new ArrayList<Integer>(s.list.size());
        for (Integer e : s.list) {
            result.add(e + 1);
        }
        hole.consume(result);
        s.list = result;
        return result;
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public List<Integer> mutableMessage(NormalState s, BlackHole hole) {
        for (int i = 0; i < s.list.size(); i++) {
            s.list.set(i, s.list.get(i) + 1);
        }
        hole.consume(s.list);
        return s.list;
    }

}
