package booking_restaurant_api.booking_restaurant_api.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

public class ReservationControllerTest {

	private static final Long RESTAURANT_ID = 1L;
	private static final Long RESERVATION_ID = 1L;
	private static final Long TURN_ID = 1L;

	private static final String LOCATOR = "Restaurante Salcedo6";
	private static final Long PERSON = 1L;
	private static final Date DATE = new Date();

	private static final String SUCCES_STATUS = "Succes";
	private static final String SUCCES_CODE = "200";
	private static final String OK = "OK";

	public static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	public static final ReservationRest RESERVATION_REST = new ReservationRest();

	@Mock
	ReservationService reservationService;

	@InjectMocks
	ReservationController reservationController;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);

		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);

		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
		
		RESERVATION_REST.setDate(DATE);
		RESERVATION_REST.setLocator(LOCATOR);
		RESERVATION_REST.setPerson(PERSON);
		RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		RESERVATION_REST.setTurnId(TURN_ID);
		
		Mockito.when(reservationService.getReservationById(RESERVATION_ID)).thenReturn(RESERVATION_REST);
	}

	@Test
	public void createReservationTest() throws BookingException {
		final BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);

		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LOCATOR);
	}

	@Test
	public void getReservationByIdTest() throws BookingException {
		final BookingResponse<ReservationRest> response = reservationController.getReservationById(RESERVATION_ID);

		assertEquals(response.getStatus(), SUCCES_STATUS);
		assertEquals(response.getCode(), SUCCES_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_REST);
	}
}
