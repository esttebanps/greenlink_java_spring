package com.api.greenlink.flowerpot.entity;

import com.api.greenlink.auditing.AbstractAuditingEntity;
import com.api.greenlink.user.entity.UserGreenlink;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity @Table(name = "flowerpot")
@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Flowerpot extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mac_address")
    private String macAddress;

    private String name;

    @Column(name = "red_ssid")
    private String redSsid;

    @Column(name = "red_password")
    private String redPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserGreenlink user;

    @Override
    public String toString() {
        return "Flowerpot{" +
                "id=" + id +
                '}';
    }
}
