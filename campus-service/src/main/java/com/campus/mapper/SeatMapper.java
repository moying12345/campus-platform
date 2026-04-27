package com.campus.mapper;
import com.campus.pojo.Seat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SeatMapper {
    List<Seat> list();
    Seat getById(Long id);
    int insert(Seat seat);
    int update(Seat seat);
    int delete(Long id);
    List<Seat> listByRoomId(@Param("roomId") Long roomId);
}