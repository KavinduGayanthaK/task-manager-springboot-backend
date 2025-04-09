package lk.ijse.taskmanager.controller;

import lk.ijse.taskmanager.dto.TaskDTO;
import lk.ijse.taskmanager.exception.DataPersistException;
import lk.ijse.taskmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveTask(@RequestBody TaskDTO taskDTO) {
        logger.info("Received request to save task: {}", taskDTO);

        try {
            System.out.println(taskDTO);
            boolean success = taskService.saveTask(taskDTO);

            if (success) {
                logger.info("Task saved successfully with details: {}", taskDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body("Task saved successfully");
            } else {
                logger.error("Failed to save task due to internal server issue for task: {}", taskDTO);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save task");
            }

        } catch (DataPersistException e) {
            logger.warn("DataPersistException while saving task: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid task data: " + e.getMessage());

        } catch (Exception e) {
            logger.error("Unexpected error occurred while saving task", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
        try {
            logger.info("Received request to get task by id: {}", taskId);
            TaskDTO taskById = taskService.getTaskById(taskId);
            if (taskById == null) {
                logger.error("Task with id {} not found", taskId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(taskById);
        } catch (DataPersistException e) {
            logger.warn("DataPersistException while getting task by id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while getting task by id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> getAllTasks() {
        try {
            logger.info("Received request to get tasks");
            return taskService.getAllTasks();
        }catch (DataPersistException e) {
            logger.warn("DataPersistException while getting tasks");
            return null;
        }catch (Exception e) {
            logger.error("Unexpected error occurred while getting tasks");
            return null;
        }
    }

    @PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTask(@RequestBody TaskDTO taskDTO) {}
}
