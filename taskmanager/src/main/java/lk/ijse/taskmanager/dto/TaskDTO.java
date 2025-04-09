package lk.ijse.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    @NotBlank
    private long id;
    @Size(max = 100, message = "Title cannot be more than 100 characters")
    private String title;
    @Size(max = 250, message = "Description cannot be more than 250 characters")
    private String description;
    private String status;
    @PastOrPresent(message = "Created date cannot be in the future ")
    private LocalDateTime createdAt;
}
