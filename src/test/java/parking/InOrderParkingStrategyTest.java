package parking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

    @Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
         * With using Mockito to mock the input parameter */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = mock(Car.class);
        when(parkingLot.getName()).thenReturn("k");
        when(car.getName()).thenReturn("h");
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        //when
        Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot, car);
        //then
        assertEquals(receipt.getCarName(), "h");
        assertEquals(receipt.getParkingLotName(), "k");
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        //given
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("h");
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        //when
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(car);
        //then
        assertEquals(receipt.getCarName(), "h");
        assertEquals(receipt.getParkingLotName(), ParkingStrategy.NO_PARKING_LOT);
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        //given
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("h");
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        //when
        Receipt receipt = inOrderParkingStrategy.park(null, car);
        //then
        verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(any());
        assertEquals(receipt.getCarName(), "h");
        assertEquals(receipt.getParkingLotName(), ParkingStrategy.NO_PARKING_LOT);

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = mock(Car.class);
        when(parkingLot.getName()).thenReturn("k");
        when(car.getName()).thenReturn("h");
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        //when
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);
        //then
        verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
        assertEquals(receipt.getParkingLotName(), "k");
        assertEquals(receipt.getCarName(), "h");
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = mock(Car.class);
        when(parkingLot.getName()).thenReturn("k");
        when(car.getName()).thenReturn("h");
        when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        //when
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);
        //then
        verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(any());
        assertEquals(receipt.getCarName(), "h");
        assertEquals(receipt.getParkingLotName(), ParkingStrategy.NO_PARKING_LOT);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        ParkingLot parkingLot1 = mock(ParkingLot.class);
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("h");
        when(parkingLot.getName()).thenReturn("k");
        when(parkingLot1.getName()).thenReturn("k1");
        when(parkingLot.isFull()).thenReturn(true);
        when(parkingLot1.isFull()).thenReturn(false);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot);
        parkingLots.add(parkingLot1);
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        //when
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);
        //then
        verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
        verify(inOrderParkingStrategy, times(0)).createNoSpaceReceipt(any());
        assertEquals(receipt.getParkingLotName(), "k1");
    }


}
