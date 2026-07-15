package com.overdose.cyber_tunnel;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    private final AtomicLong nextId = new AtomicLong(1);
    private final CopyOnWriteArrayList<MemoResponse> memos = new CopyOnWriteArrayList<>();

    @GetMapping
    public List<MemoResponse> listMemos() {
        return memos.stream()
                .sorted(Comparator.comparing(MemoResponse::createdAt).reversed())
                .toList();
    }

    @PostMapping
    public ResponseEntity<MemoResponse> createMemo(@RequestBody MemoRequest request) {
        String content = request == null || request.content() == null ? "" : request.content().trim();
        if (content.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        MemoResponse memo = new MemoResponse(nextId.getAndIncrement(), content, LocalDateTime.now());
        memos.add(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(memo);
    }

    public record MemoRequest(String content) {
    }

    public record MemoResponse(Long id, String content, LocalDateTime createdAt) {
    }
}
