package nl.whitehorses.ravenow.controller;

import lombok.AllArgsConstructor;
import nl.whitehorses.ravenow.model.Gebruiker;
import nl.whitehorses.ravenow.repositories.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserRepo userRepo;

    @PostMapping("/user")
    public ModelAndView createUser(@ModelAttribute("gebruiker") Gebruiker user){
        userRepo.save(user);
        return new ModelAndView("home");
    }
}

