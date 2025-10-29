package br.com.serratec.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.serratec.entity.Role;
import br.com.serratec.entity.User;
import br.com.serratec.entity.UserDetailsImpl;
import br.com.serratec.enums.RoleEnum;
import br.com.serratec.records.CredenciaisLoginRecord;
import br.com.serratec.records.JwtTokenRecord;
import br.com.serratec.records.UserRecord;
import br.com.serratec.repository.RoleRepository;
import br.com.serratec.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public JwtTokenRecord authenticateUser(CredenciaisLoginRecord credenciaisLoginRecord) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(credenciaisLoginRecord.login(), credenciaisLoginRecord.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new JwtTokenRecord(jwtTokenService.generateToken(userDetails));
    }

    public User createUser(UserRecord userRecord) {

    Set<String> strRoles = userRecord.role();
    List<Role> roles = new At<>();

    if (strRoles != null) {
        for (String strRole : strRoles) {
            RoleEnum roleEnum;
            try {
                roleEnum = RoleEnum.valueOf(strRole);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Role inválida: " + strRole);
            }

            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new RuntimeException("Role não encontrada no banco: " + strRole));

            roles.add(role);
        }
    }

    User newUser = new User(
            userRecord.login(),
            encoder.encode(userRecord.password()),
            new ArrayList<>(roles) 
    );

    return userRepository.save(newUser);
    }
}
