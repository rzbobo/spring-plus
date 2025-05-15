package org.example.expert.domain.todo.controller;

import java.time.LocalDate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // Lv1. 코드 개선 퀴즈 - @Transactional의 이해
    // 에러가 발생하지 않고 정상적으로 할 일을 저장 할 수 있도록 코드를 수정해주세요.
    // 에러 내용을 살펴보면, transcational 설정이 읽기 전용으로 되어 있기 때문에 발생하는 에러이므로
    // service 단에 save 메서드 부분을 transcational 설정을 readonly =false로 설정해줌.
    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String weather,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate
    ) {
        return ResponseEntity.ok(todoService.getTodos(page, size, weather, startDate, endDate));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    // 🚨 기획자의 긴급 요청이 왔어요!
    // 아래의 요구사항에 맞춰 기획 요건에 대응할 수 있는 코드를 작성해주세요.
    // - 할 일 검색 시 `weather` 조건으로도 검색할 수 있어야해요.
    //     - `weather` 조건은 있을 수도 있고, 없을 수도 있어요!
    // - 할 일 검색 시 수정일 기준으로 기간 검색이 가능해야해요.
    //     - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있어요!
    // - JPQL을 사용하고, 쿼리 메소드명은 자유롭게 지정하되 너무 길지 않게 해주세요.
    //
    // <aside>
    // 💡 필요할 시, 서비스 단에서 if문을 사용해 여러 개의 쿼리(JPQL)를 사용하셔도 좋습니다.
    //
    // </aside>

}
