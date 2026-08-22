package com.overdose.cyber_tunnel.repository;

import com.overdose.cyber_tunnel.model.MoodRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 心情记录 —— 数据访问层
 */
@Repository
public interface MoodRecordRepository extends JpaRepository<MoodRecord, Long> {

    /** 按用户查询所有心情记录，按记录时间倒序 */
    List<MoodRecord> findByUserIdOrderByRecordedAtDesc(Long userId);
}
