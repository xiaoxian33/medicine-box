package com.overdose.cyber_tunnel.service;

import com.overdose.cyber_tunnel.model.MedicationRecord;
import com.overdose.cyber_tunnel.repository.MedicationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationRecordService {

    private final MedicationRecordRepository repository;

    public MedicationRecordService(MedicationRecordRepository repository) {
        this.repository = repository;
    }

    /** 获取用户的所有记录 */
    @Transactional(readOnly = true)
    public List<MedicationRecord> findByUserId(Long userId) {
        return repository.findByUserIdOrderByTakenAtDesc(userId);
    }

    /** 获取所有公开记录 */
    @Transactional(readOnly = true)
    public List<MedicationRecord> findPublic() {
        return repository.findByPrivacyOrderByTakenAtDesc(MedicationRecord.Privacy.PUBLIC);
    }

    /** 新增一条服药记录 */
    @Transactional
    public MedicationRecord create(Long userId, String medicineName, Integer dosage,
                                    LocalDateTime takenAt, Boolean isCommon,
                                    MedicationRecord.Privacy privacy) {
        MedicationRecord record = new MedicationRecord();
        record.setUserId(userId);
        record.setMedicineName(medicineName);
        record.setDosage(dosage);
        record.setTakenAt(takenAt);
        record.setIsCommon(isCommon != null ? isCommon : false);
        record.setPrivacy(privacy != null ? privacy : MedicationRecord.Privacy.PRIVATE);
        record.setArchived(false);
        return repository.save(record);
    }

    /** 更新心得描述 */
    @Transactional
    public Optional<MedicationRecord> updateThoughts(Long id, String thoughts) {
        return repository.findById(id).map(record -> {
            record.setThoughts(thoughts);
            return repository.save(record);
        });
    }

    /** 归档（封存）用户所有未归档记录 */
    @Transactional
    public int archiveAll(Long userId) {
        List<MedicationRecord> records = repository.findByUserIdAndArchivedFalse(userId);
        for (MedicationRecord record : records) {
            record.setArchived(true);
            // 计算历时（从服药时间到现在的时长）
            long hours = ChronoUnit.HOURS.between(record.getTakenAt(), LocalDateTime.now());
            record.setDurationHours((double) hours);
        }
        repository.saveAll(records);
        return records.size();
    }

    /** 删除记录 */
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
