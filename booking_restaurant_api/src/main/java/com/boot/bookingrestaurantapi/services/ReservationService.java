package com.boot.bookingrestaurantapi.services;

import java.util.List;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;


public interface ReservationService {
	
	ReservationRest getReservationById(Long reservationId)throws  BookingException;
	
	public List<ReservationRest> getReservations() throws BookingException;
	
	String createReservation(CreateReservationRest createReservationRest)throws BookingException;
	
}
