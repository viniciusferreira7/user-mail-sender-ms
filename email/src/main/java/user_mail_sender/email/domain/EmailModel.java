package user_mail_sender.email.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import user_mail_sender.email.domain.enums.EmailStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "email_to", nullable = false)
    private String emailTo;

    @Column(name = "email_from", nullable = false)
    private String emailFrom;

    @Column(nullable = false)
    private String subject;

    @Column(length = 500, nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;

    @CreationTimestamp
    @Column(name = "send_at", updatable = false, nullable = false)
    private LocalDateTime sendAt;
}