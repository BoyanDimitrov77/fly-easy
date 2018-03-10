package com.fly.easy.flyeasy.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.HotelBookingDto;
import com.fly.easy.flyeasy.api.dto.HotelDto;
import com.fly.easy.flyeasy.api.dto.HotelRoomDto;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.db.model.BookStatus;
import com.fly.easy.flyeasy.db.model.Hotel;
import com.fly.easy.flyeasy.db.model.HotelBook;
import com.fly.easy.flyeasy.db.model.HotelRoom;
import com.fly.easy.flyeasy.db.model.Location;
import com.fly.easy.flyeasy.db.model.Picture;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.repository.HotelBookRepository;
import com.fly.easy.flyeasy.db.repository.HotelRepository;
import com.fly.easy.flyeasy.db.repository.LocationRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.service.interfaces.HotelService;
import com.fly.easy.flyeasy.service.interfaces.LocationService;
import com.fly.easy.flyeasy.service.interfaces.PaymentService;
import com.fly.easy.flyeasy.service.interfaces.PictureService;
import com.fly.easy.flyeasy.util.PictureUtil;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private HotelBookRepository hotelBookRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private PictureService pictureService;

	@Override
	public List<HotelDto> findHotelsByCurrentDestionation(long locationId) {

		Location location = locationRepository.findOne(locationId);

		if (location == null) {
			throw new ApiException("Location not found");
		}

		List<Hotel> hotels = hotelRepository.findByLocation(location);

		return hotels.stream().map(hotel -> HotelDto.of(hotel)).collect(Collectors.toList());
	}

	@Override
	public HotelDto getHotel(long hotelId) {

		Hotel hotel = hotelRepository.findOne(hotelId);
		if (hotel == null) {
			throw new ApiException("Hotel not found");
		}

		return HotelDto.of(hotel);
	}

	@Override
	public HotelBookingDto bookHotel(long hotelRoomId, long userId) {

		HotelRoom hotelRoom = hotelRepository.getHotelRoom(hotelRoomId);

		if (hotelRoom == null) {
			throw new ApiException("Hotel room not found");
		}

		User booker = userRepository.findOne(userId);

		HotelBook hotelBook = new HotelBook();
		hotelBook.setHotelRoom(hotelRoom);
		hotelBook.setUser(booker);
		hotelBook.setPayment(paymentService.createPaymentRecord(booker));
		hotelBook.setStatus(BookStatus.WAITING.toString());
		hotelBook.setTimestamp(new Date());

		HotelBook saveHotelBook = hotelBookRepository.saveAndFlush(hotelBook);

		return HotelBookingDto.of(saveHotelBook);
	}

	@Override
	public HotelBookingDto payHotel(long hotelBookId, BigDecimal amount) {

		HotelBook hotelBook = hotelBookRepository.findOne(hotelBookId);

		HotelBook hotelB = paymentService.payHotelBook(hotelBook, amount);

		HotelBook saveHotelBook = hotelBookRepository.saveAndFlush(hotelB);

		return HotelBookingDto.of(saveHotelBook);
	}

	@Override
	public HotelDto createHotel(HotelDto hotelDto) {

		Hotel hotel = new Hotel();
		hotel.setHotelName(hotelDto.getHotelName());
		hotel.setDescription(hotelDto.getDescription());
		hotel.setLocation(locationService.createLocation(hotelDto.getLocation().getName()));
		hotel.setHotelRooms(createHotelRoom(hotelDto.getHotelRooms(), hotel));
		hotel.setTimestamp(new Date());

		Hotel saveHotel = hotelRepository.saveAndFlush(hotel);

		return HotelDto.of(saveHotel);
	}

	private List<HotelRoom> createHotelRoom(List<HotelRoomDto> list, Hotel hotel) {

		return list.stream().map(hotelRoomDto -> {
			HotelRoom hotelRoom = new HotelRoom();
			hotelRoom.setHotel(hotel);
			hotelRoom.setPrice(hotelRoomDto.getPrice());
			hotelRoom.setTypeRoom(hotelRoomDto.getTypeRoom());

			return hotelRoom;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PictureDto> uploadHotelPicutures(long hotelId, MultipartFile[] files) throws IOException {

		Hotel hotel = hotelRepository.findOne(hotelId);

		Hotel savedHotel = setHotelPctures(files, hotel, Hotel::setHotelPictures);

		return savedHotel.getHotelPictures().stream().map(picture -> PictureDto.of(picture))
				.collect(Collectors.toList());
	}

	private Hotel setHotelPctures(MultipartFile[] files, Hotel hotel, BiConsumer<Hotel, List<Picture>> setter)
			throws IOException {

		List<PictureDto> pictureDtos = new ArrayList<>();

		for (MultipartFile file : files) {
			PictureDto savePicure = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(file),
					hotel.getHotelName());
			pictureDtos.add(savePicure);
		}

		List<Picture> hotelPictures = pictureDtos.stream()
				.map(pictureDto -> pictureService.getPictureById(pictureDto.getId())).collect(Collectors.toList());
		setter.accept(hotel, hotelPictures);

		Hotel saveHotel = hotelRepository.saveAndFlush(hotel);

		return saveHotel;
	}
}
