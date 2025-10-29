package br.com.serratec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.entity.User;
import br.com.serratec.records.CredenciaisLoginRecord;
import br.com.serratec.records.JwtTokenRecord;
import br.com.serratec.records.UserRecord;
import br.com.serratec.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<JwtTokenRecord> login(@RequestBody CredenciaisLoginRecord credenciaisLoginRecord) {
        JwtTokenRecord jwtToken = userService.authenticateUser(credenciaisLoginRecord);
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
    @PostMapping("/signup")
    public ResponseEntity<User> cadastro(@RequestBody UserRecord userRecord) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRecord));
    }

}
