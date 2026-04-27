package com.campus.service.impl;

import com.campus.mapper.StudyRoomMapper;
import com.campus.pojo.StudyRoom;
import com.campus.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudyRoomServiceImpl implements StudyRoomService {

    @Autowired
    private StudyRoomMapper studyRoomMapper;

    @Override
    public List<StudyRoom> list() {
        return studyRoomMapper.list();
    }

    @Override
    public StudyRoom getById(Long id) {
        return studyRoomMapper.getById(id);
    }

    @Override
    public void add(StudyRoom studyRoom) {
        studyRoomMapper.insert(studyRoom);
    }

    @Override
    public void update(StudyRoom studyRoom) {
        studyRoomMapper.update(studyRoom);
    }

    @Override
    public void delete(Long id) {
        studyRoomMapper.delete(id);
    }

    @Override
    public int countTotalSeats(Long roomId) {
        return studyRoomMapper.countTotalSeats(roomId);
    }

    @Override
    public int countAvailableSeats(Long roomId) {
        return studyRoomMapper.countAvailableSeats(roomId);
    }
}