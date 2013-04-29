package org.henri.endinsert;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.henri.endinsert.ImmutableJMH.NormalState;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.logic.BlackHole;

public class ImmutableJMHTest {

    private ImmutableJMH test = new ImmutableJMH();
    
    private NormalState state = new NormalState();
    
    private BlackHole hole = new BlackHole();
    
    @Before
    public void setup() {
        state.list = Arrays.asList(0, 1, 2);
    }
    
    @Test
    public void immutableTest() {
        assertArrayEquals(new Integer[] {1,2,3}, test.immutableMessage(state, hole).toArray(new Integer[3]));
    }
    
    @Test
    public void mutableTest() throws Exception {
        assertArrayEquals(new Integer[] {1,2,3}, test.mutableMessage(state, hole).toArray(new Integer[3]));
    }
    
    @Test
    public void benchTest() throws Exception {
        state.up();
        assertEquals(ImmutableJMH.COUNT, test.mutableMessage(state, hole).size());
        assertEquals(ImmutableJMH.COUNT, test.immutableMessage(state, hole).size());
    }
}
