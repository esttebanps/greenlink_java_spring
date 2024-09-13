package com.api.greenlink.data.entity;

import com.api.greenlink.auditing.AbstractAuditingEntity;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flowerpot_data")
public class FlowerpotData extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Temperature es obligatorio")
    @Column(name = "temperature")
    private String temperature;

    @NotBlank(message = "Humidity es obligatorio")
    @Column(name = "humidity")
    private String humidity;

    @NotBlank(message = "Sun es obligatorio")
    @Column(name = "sun")
    private String sun;

    @ManyToOne
    @JoinColumn(name = "flowerpot_id")
    private Flowerpot flowerpot;
}
