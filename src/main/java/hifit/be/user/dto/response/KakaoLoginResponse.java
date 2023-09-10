package hifit.be.user.dto.response;

import hifit.be.user.dto.request.KakaoAccount;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoLoginResponse {

    private Long id;
    private KakaoAccount kakaoAccount;
}
