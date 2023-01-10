package booking_restaurant_api.booking_restaurant_api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;

public class ReservationServiceTest {

	private static final Long RESTAURANT_ID = 5L;
	private static final Long RESERVATION_ID = 1L;
	private static final Long TURN_ID = 5L;

	private static final String LOCATOR = "Restaurante Salcedo6";
	private static final Long PERSON = 30L;
	private static final Date DATE = new Date();
	private static final String TURN_NAME = "TURN_12_004";

	private static final String NAME = "Foster";
	private static final String DESCRIPTION = "Especialista en comida americana";
	private static final String ADDRES = "Calle Santa Eulalia";
	private static final String IMAGE = "www.image.com";

	public static final List<Turn> TURN_LIST = new ArrayList<>();

	public static final Reservation RESERVATION = new Reservation();
	public static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	public static final Restaurant RESTAURANT = new Restaurant();
	public static final Turn TURN = new Turn();

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private TurnRepository turnRepository;

	@InjectMocks
	private ReservationServiceImpl reservationServiceImpl;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);

		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);

		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRES);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);

		TURN.setId(TURN_ID);
		TURN.setName(TURN_NAME);
		TURN.setRestaurant(RESTAURANT);

		RESERVATION.setDate(DATE);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setRestaurant(RESTAURANT);
		RESERVATION.setTurn(TURN_NAME);

	}

	// Test Unitario para un el método createReservation y todo lo que contiene
	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(Optional.empty());
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);

	}

	// Test Unitario para un error
	@Test(expected = BookingException.class)
	public void createReservationRestaurantFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	// Test Unitario para un error
	@Test(expected = BookingException.class)
	public void createReservationTurnFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.empty());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	// Test Unitario para un error
	@Test(expected = BookingException.class)
	public void createReservationFindByTurnAndRestaurantIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(Optional.of(RESERVATION));
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	// Test Unitario Internal Server Error
	@Test(expected = BookingException.class)
	public void createReservationInternalServerErrorTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(Optional.of(TURN));
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(Optional.empty());
		
		Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
		
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	// Test Unitario sobre el método getReservationById
	@Test
	public void getReservationByIdTest() throws BookingException {
		Mockito.when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.of(RESERVATION));
		reservationServiceImpl.getReservationById(RESERVATION_ID);
	}

	// Test Unitario sobre el método getReservationById cuando salte el error
	@Test(expected = BookingException.class)
	public void getReservationByIdTestError() throws BookingException {
		Mockito.when(reservationRepository.findById(RESERVATION_ID)).thenReturn(Optional.empty());
		reservationServiceImpl.getReservationById(RESERVATION_ID);
		fail();
	}

	// Test Unitario sobre el método getReservations que obtiene la lista de
	// reservas
	@Test
	public void getReservationsTest() throws BookingException {
		final Reservation reservation = new Reservation();
		Mockito.when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));
		final List<ReservationRest> response = reservationServiceImpl.getReservations();
		assertNotNull(response);
		assertFalse(response.isEmpty());
		assertEquals(response.size(), 1);
	}

}
