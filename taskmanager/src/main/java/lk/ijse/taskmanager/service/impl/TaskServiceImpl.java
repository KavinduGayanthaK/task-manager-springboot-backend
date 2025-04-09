package lk.ijse.taskmanager.service.impl;

import lk.ijse.taskmanager.dto.TaskDTO;
import lk.ijse.taskmanager.entity.TaskEntity;
import lk.ijse.taskmanager.exception.NotFoundException;
import lk.ijse.taskmanager.repository.TaskRepository;
import lk.ijse.taskmanager.service.TaskService;
import lk.ijse.taskmanager.util.Mapping;
import lk.ijse.taskmanager.util.idGenerator.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final Mapping mapping;
    private final IdGenerator idGenerator;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskServiceImpl(TaskRepository taskRepository, Mapping mapping, IdGenerator idGenerator) {
        this.taskRepository = taskRepository;
        this.mapping = mapping;
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean saveTask(TaskDTO taskDTO) {
        try {
            taskDTO.setId(idGenerator.generateTaskId());
            logger.info("Saving task: {}", taskDTO.getTitle());
            taskRepository.save(mapping.toTaskEntity(taskDTO));
            logger.info("Task saved successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to save task", e);
            return false;
        }

    }

    @Override
    public TaskDTO getTaskById(long id) {
        try {
            logger.info("Retrieving task by id: {}", id);
            TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found: " + id));
            return mapping.toTaskDTO(taskEntity);
        }catch (Exception e) {
            logger.error("Failed to get task by id", e);
            return null;
        }
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        try {
            logger.info("Retrieving all tasks");
            List<TaskEntity> taskEntities = taskRepository.findAll();
            return mapping.toTaskDTOList(taskEntities);
        }catch (Exception e) {
            logger.error("Failed to get tasks", e);
            return null;
        }
    }

    @Override
    public boolean updateTask(TaskDTO taskDTO, long taskId) {
        try {
            logger.info("Updating task: {}", taskDTO);
            TaskEntity existTaskEntity = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found: " + taskId));
            existTaskEntity.setTitle(taskDTO.getTitle());
            existTaskEntity.setDescription(taskDTO.getDescription());
            existTaskEntity.setStatus(taskDTO.getStatus());
            existTaskEntity.setCreatedAt(taskDTO.getCreatedAt());
            taskRepository.save(existTaskEntity);
            logger.info("Task updated successfully.");
            return true;
        }catch (Exception e) {
            logger.error("Failed to update task", e);
            return false;
        }
    }

}
