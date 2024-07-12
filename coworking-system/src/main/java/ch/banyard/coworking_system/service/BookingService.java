package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.Booking;
import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.BookingDTO;
import ch.banyard.coworking_system.model.mapper.BookingMapper;
import ch.banyard.coworking_system.model.request.RequestBooking;
import ch.banyard.coworking_system.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final BookingMapper bookingMapper;

	public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper) {
		this.bookingRepository = bookingRepository;
		this.bookingMapper = bookingMapper;
	}

	public List<BookingDTO> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookingMapper.mapBookingsToDtoList(bookings);
	}

	public BookingDTO getBookingById(Long id) {
		Booking booking = bookingRepository.findById(id).orElseThrow();
		return bookingMapper.mapBookingToDTO(booking);
	}

	public BookingDTO createBooking(BookingDTO bookingDTO, CoworkingUser coworkingUser) {
		Booking booking = bookingMapper.mapsBookingDtoToEntity(bookingDTO, coworkingUser);
		if  (bookingRepository.findByName(booking.getName()).isPresent()) {
			throw new IllegalArgumentException("Booking already exists");
		}
		booking.setCoworkingUser(coworkingUser);
		bookingRepository.save(booking);
		return bookingMapper.mapBookingToDTO(booking);
	}

	public BookingDTO updateBooking(Long id, RequestBooking requestBooking) {
		if  (bookingRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Booking does not exist");
		}
		Booking existingBooking = bookingRepository.findById(id).orElseThrow();
		existingBooking.setName(requestBooking.getName());
		existingBooking.setDate(requestBooking.getDate());
		existingBooking.setDay(requestBooking.getDay());
		existingBooking.setStatus(requestBooking.getStatus());
		bookingRepository.save(existingBooking);
		return bookingMapper.mapBookingToDTO(existingBooking);
	}

	public void deleteBooking(Long id) {
		if (bookingRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Booking does not exist");
		}
		bookingRepository.deleteById(id);
	}
}
