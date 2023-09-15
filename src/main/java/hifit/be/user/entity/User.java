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
    private int age;
    private int stamp;


    public void addStamp() {

        this.stamp++;

        if (this.stamp > 20) {
            throw new IllegalArgumentException("[ERROR] 스탬프 최대 개수를 초과했습니다.");
        }
    }


}
