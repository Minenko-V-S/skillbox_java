package main.services;

import main.config.AppProperties;
import main.model.Users;
import main.model.dto.AuthorizedUserDTO;
import main.repositories.PostsRepository;
import main.repositories.UsersRepository;
import main.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Service
public class UserAuthService {
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostsRepository postsRepository;

    public UserAuthService() {
    }

    public ResponseEntity<?> checkUserIsAuthorized() {
        Optional<Users> userOptional = getAuthorizedUser();

        if (userOptional.isEmpty())
            return ResponseEntity.ok(APIResponse.error());

        Users user = userOptional.get();

        //log.info(String.format("User '%s' is authenticated.", user));

        AuthorizedUserDTO authorizedUser = getAuthorizedUserDTO(user);

        return ResponseEntity.ok(APIResponse.ok("user", authorizedUser));
    }

    public boolean isAuthorized() {
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        if (!appProperties.getSessions().containsKey(sessionId)) {
           // log.info(String.format("Unauthorized user. Session '%s' not found.", sessionId));
            return false;
        }

       // log.info(String.format("Authorized user. Session '%s' found.", sessionId));
        return true;
    }

    private AuthorizedUserDTO getAuthorizedUserDTO(Users user) {
        AuthorizedUserDTO authorizedUser = new AuthorizedUserDTO();

        authorizedUser.setId(user.getId());
        authorizedUser.setName(user.getName());
        authorizedUser.setPhoto(user.getPhoto());
        authorizedUser.setEmail(user.getEmail());

        if (user.isModerator()) {
            authorizedUser.setUserIsModerator(
                    postsRepository.countPostAwaitingModeration()
            );
        }

        return authorizedUser;
    }

    public Optional<Users> getAuthorizedUser() {
        if (isAuthorized()) {
            final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
            int userId = appProperties.getUserIdBySessionId(sessionId);

            return usersRepository.findById(userId);
        }

        return Optional.empty();
    }
    public boolean isModerator(Users user) {
        return user.isModerator();
    }
}
