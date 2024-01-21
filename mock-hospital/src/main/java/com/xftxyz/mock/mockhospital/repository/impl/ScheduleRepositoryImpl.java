package com.xftxyz.mock.mockhospital.repository.impl;

import com.xftxyz.mock.mockhospital.domain.Schedule;
import com.xftxyz.mock.mockhospital.repository.ScheduleRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private List<Schedule> schedules = new ArrayList<>();

    @Override
    public void save(Schedule schedule) {
        schedules.add(schedule);
    }

    @Override
    public void delete(String id) {
        schedules.removeIf(schedule -> schedule.getId().equals(id));
    }

    @Override
    public void update(Schedule schedule) {
        delete(schedule.getId());
        save(schedule);
    }

    @Override
    public List<Schedule> query(Predicate<Schedule> filter) {
        return schedules.stream().filter(filter).collect(Collectors.toList());
    }
}