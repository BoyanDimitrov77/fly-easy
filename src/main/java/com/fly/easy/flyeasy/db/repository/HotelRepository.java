package com.fly.easy.flyeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fly.easy.flyeasy.db.model.Hotel;
import com.fly.easy.flyeasy.db.model.HotelRoom;
import com.fly.easy.flyeasy.db.model.Location;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	List<Hotel> findByLocation(Location location);

	@Query("SELECT hr FROM Hotel h JOIN h.hotelRooms hr WHERE hr.id =:hotelRoomId")
	HotelRoom getHotelRoom(@Param("hotelRoomId") long hotelRoomId);

}
