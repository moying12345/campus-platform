package com.campus.service.impl;

import com.campus.mapper.ReservationMapper;
import com.campus.pojo.Reservation;
import com.campus.pojo.ReservationRule;
import com.campus.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Reservation> list() {
        return reservationMapper.findAll();
    }

    @Override
    public void add(Reservation reservation) {
        reservationMapper.add(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        reservationMapper.update(reservation);
    }

    @Override
    public void delete(Long id) {
        reservationMapper.delete(id);
    }

    @Override
    public Reservation getById(Long id) {
        return reservationMapper.findById(id);
    }

    @Override
    public List<Reservation> listByDateRange(Date start, Date end, Long roomId) {
        return reservationMapper.findByDateRange(start, end, roomId);
    }

    @Override
    public List<ReservationRule> listRules() {
        return reservationMapper.findRules();
    }

    @Override
    public boolean checkConflict(Long seatId, Long roomId, Date reserveDate, String timeSlot, Integer duration) {
        // 查询同一座位同一日期的所有预约
        List<Reservation> reservations = reservationMapper.findBySeatAndDate(seatId, reserveDate);

        for (Reservation res : reservations) {
            // 如果状态是已取消或已完成，不算冲突
            if (res.getStatus() == 2 || res.getStatus() == 3) {
                continue;
            }

            // 检查时段是否冲突
            if (isTimeSlotConflict(res.getTimeSlot(), timeSlot, res.getDuration(), duration)) {
                return true;
            }
        }

        return false;
    }

    private boolean isTimeSlotConflict(String existingSlot, String newSlot, Integer existingDuration, Integer newDuration) {
        // 将时段转换为小时数进行比较
        int existingStart = getTimeSlotStartHour(existingSlot);
        int newStart = getTimeSlotStartHour(newSlot);

        int existingEnd = existingStart + (existingDuration != null ? existingDuration : 2);
        int newEnd = newStart + (newDuration != null ? newDuration : 2);

        // 检查时间是否重叠
        return newStart < existingEnd && newEnd > existingStart;
    }

    private int getTimeSlotStartHour(String timeSlot) {
        if ("上午".equals(timeSlot)) {
            return 8;
        } else if ("下午".equals(timeSlot)) {
            return 14;
        } else if ("晚上".equals(timeSlot)) {
            return 19;
        }
        return 0;
    }

    @Override
    public String checkReservationRule(String timeSlot, Integer duration) {
        // 获取该时段的规则
        List<ReservationRule> rules = reservationMapper.findRulesByTimeSlot(timeSlot);

        if (rules != null && !rules.isEmpty()) {
            ReservationRule rule = rules.get(0);

            // 检查最大预约时长
            if (rule.getMaxDuration() != null && duration > rule.getMaxDuration()) {
                return "该时段最大预约时长为 " + rule.getMaxDuration() + " 小时";
            }
        }

        // 默认规则
        if (duration < 1) {
            return "预约时长不能少于 1 小时";
        }
        if (duration > 4) {
            return "单次预约时长不能超过 4 小时";
        }

        return null;
    }

    @Override
    public String checkCancelRule(Reservation reservation) {
        // 检查预约时间是否已过
        if (reservation.getReserveDate().before(new Date())) {
            return "预约时间已过，无法取消";
        }

        // 获取取消规则
        List<ReservationRule> rules = reservationMapper.findRulesByTimeSlot(reservation.getTimeSlot());
        if (rules != null && !rules.isEmpty()) {
            ReservationRule rule = rules.get(0);
            if (rule.getCancelAdvanceHours() != null) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR_OF_DAY, rule.getCancelAdvanceHours());
                if (reservation.getReserveDate().before(cal.getTime())) {
                    return "需要至少提前 " + rule.getCancelAdvanceHours() + " 小时取消预约";
                }
            }
        }

        // 默认规则：至少提前 2 小时取消
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 2);
        if (reservation.getReserveDate().before(cal.getTime())) {
            return "需要至少提前 2 小时取消预约";
        }

        return null;
    }
}
