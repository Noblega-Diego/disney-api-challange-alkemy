package com.history.api.disney.services;

import com.history.api.disney.dao.UserDao;
import com.history.api.disney.dto.AuthenticationRequest;
import com.history.api.disney.dto.AuthenticationResponse;
import com.history.api.disney.dto.UserDTO;
import com.history.api.disney.models.User;
import com.history.api.disney.utils.Mapeador;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private Environment env;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService email;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Mapeador mapped;

    private String URL_HOSTED_SERVER;

    public AuthService(){
        URL_HOSTED_SERVER = env.getProperty("app.hostedserverurl");
    }

    public void registerUser(UserDTO user) throws Exception{
            User createdUser = mapped.map(user, User.class);
            if(userDao.findByEmail(user.getEmail()) != null)
                throw new Exception("email already exist");
            createdUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(6)));
            createdUser = userDao.insert(createdUser);
            sendEmail(createdUser);
    }

    public void sendEmail(User user){
        Map<String, Object> values =  new HashMap<>();
        values.put("authentication", "OK");
        String confirmToken = jwtService.createToken(user, values);
        email.sendEmailContentHTML(user.getEmail(),user.getName() + " " + user.getLastname(),
                "Welcome!, confirm your email", "" +
                        "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Confirm Email</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Welcome to api-disney</h1>" +
                        "    <p>Before you can use our api you need to confirm your email address, click on the link below.</p>" +
                        "    <a href=\"http://"+URL_HOSTED_SERVER+"/auth/confirm_email/" + confirmToken +"\">Confirm email</a>\n" +
                        "</body>\n" +
                        "</html>"
        );
    }

    public boolean confirmEmail(String token){
        try {
            Long id = jwtService.extractId(token);
            User user = userDao.findById(id);
            user.setActive(true);
            userDao.update(user);
            return true;
        }catch (SignatureException e){
            return false;
        }
    }

    public AuthenticationResponse createToken(AuthenticationRequest authenticationRequest) throws Exception{
        User user = userDao.findByEmail(authenticationRequest.getEmail());
        if (user == null)
            throw new Exception("User not exist");
        if (!user.isActive())
            throw new Exception("User is not actived");
        if (!BCrypt.checkpw(authenticationRequest.getPassword(),user.getPassword()))
            throw new Exception("Incorrect password");
        return new AuthenticationResponse(jwtService.createToken(user));
    }

}
