package ch.banyard.coworking_system.service;

import ch.banyard.coworking_system.model.Booking;
import ch.banyard.coworking_system.model.CoworkingUser;
import ch.banyard.coworking_system.model.dto.BookingDTO;
import ch.banyard.coworking_system.model.mapper.BookingMapper;
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
		Booking booking = bookingMapper.mapsBookingDtoToEntity(bookingDTO);
		if  (bookingRepository.findById(booking.getId()).isPresent()) {
			throw new IllegalArgumentException("Booking already exists");
		}
		booking.setCoworkingUser(coworkingUser);
		bookingRepository.save(booking);
		return bookingMapper.mapBookingToDTO(booking);
	}

	public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
		Booking booking = bookingMapper.mapsBookingDtoToEntity(bookingDTO);
		if  (bookingRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Booking does not exist");
		}
		bookingRepository.save(booking);
		return bookingMapper.mapBookingToDTO(booking);
	}

	public void deleteBooking(Long id) {
		if (bookingRepository.findById(id).isEmpty()) {
			throw new IllegalArgumentException("Booking does not exist");
		}
		bookingRepository.deleteById(id);
	}
}
