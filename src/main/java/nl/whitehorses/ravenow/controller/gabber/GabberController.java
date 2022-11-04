package nl.whitehorses.ravenow.controller.gabber;

import antlr.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.whitehorses.ravenow.model.Rave;
import nl.whitehorses.ravenow.model.SearchRave;
import nl.whitehorses.ravenow.repositories.RaveRepo;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionFactoryBeanConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GabberController {

    private final RaveRepo raveRepo;

    @PostMapping("/gabber/zoek-rave")
    public ModelAndView findRave(@ModelAttribute("searchRave") SearchRave searchRave) {
        log.info("Zoeken voor {}", searchRave);
        var raves = raveRepo.findAll();
        var geoData = Arrays.stream(searchRave.getCurrentLocation().trim().split(";")).toList();
        var zoekTags = Arrays.stream(searchRave.getTags().trim().split(",")).filter(s -> !s.isEmpty()).toList();

        log.info(geoData.toString());
        String searchLat;
        String searchLong;

        if (geoData.size() >= 2) {
            searchLat = geoData.get(0);
            searchLong = geoData.get(1);
        } else {
            searchLat = "52.091680";
            searchLong = "5.120360";
        }

        List<Rave> toReturn = new ArrayList<Rave>();

        var distance = searchRave.getDistance() == 0 ? Integer.MAX_VALUE : searchRave.getDistance();
        var distanceFiltered = raves.stream()
                .filter(r -> r.getLatitude() != null && r.getLongitude() != null)
                .filter(r -> distance > distance(Double.valueOf(searchLat), Double.valueOf(searchLong), Double.valueOf(r.getLatitude()), Double.valueOf(r.getLongitude())))
                .toList();

        if (!zoekTags.isEmpty()) {
            toReturn = distanceFiltered.stream().filter(r -> Arrays.stream(r.getTags().trim().split(",")).anyMatch(zoekTags::contains)
            ).toList();
        } else {
            toReturn = distanceFiltered;
        }

        var sorted = toReturn.stream().peek(r -> r.setDistance(distance(Double.valueOf(searchLat), Double.valueOf(searchLong), Double.valueOf(r.getLatitude()), Double.valueOf(r.getLongitude()))))
                .sorted(Comparator.comparing(Rave::getDistance))
                .sorted(Comparator.comparing(Rave::getDatum))
                .toList();
        return new ModelAndView("gabbers/gabber-home", "raves", sorted);
    }

    private double distance(Double searchLat, Double searchLong, Double raveLat,
                            Double raveLong) {

        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(raveLat - searchLat);
        double lonDistance = Math.toRadians(raveLong - searchLong);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(searchLat)) * Math.cos(Math.toRadians(raveLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // in km
    }

}
