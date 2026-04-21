package app.application.adapters.api.controllers;

import app.application.adapters.api.request.UserRequest;
import app.application.adapters.api.response.UserResponse;
import app.application.usecases.AdminUseCase;
import app.domain.models.User;
import app.domain.models.enums.UserState;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminController {

    private final AdminUseCase adminUseCase;

    public AdminController(AdminUseCase adminUseCase) {
        this.adminUseCase = adminUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        User user = toUser(request);
        adminUseCase.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                    @Valid @RequestBody UserRequest request) {
        User user = toUser(request);
        user.setUserId(userId);
        adminUseCase.updateUser(user);
        return ResponseEntity.ok(toResponse(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable Long userId) {
        return ResponseEntity.ok(toResponse(adminUseCase.findUser(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> users = adminUseCase.findAllUsers()
                .stream().map(AdminController::toResponse).toList();
        return ResponseEntity.ok(users);
    }

    private static User toUser(UserRequest req) {
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(req.getPassword());
        u.setRoles(req.getRoles());
        u.setUserState(req.getUserState() != null ? req.getUserState() : UserState.ACTIVE);
        u.setCompanyNit(req.getCompanyNit());
        return u;
    }

    static UserResponse toResponse(User u) {
        return new UserResponse(u.getUserId(), u.getUsername(),
                u.getRoles(), u.getUserState(), u.getCompanyNit());
    }
}
