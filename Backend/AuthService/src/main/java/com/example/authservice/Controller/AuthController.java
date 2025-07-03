package com.example.authservice.Controller;

import com.example.authservice.DTO.DoctorDTO;
import com.example.authservice.Model.RefreshToken;
import com.example.authservice.Model.User;
import com.example.authservice.Request.AuthRequest;
import com.example.authservice.Request.RefreshTokenRequest;
import com.example.authservice.Response.JwtResponse;
import com.example.authservice.Service.JwtService;
import com.example.authservice.Service.RefreshTokenService;
import com.example.authservice.Service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Slf4j
//@CrossOrigin(origins = "${FRONTEND_URL}")
public class AuthController {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtService jwtService, RefreshTokenService refreshTokenService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/patient/sign-up")
    public ResponseEntity<?> SignUp(@RequestBody User user) {
        try {
            Boolean isSignUped = userService.signupUser(user);
            if (Boolean.FALSE.equals(isSignUped)) {
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
            String jwtToken = jwtService.GenerateToken(user.getUsername(), "Patient");
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.toString());
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/doctor/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> DoctorSignUp(@RequestPart("doctorData") DoctorDTO user, @RequestPart("docImg") MultipartFile file) {
        try {
            // Process the file as needed
            Boolean isSignUped = userService.doctorSignupUser(user, file);
            if (Boolean.FALSE.equals(isSignUped)) {
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
            String jwtToken = jwtService.GenerateToken(user.getEmail(), "Doctor");
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.toString());
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/sign-up")
    public ResponseEntity<?> AdminSignUp(@RequestBody User user) {
        try {
            Boolean isSignUped = userService.adminSignupUser(user);
            if (Boolean.FALSE.equals(isSignUped)) {
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
            String jwtToken = jwtService.GenerateToken(user.getUsername(), "Admin");
            return new ResponseEntity<>(JwtResponse.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.toString());
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> AuthenticateAdminAndGetToken(@RequestBody AuthRequest authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(JwtResponse.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername(), "Admin"))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Exception in Admin Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/patient/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(JwtResponse.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername(), "Patient"))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<?> AuthenticateDoctorAndGetToken(@RequestBody AuthRequest authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(JwtResponse.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername(), "Doctor"))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Exception in Doctor Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequestDTO) {
        try {
            return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUserInfo)
                    .map(userInfo -> {
                        String accessToken = jwtService.GenerateToken(userInfo.getUsername(), "Patient");
                        return ResponseEntity.ok(JwtResponse.builder()
                                .accessToken(accessToken)
                                .token(refreshTokenRequestDTO.getToken())
                                .build());
                    }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token expired. Please use a valid refresh token.");
        } catch (Exception ex) {
            log.error("Error refreshing token: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing token.");
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = userService.getUserByUsername(authentication.getName());
            if (Objects.nonNull(userId)) {
                return ResponseEntity.ok(userId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}