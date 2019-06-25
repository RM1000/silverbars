package com.silverbars;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class TestLiveOrderBoard {

    private LiveOrderBoard liveOrderBoard;

    @Before
    public void setUp(){
        liveOrderBoard = new LiveOrderBoard();
    }


    @Test
    public void testRegisterOrder(){
        Assert.assertTrue(liveOrderBoard.register(new Order("one",new BigDecimal(2.0),new BigDecimal(3.0),Type.SELL)));
        Assert.assertTrue(liveOrderBoard.register(new Order("two",new BigDecimal(2.0),new BigDecimal(3.0),Type.BUY)));
        Assert.assertTrue(liveOrderBoard.register(new Order("two",new BigDecimal(2.0),new BigDecimal(3.0),Type.BUY)));
    }

    @Test
    public void  testRemoveOrder(){
        liveOrderBoard.register(new Order("one",new BigDecimal(2.0),new BigDecimal(3.0),Type.SELL));
        Assert.assertTrue(liveOrderBoard.cancel(new Order("one",new BigDecimal(2.0),new BigDecimal(3.0),Type.SELL)));
        Assert.assertFalse(liveOrderBoard.cancel(new Order("one",new BigDecimal(2.0),new BigDecimal(3.0),Type.SELL)));
        Assert.assertFalse(liveOrderBoard.cancel(new Order("two",new BigDecimal(3.0),new BigDecimal(3.0),Type.SELL)));
    }

    @Test
    public void testLiveOrderBoard_SingleQuantities(){
        liveOrderBoard.register(new Order("two",new BigDecimal(2.0),new BigDecimal(20.0),Type.BUY));
        liveOrderBoard.register(new Order("one",new BigDecimal(1.0),new BigDecimal(10.0),Type.BUY));
        liveOrderBoard.register(new Order("three",new BigDecimal(3.0),new BigDecimal(30.0),Type.BUY));

        liveOrderBoard.register(new Order("two",new BigDecimal(2.0),new BigDecimal(20.0),Type.SELL));
        liveOrderBoard.register(new Order("three",new BigDecimal(1.0),new BigDecimal(10.0),Type.SELL));
        liveOrderBoard.register(new Order("one",new BigDecimal(3.0),new BigDecimal(30.0),Type.SELL));

        List<Summary> summaries = liveOrderBoard.getLiveOrderBoard();

        Assert.assertEquals(summaries.get(0),new Summary(new BigDecimal(1.0),new BigDecimal(10.0),Type.SELL));
        Assert.assertEquals(summaries.get(1),new Summary(new BigDecimal(2.0),new BigDecimal(20.0),Type.SELL));
        Assert.assertEquals(summaries.get(2),new Summary(new BigDecimal(3.0),new BigDecimal(30.0),Type.SELL));

        Assert.assertEquals(summaries.get(3),new Summary(new BigDecimal(3.0),new BigDecimal(30.0),Type.BUY));
        Assert.assertEquals(summaries.get(4),new Summary(new BigDecimal(2.0),new BigDecimal(20.0),Type.BUY));
        Assert.assertEquals(summaries.get(5),new Summary(new BigDecimal(1.0),new BigDecimal(10.0),Type.BUY));
    }

    @Test
    public void testLiveOrderBoard_AddedQuantities(){
        liveOrderBoard.register(new Order("two",new BigDecimal(2.0),new BigDecimal(20.0),Type.BUY));
        liveOrderBoard.register(new Order("three",new BigDecimal(3.0),new BigDecimal(30.0),Type.BUY));
        liveOrderBoard.register(new Order("four",new BigDecimal(3.0),new BigDecimal(30.0),Type.BUY));
        liveOrderBoard.register(new Order("three",new BigDecimal(3.0),new BigDecimal(30.0),Type.BUY));

        liveOrderBoard.register(new Order("three",new BigDecimal(1.0),new BigDecimal(10.0),Type.SELL));
        liveOrderBoard.register(new Order("one",new BigDecimal(3.0),new BigDecimal(30.0),Type.SELL));
        liveOrderBoard.register(new Order("two",new BigDecimal(3.0),new BigDecimal(30.0),Type.SELL));

        List<Summary> summaries = liveOrderBoard.getLiveOrderBoard();

        Assert.assertEquals(summaries.get(0),new Summary(new BigDecimal(1.0),new BigDecimal(10.0),Type.SELL));
        Assert.assertEquals(summaries.get(1),new Summary(new BigDecimal(3.0),new BigDecimal(60.0),Type.SELL));

        Assert.assertEquals(summaries.get(2),new Summary(new BigDecimal(3.0),new BigDecimal(90.0),Type.BUY));
        Assert.assertEquals(summaries.get(3),new Summary(new BigDecimal(2.0),new BigDecimal(20.0),Type.BUY));

    }

}
