package com.yasir.erp.minierp.modules.user.infrastructure.controller;


import com.yasir.erp.minierp.modules.user.application.dto.UserDto;
import com.yasir.erp.minierp.modules.user.application.dto.request.CreateUserDtoRequest;
import com.yasir.erp.minierp.modules.user.application.dto.request.UpdateUserDtoRequest;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.ActivateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.AddUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.DeactivateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.DeleteUserByIdUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.command.UpdateUserUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.query.FindUserByIdAndActiveUseCase;
import com.yasir.erp.minierp.modules.user.domain.port.inbound.query.FindUserByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final AddUserUseCase addUser;
    private final UpdateUserUseCase updateUser;
    private final ActivateUserUseCase activateUser;
    private final DeactivateUserUseCase deactivateUser;
    private final DeleteUserByIdUseCase deleteUser;

    private final FindUserByIdUseCase findById;
    private final FindUserByIdAndActiveUseCase findByIdAndActive;

    public UserController(AddUserUseCase addUser,
                          UpdateUserUseCase updateUser,
                          ActivateUserUseCase activateUser,
                          DeactivateUserUseCase deactivateUser,
                          DeleteUserByIdUseCase deleteUser,
                          FindUserByIdUseCase findById,
                          FindUserByIdAndActiveUseCase findByIdAndActive) {
        this.addUser = addUser;
        this.updateUser = updateUser;
        this.activateUser = activateUser;
        this.deactivateUser = deactivateUser;
        this.deleteUser = deleteUser;
        this.findById = findById;
        this.findByIdAndActive = findByIdAndActive;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserDtoRequest req) {
        UserDto dto = addUser.addUser(req);
        return ResponseEntity.created(URI.create("/api/users/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id,
                                          @Valid @RequestBody UpdateUserDtoRequest req) {

        UpdateUserDtoRequest fixed = new UpdateUserDtoRequest(
                id,
                req.getName(),
                req.getSurName(),
                req.getCompanyName(),
                req.getPhoneNumber(),
                req.getUsername(),
                req.getEmail(),
                req.getTaxNumber(),
                req.getRoles()
        );
        return ResponseEntity.ok(updateUser.updateUser(fixed));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<UserDto> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(activateUser.activeUser(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<UserDto> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(deactivateUser.reactiveUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUser.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id,
                                           @RequestParam(required = false) Boolean active) {

        UserDto dto = (active == null)
                ? findById.findUserDtoById(id)
                : findByIdAndActive.findDtoByIdAndActive(id, active);
        return ResponseEntity.ok(dto);
    }
}
