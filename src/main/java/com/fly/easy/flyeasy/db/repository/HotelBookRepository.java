package com.fly.easy.flyeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fly.easy.flyeasy.db.model.HotelBook;

public interface HotelBookRepository extends JpaRepository<HotelBook, Long> {

	@Query("SELECT hb FROM HotelBook hb WHERE hb.user.id =:userId")
	List<HotelBook> findAllUserHotelBooked(@Param("userId") long userId);
}
