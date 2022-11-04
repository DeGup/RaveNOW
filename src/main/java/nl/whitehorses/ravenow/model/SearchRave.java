package nl.whitehorses.ravenow.model;


import lombok.Data;

@Data
public class SearchRave {
    private long distance;
    private String currentLocation;
    private String tags;
}
