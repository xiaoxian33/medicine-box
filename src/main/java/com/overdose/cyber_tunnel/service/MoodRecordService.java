package com.overdose.cyber_tunnel.service;

import com.overdose.cyber_tunnel.model.MoodRecord;
import com.overdose.cyber_tunnel.repository.MoodRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoodRecordService {

    private final MoodRecordRepository repository;

    public MoodRecordService(MoodRecordRepository repository) {
        this.repository = repository;
    }

    /** 获取用户所有心情记录 */
    @Transactional(readOnly = true)
    public List<MoodRecord> findByUserId(Long userId) {
        return repository.findByUserIdOrderByRecordedAtDesc(userId);
    }

    /** 记录心情 */
    @Transactional
    public MoodRecord create(Long userId, Integer moodValue, String moodEmoji) {
        MoodRecord record = new MoodRecord(moodValue, moodEmoji);
        record.setUserId(userId);
        return repository.save(record);
    }
}
