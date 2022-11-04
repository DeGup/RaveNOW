package nl.whitehorses.ravenow.controller;

import net.bytebuddy.matcher.StringMatcher;
import nl.whitehorses.ravenow.model.Rave;
import nl.whitehorses.ravenow.model.Role;
import nl.whitehorses.ravenow.model.SearchRave;
import nl.whitehorses.ravenow.model.Gebruiker;
import nl.whitehorses.ravenow.repositories.GabberRepo;
import nl.whitehorses.ravenow.repositories.RaveRepo;
import nl.whitehorses.ravenow.repositories.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {

    private final GabberRepo gabberRepo;
    private final RaveRepo raveRepo;
    private final UserRepo userRepo;

    public HomeController(GabberRepo gabberRepo, RaveRepo raveRepo, UserRepo userRepo) {
        this.gabberRepo = gabberRepo;
        this.raveRepo = raveRepo;
        this.userRepo = userRepo;

        var gabber = new Gebruiker();
        gabber.setRole(Role.GABBER);
        gabber.setUsername("gabber");
        gabber.setPassword("password");
        userRepo.save(gabber);

        var org = new Gebruiker();
        org.setRole(Role.ORGANISATIE);
        org.setUsername("org");
        org.setPassword("password");
        userRepo.save(org);
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }


    @PostMapping("login")
    public ModelAndView login(String username, String password) {
        var user = userRepo.findByUsernameAndPassword(username, password);
        return user.map(gebruiker -> switch (gebruiker.getRole()) {
            case GABBER -> loginGabber();
            case ORGANISATIE -> loginOrganisatie();
            default -> new ModelAndView("home");
        }).orElseGet(() -> new ModelAndView("home"));

    }

    @GetMapping("register")
    public ModelAndView register(){
        return new ModelAndView("register", "gebruiker", new Gebruiker());
    }

    private ModelAndView loginOrganisatie() {
        var model = Map.of(
                "raves", raveRepo.findAll(),
                "rave", new Rave()
        );
        return new ModelAndView("organisatie/organisatie-home", model);
    }

    private ModelAndView loginGabber() {
        var model = Map.of(
                "raves", raveRepo.findAll(),
                "searchRave", new SearchRave()
        );
        return new ModelAndView("gabbers/gabber-home", model);
    }
}
