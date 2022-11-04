package nl.whitehorses.ravenow.controller.gabber;

import lombok.RequiredArgsConstructor;
import nl.whitehorses.ravenow.model.SearchRave;
import nl.whitehorses.ravenow.repositories.RaveRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class GabberController {

    private final RaveRepo raveRepo;

    @PostMapping("/gabber/zoek-rave")
    public ModelAndView findRave(@ModelAttribute("searchRave") SearchRave modelAndView) {
        var raves = raveRepo.findAll();
        var geoData = Arrays.stream(modelAndView.getCurrentLocation().trim().split(";")).toList();
        var zoekTags = Arrays.stream(modelAndView.getTags().trim().split(",")).toList();
        if (geoData.size() < 2 && zoekTags.isEmpty()) {
            return new ModelAndView("gabbers/gabber-home", "raves", raves);
        }

        var searchLat = geoData.get(0);
        var searchLong = geoData.get(1);
        var distance = modelAndView.getDistance() == 0 ? Integer.MAX_VALUE : modelAndView.getDistance();
        var distanceFiltered = raves.stream()
                .filter(r -> distance > distance(Double.valueOf(searchLat), Double.valueOf(searchLong), Double.valueOf(r.getLatitude()), Double.valueOf(r.getLongitude())))
                .toList();

        var filtered = distanceFiltered.stream().dropWhile(r ->
                Arrays.stream(r.getTags().split(",")).noneMatch(zoekTags::contains)).toList();


        return new ModelAndView("gabbers/gabber-home", "raves", filtered);
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
