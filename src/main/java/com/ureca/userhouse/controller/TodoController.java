package com.ureca.userhouse.controller;

import com.ureca.userhouse.entity.Todo;
import com.ureca.userhouse.entity.Manager;
import com.ureca.userhouse.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/todo/api")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos(HttpSession session) {
        Manager manager = (Manager) session.getAttribute("member");
        if (manager == null) {
            return ResponseEntity.status(401).build();
        }
        List<Todo> todos = todoRepository.findByManager(manager);
        return ResponseEntity.ok(todos);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo, HttpSession session) {
        Manager manager = (Manager) session.getAttribute("member");
        if (manager == null) {
            return ResponseEntity.status(401).build();
        }
        todo.setManager(manager);
        Todo createdTodo = todoRepository.save(todo);
        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Map<String, String>> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todoDetails, HttpSession session) {
        Manager manager = (Manager) session.getAttribute("member");
        if (manager == null) {
            Map<String, String> result = new HashMap<>();
            result.put("code", "error");
            result.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(result);
        }

        Optional<Todo> todoOptional = todoRepository.findByIdAndManager(id, manager);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setContent(todoDetails.getContent());
            todo.setCompleted(todoDetails.isCompleted());
            todoRepository.save(todo);

            Map<String, String> result = new HashMap<>();
            result.put("code", "ok");
            result.put("message", "Todo 항목이 수정되었습니다.");
            return ResponseEntity.ok(result);
        } else {
            Map<String, String> result = new HashMap<>();
            result.put("code", "error");
            result.put("message", "Todo 항목을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(result);
        }
    }

    @DeleteMapping("/todos/{id}")
    @ResponseBody
    public Map<String, String> deleteTodo(@PathVariable("id") Long id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        Manager manager = (Manager) session.getAttribute("member");
        if (manager == null) {
            result.put("code", "error");
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        Optional<Todo> todoOptional = todoRepository.findByIdAndManager(id, manager);
        if (todoOptional.isPresent()) {
            todoRepository.deleteById(id);
            result.put("message", "삭제되었습니다.");
        } else {
            result.put("message", "찾을 수 없습니다.");
        }
        return result;
    }
}
