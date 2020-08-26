package parking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {
    @Mock
    Car car;
    @Mock
    CarDao carDao;
    @InjectMocks
    VipParkingStrategy vipParkingStrategy;

    @Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLot.isFull()).thenReturn(true);
        when(parkingLot.getName()).thenReturn("ka");
        parkingLots.add(parkingLot);

        Car car = spy(new Car("A11111"));
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(true);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        vipParkingStrategy.carDao = carDao;
        //when
        Receipt receipt = vipParkingStrategy.park(parkingLots, car);
        //then
        verify(vipParkingStrategy, times(1)).park(any(), any());
        assertEquals(receipt.getParkingLotName(), "ka");
        assertEquals(receipt.getCarName(), "A11111");
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLot.isFull()).thenReturn(true);
        when(parkingLot.getName()).thenReturn("ka");
        parkingLots.add(parkingLot);

        Car car = spy(new Car("A11111"));
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        vipParkingStrategy.carDao = carDao;
        //when
        Receipt receipt = vipParkingStrategy.park(parkingLots, car);
        //then
        verify(vipParkingStrategy, times(1)).park(any(), any());
        assertEquals(receipt.getParkingLotName(), ParkingStrategy.NO_PARKING_LOT);
        assertEquals(receipt.getCarName(), "A11111");
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        ParkingLot parkingLot = mock(ParkingLot.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLot.isFull()).thenReturn(true);
        when(parkingLot.getName()).thenReturn("ka");
        parkingLots.add(parkingLot);

        Car car = spy(new Car("A11111"));
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        vipParkingStrategy.carDao = carDao;
        //when
        Receipt receipt = vipParkingStrategy.park(parkingLots, car);
        //then
        verify(vipParkingStrategy, times(1)).park(any(), any());
        assertEquals(receipt.getParkingLotName(), ParkingStrategy.NO_PARKING_LOT);
        assertEquals(receipt.getCarName(), "A11111");
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(car.getName()).thenReturn("xxx");
        when(carDao.isVip(anyString())).thenReturn(true);
        //when
        boolean allowOverPark = this.vipParkingStrategy.isAllowOverPark(this.car);
        //then
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(car.getName()).thenReturn("A");
        when(carDao.isVip(anyString())).thenReturn(false);
        //when
        boolean allowOverPark = this.vipParkingStrategy.isAllowOverPark(this.car);
        //then
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not PowerMock) and @InjectMocks
         */
        //given
        when(car.getName()).thenReturn("xxx");
        when(carDao.isVip(anyString())).thenReturn(false);
        //when
        boolean allowOverPark = this.vipParkingStrategy.isAllowOverPark(this.car);
        //then
        assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
