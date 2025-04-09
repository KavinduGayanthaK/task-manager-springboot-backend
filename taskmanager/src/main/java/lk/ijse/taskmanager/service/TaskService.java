package lk.ijse.taskmanager.service;

import lk.ijse.taskmanager.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    boolean saveTask(TaskDTO taskDTO);
    TaskDTO getTaskById(long id);
    List<TaskDTO> getAllTasks();
}
