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
    private int stamp;
    @Enumerated(EnumType.STRING)
    private Sarcopenia sarcopenia;

    public void addStamp() {

        this.stamp++;

        if (this.stamp > 20) {
            throw new IllegalArgumentException("[ERROR] 스탬프 최대 개수를 초과했습니다.");
        }
    }

    public void updateHeight(double height) {

        this.height = height;
    }

    public void updateWeight(double weight) {

        this.weight = weight;
    }

    public void updateSarcopenia(Sarcopenia sarcopenia) {

        this.sarcopenia = sarcopenia;
    }
}
