package com.campus.mapper;
import com.campus.pojo.StudyRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoom> list();
    StudyRoom getById(Long id);
    int insert(StudyRoom studyRoom);
    int update(StudyRoom studyRoom);
    int delete(Long id);
    int countTotalSeats(@Param("roomId") Long roomId);
    int countAvailableSeats(@Param("roomId") Long roomId);
}