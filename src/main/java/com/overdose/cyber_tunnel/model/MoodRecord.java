package com.overdose.cyber_tunnel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 心情记录表 —— 记录每次的心情值
 */
@Entity
@Table(name = "mood_record")
public class MoodRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户ID */
    @Column(name = "user_id", nullable = false)
    private Long userId = 1L;

    /** 心情值（1-100） */
    @Column(name = "mood_value", nullable = false)
    private Integer moodValue;

    /** 对应的表情符号 */
    @Column(name = "mood_emoji")
    private String moodEmoji;

    /** 记录时间 */
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    /** 创建时间 */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }

    public MoodRecord() {}

    public MoodRecord(Integer moodValue, String moodEmoji) {
        this.moodValue = moodValue;
        this.moodEmoji = moodEmoji;
        this.recordedAt = LocalDateTime.now();
    }

    // ====== Getter / Setter ======

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getMoodValue() { return moodValue; }
    public void setMoodValue(Integer moodValue) { this.moodValue = moodValue; }
    public String getMoodEmoji() { return moodEmoji; }
    public void setMoodEmoji(String moodEmoji) { this.moodEmoji = moodEmoji; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
