package com.xftxyz.mock.mockhospital.repository;

import com.xftxyz.mock.mockhospital.domain.Schedule;

import java.util.List;
import java.util.function.Predicate;

public interface ScheduleRepository {

    // 添加
    void save(Schedule schedule);

    // 删除
    void delete(String id);

    // 修改
    void update(Schedule schedule);

    // 查询（传入一个过滤器）
    List<Schedule> query(Predicate<Schedule> filter);
}
