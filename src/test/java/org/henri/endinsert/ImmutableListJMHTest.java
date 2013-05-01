package org.henri.endinsert;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class ImmutableListJMHTest {

    private ImmutableListJMH test = new ImmutableListJMH();
    
    private ImmutableListJMH.NormalState state = new ImmutableListJMH.NormalState();
    
    @Before
    public void setup() {
        state.list = Arrays.asList(0, 1, 2);
    }
    
    @Test
    public void immutableTest() {
        assertArrayEquals(new Integer[] {1,2,3}, test.immutableMessage(state).toArray(new Integer[3]));
    }
    
    @Test
    public void mutableTest() throws Exception {
        assertArrayEquals(new Integer[] {1,2,3}, test.mutableMessage(state).toArray(new Integer[3]));
    }
    
    @Test
    public void benchTest() throws Exception {
        state.up();
        assertEquals(ImmutableListJMH.COUNT, test.mutableMessage(state).size());
        assertEquals(ImmutableListJMH.COUNT, test.immutableMessage(state).size());
    }
}
