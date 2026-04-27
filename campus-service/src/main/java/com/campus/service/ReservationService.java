package com.campus.service;

import com.campus.pojo.Reservation;
import com.campus.pojo.ReservationRule;
import java.util.Date;
import java.util.List;

public interface ReservationService {
    List<Reservation> list();
    void add(Reservation reservation);
    void update(Reservation reservation);
    void delete(Long id);
    Reservation getById(Long id);
    List<Reservation> listByDateRange(Date start, Date end, Long roomId);
    List<ReservationRule> listRules();
    boolean checkConflict(Long seatId, Long roomId, Date reserveDate, String timeSlot, Integer duration);
    String checkReservationRule(String timeSlot, Integer duration);
    String checkCancelRule(Reservation reservation);
}
