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
        boom.setTags("boom,portugal,psytrance");
        boom.setOmschrijving("Psy-Trance in Portugal!");
        boom.setName("Boom festival");
        boom.setLatitude("39.916667");
        boom.setLongitude("-7.233333");
        boom.setDatum(LocalDateTime.of(1992, 7, 20, 15, 0));
        raveRepo.save(boom);

        var pepfest = new Rave();
        pepfest.setTags("nijmegen,hardstyle");
        pepfest.setOmschrijving("");
        pepfest.setName("PEPfest");
        pepfest.setLatitude("51.812565");
        pepfest.setLongitude("5.837226");
        pepfest.setDatum(LocalDateTime.of(1990, 11, 4, 23, 0));
        raveRepo.save(pepfest);

        var harder = new Rave();
        harder.setTags("arnhem,hardstyle,hardcore");
        harder.setOmschrijving("Bullet Proof Hardcore");
        harder.setName("Harder is Beter");
        harder.setLatitude("51.985104");
        harder.setLongitude("5.898730");
        harder.setDatum(LocalDateTime.of(1990, 11, 18, 23, 0));
        raveRepo.save(harder);
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
