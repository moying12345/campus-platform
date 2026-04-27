package com.campus.mapper;

import com.campus.pojo.Reservation;
import com.campus.pojo.ReservationRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReservationMapper {
    List<Reservation> findAll();
    void add(Reservation reservation);
    void update(Reservation reservation);
    void delete(Long id);
    Reservation findById(Long id);
    List<Reservation> findByDateRange(@Param("start") Date start, @Param("end") Date end, @Param("roomId") Long roomId);
    List<Reservation> findBySeatAndDate(@Param("seatId") Long seatId, @Param("reserveDate") Date reserveDate);
    List<ReservationRule> findRules();
    List<ReservationRule> findRulesByTimeSlot(@Param("timeSlot") String timeSlot);
}
