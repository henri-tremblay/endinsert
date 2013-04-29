package org.henri.endinsert;

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
public class ImmutableJMH {

    static final int COUNT = 5_000_000;

    @State
    public static class NormalState {
        int[] list;

        @Setup(Level.Trial)
        public void up() {
            list = new int[COUNT];
            for (int i = 0; i < COUNT; i++) {
                list[i] = i;
            }
        }
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public int[] baseline(NormalState s) {
        return s.list;
    }
    
    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public int[] immutableMessage(NormalState s) {
        int[] result = new int[s.list.length];
        for (int i = 0; i < s.list.length; i++) {
            result[i] = s.list[i] + 1;
        }
        s.list = result;
        return result;
    }

    @GenerateMicroBenchmark(BenchmarkType.AverageTimePerOp)
    public int[] mutableMessage(NormalState s) {
        for (int i = 0; i < s.list.length; i++) {
            s.list[i] = s.list[i] + 1;
        }
        return s.list;
    }

}
