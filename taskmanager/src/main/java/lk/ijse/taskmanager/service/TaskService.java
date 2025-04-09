package lk.ijse.taskmanager.service;

import lk.ijse.taskmanager.dto.TaskDTO;

public interface TaskService {
    boolean saveTask(TaskDTO taskDTO);
    TaskDTO getTaskById(long id);
}
