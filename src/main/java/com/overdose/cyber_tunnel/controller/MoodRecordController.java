package com.overdose.cyber_tunnel.controller;

import com.overdose.cyber_tunnel.model.MoodRecord;
import com.overdose.cyber_tunnel.service.MoodRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moods")
public class MoodRecordController {

    private final MoodRecordService service;

    public MoodRecordController(MoodRecordService service) {
        this.service = service;
    }

    /** GET /api/moods?userId=1 — 获取心情记录列表 */
    @GetMapping
    public ResponseEntity<List<MoodRecord>> listByUser(@RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    /** POST /api/moods — 记录心情 */
    @PostMapping
    public ResponseEntity<MoodRecord> create(@RequestBody CreateRequest request) {
        MoodRecord record = service.create(
                request.userId() != null ? request.userId() : 1L,
                request.moodValue(),
                request.moodEmoji()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    public record CreateRequest(Long userId, Integer moodValue, String moodEmoji) {}
}
