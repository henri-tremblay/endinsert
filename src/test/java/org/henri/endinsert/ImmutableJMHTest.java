package org.henri.endinsert;

import static org.junit.Assert.*;

import org.henri.endinsert.ImmutableJMH.NormalState;
import org.junit.Before;
import org.junit.Test;

public class ImmutableJMHTest {

    private ImmutableJMH test = new ImmutableJMH();
    
    private NormalState state = new NormalState();
    
    @Before
    public void setup() {
        state.list = new int[] {0, 1, 2};
    }
    
    @Test
    public void immutableTest() {
        assertArrayEquals(new int[] {1,2,3}, test.immutableMessage(state));
    }
    
    @Test
    public void mutableTest() throws Exception {
        assertArrayEquals(new int[] {1,2,3}, test.mutableMessage(state));
    }
    
    @Test
    public void benchTest() throws Exception {
        state.up();
        assertEquals(ImmutableJMH.COUNT, test.mutableMessage(state).length);
        assertEquals(ImmutableJMH.COUNT, test.immutableMessage(state).length);
    }
}
