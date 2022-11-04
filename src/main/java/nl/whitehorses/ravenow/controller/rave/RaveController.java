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
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RaveController {
    private final RaveRepo raveRepo;

    @PostConstruct
    private void defaults() {
        var boom = new Rave();
        boom.setName("Boom festival");
        boom.setTags("boom,portugal,psytrance");
        boom.setOmschrijving("Psy-Trance in Portugal!");
        boom.setLatitude("39.916667");
        boom.setLongitude("-7.233333");
        boom.setDatum(LocalDateTime.of(2023, 7, 20, 15, 0));
        raveRepo.save(boom);

        var pepfest = new Rave();
        pepfest.setName("PEPfest");
        pepfest.setTags("nijmegen,hardstyle");
        pepfest.setOmschrijving("");
        pepfest.setLatitude("51.812565");
        pepfest.setLongitude("5.837226");
        pepfest.setDatum(LocalDateTime.now());
        raveRepo.save(pepfest);

        var fout = new Rave();
        fout.setName("Fout XXL");
        fout.setTags("fout,hardstyle,utrecht");
        fout.setOmschrijving("Lekker fout!");
        fout.setLatitude("52.091680");
        fout.setLongitude("5.120360");
        fout.setDatum(LocalDateTime.now());
        raveRepo.save(fout);

        var harder = new Rave();
        harder.setName("Harder is Beter");
        harder.setTags("arnhem,hardstyle,hardcore");
        harder.setOmschrijving("Bullet Proof Hardcore");
        harder.setLatitude("51.985104");
        harder.setLongitude("5.898730");
        harder.setDatum(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
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
