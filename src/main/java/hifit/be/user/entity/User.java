package hifit.be.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long socialId;
    private String name;
    private String phoneNumber;
    private String gender;
    private int age;
    private Double height;
    private Double weight;

    @Enumerated(EnumType.STRING)
    private Sarcopenia sarcopenia;
}
