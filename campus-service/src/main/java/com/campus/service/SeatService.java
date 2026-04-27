package com.campus.service;
import com.campus.pojo.Seat;
import java.util.List;

public interface SeatService {
    List<Seat> list();
    Seat getById(Long id);
    void add(Seat seat);
    void update(Seat seat);
    void delete(Long id);
    List<Seat> listByRoomId(Long roomId);
}