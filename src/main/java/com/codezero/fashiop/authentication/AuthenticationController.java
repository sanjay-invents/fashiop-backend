package com.codezero.fashiop.authentication;

import com.codezero.fashiop.config.TokenProvider;
import com.codezero.fashiop.shared.model.AuthToken;
import com.codezero.fashiop.users.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.codezero.fashiop.shared.model.Constants.HEADER_STRING;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping(value = "/generate-token")
    public ResponseEntity generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @GetMapping(value="/refresh-token")
    public ResponseEntity refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader(HEADER_STRING);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new AuthToken(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}