package com.campus.service.impl;
import com.campus.mapper.SeatMapper;
import com.campus.pojo.Seat;
import com.campus.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<Seat> list() {
        return seatMapper.list();
    }

    @Override
    public Seat getById(Long id) {
        return seatMapper.getById(id);
    }

    @Override
    public void add(Seat seat) {
        seatMapper.insert(seat);
    }

    @Override
    public void update(Seat seat) {
        seatMapper.update(seat);
    }

    @Override
    public void delete(Long id) {
        seatMapper.delete(id);
    }

    @Override
    public List<Seat> listByRoomId(Long roomId) {
        return seatMapper.listByRoomId(roomId);
    }
}