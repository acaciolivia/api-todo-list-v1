package service;

import lombok.RequiredArgsConstructor;
import model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Task createTasks(Task task) {
        return repository.save(task);
    }

    public List<Task> listAllTask() {
        return repository.findAll();
    }

    public ResponseEntity<Task> findTaskById(Long id){
        return repository.findById(id)
                .map(task -> ResponseEntity.ok()
                .body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Task> updateTask(Task task, Long id){
        return repository.findById(id)
                .map(taskToUpdated -> {
                    taskToUpdated.setTitle(task.getTitle());
                    taskToUpdated.setDescription(task.getDescription());
                    taskToUpdated.setDeadLine(task.getDeadLine());

                    Task updated = repository.save(taskToUpdated);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
