package by.gurinovich.surveybotsnp.model.survey;

import by.gurinovich.surveybotsnp.model.common.AuditableEntity;
import by.gurinovich.surveybotsnp.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Survey extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String name;

    public String email;

    public Long score;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public SurveyState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    public User user;

    public Survey reset() {
        this.state = SurveyState.NAME;
        this.name = null;
        this.email = null;
        this.score = null;

        return this;
    }

    public Survey(User user, SurveyState state) {
        this.user = user;
        this.state = state;
    }

}
