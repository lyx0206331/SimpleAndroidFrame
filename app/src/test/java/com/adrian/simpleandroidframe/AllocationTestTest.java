package com.adrian.simpleandroidframe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by qing on 2018/3/2 0002.
 */
public class AllocationTestTest {

    private AllocationTest allocationTest;

    @Before
    public void setUp() throws Exception {
        allocationTest = new AllocationTest();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void bestObjectAllocationExample() throws Exception {
        allocationTest.bestObjectAllocationExample(new int[]{12, 13});
    }

}