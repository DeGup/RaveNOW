package nl.whitehorses.ravenow.controller.rave;

import lombok.RequiredArgsConstructor;
import nl.whitehorses.ravenow.model.Rave;
import nl.whitehorses.ravenow.repositories.RaveRepo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RaveController {
    private final RaveRepo raveRepo;

    @PostConstruct
    private void defaults() {
        var boom = new Rave();
        boom.setTags("boom");
        boom.setOmschrijving("""
                A truly psychedelic global gathering of music, arts, culture & hands-on sustainability
                '""");
        boom.setName("Boom festival");
        boom.setLatitude("39.916667");
        boom.setLongitude("-7.233333");
        boom.setDatum(LocalDateTime.of(2023, 7, 20, 15, 0));
        raveRepo.save(boom);
    }

    @PostMapping("/organisatie/create-rave")
    public ModelAndView createRave(@ModelAttribute("rave") Rave rave) {
        raveRepo.save(rave);
        var model = Map.of(
                "raves", raveRepo.findAll(),
                "rave", new Rave()
        );
        return new ModelAndView("organisatie/organisatie-home", model);
    }
}
