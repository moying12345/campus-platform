package com.campus.service;

import com.campus.pojo.StudyRoom;
import java.util.List;

public interface StudyRoomService {
    List<StudyRoom> list();
    StudyRoom getById(Long id);
    void add(StudyRoom studyRoom);
    void update(StudyRoom studyRoom);
    void delete(Long id);
    int countTotalSeats(Long roomId);
    int countAvailableSeats(Long roomId);
}