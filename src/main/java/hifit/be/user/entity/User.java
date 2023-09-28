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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer age;
    private int point;

    public void updatePoint(int point) {

        this.point += point;
    }

    public void updateAgeGender(Integer age, Gender gender) {

        this.age = age;
        this.gender = gender;
    }
}
