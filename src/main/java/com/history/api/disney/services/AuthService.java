package com.history.api.disney.services;

import com.history.api.disney.dao.UserDao;
import com.history.api.disney.dto.AuthenticationRequest;
import com.history.api.disney.dto.AuthenticationResponse;
import com.history.api.disney.dto.UserDTO;
import com.history.api.disney.exceptions.BadRequestException;
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

    private Environment env;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService email;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Mapeador mapped;

    private final String URL_HOSTED_SERVER;

    public AuthService(Environment env){
        this.env = env;
        URL_HOSTED_SERVER = env.getProperty("app.hostedserverurl");
    }

    public void registerUser(UserDTO user){
            User createdUser = mapped.map(user, User.class);
            if(userDao.findByEmail(user.getEmail()) != null)
                throw new BadRequestException("email already exist");
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
                        "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n" +
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

    public AuthenticationResponse createToken(AuthenticationRequest authenticationRequest){
        User user = userDao.findByEmail(authenticationRequest.getEmail());
        if (user == null)
            throw new BadRequestException("User is not register");
        if (!user.isActive())
            throw new BadRequestException("User is not actived");
        if (!BCrypt.checkpw(authenticationRequest.getPassword(),user.getPassword()))
            throw new BadRequestException("Incorrect password");
        return new AuthenticationResponse(jwtService.createToken(user));
    }

}
