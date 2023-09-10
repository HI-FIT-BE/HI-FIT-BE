package hifit.be.user.service;

import hifit.be.user.entity.User;
import hifit.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findBySocialId(Long socialId) {

        return userRepository.findBySocialId(socialId);
    }

    public boolean existsBySocialId(Long socialId) {

        return userRepository.existsBySocialId(socialId);
    }
}
