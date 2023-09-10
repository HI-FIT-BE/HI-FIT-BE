package hifit.be.user.dto.request;

import hifit.be.user.entity.Sarcopenia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SarcopeniaRequest {

    private Sarcopenia sarcopenia;
}
