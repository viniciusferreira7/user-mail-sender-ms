package user_mail_sender.email.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import user_mail_sender.email.domain.enums.EmailStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String to;

    @Column(nullable = false)
    private String from;

    @Column(nullable = false)
    private String subject;

    @Column(length = 500, nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;

    @CreationTimestamp
    @Column(name = "send_at", updatable = false)
    private LocalDateTime sendAt;
}