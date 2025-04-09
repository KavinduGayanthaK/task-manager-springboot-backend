package lk.ijse.taskmanager.util;

import lk.ijse.taskmanager.dto.TaskDTO;
import lk.ijse.taskmanager.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapping {


    private final ModelMapper modelMapper;

    public Mapping(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public TaskEntity toTaskEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, TaskEntity.class);
    }

    public TaskDTO toTaskDTO(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDTO.class);
    }

    public List<TaskDTO> toTaskDTOList(List<TaskEntity> taskEntities) {
        return taskEntities.stream().map(this::toTaskDTO).collect(Collectors.toList());
    }
}
