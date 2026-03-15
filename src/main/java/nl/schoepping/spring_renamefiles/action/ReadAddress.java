package nl.schoepping.spring_renamefiles.action;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.ExifInfo;
import nl.schoepping.spring_renamefiles.domain.OSMLocation;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Log
public class ReadAddress {

    private RestClient client;

    public ReadAddress() {
        this.client = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org/reverse")
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "nl,en-US;q=0.7,en;q=0.3",
                        HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br",
                        HttpHeaders.CACHE_CONTROL, "max-age=0",
                        HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                        HttpHeaders.CONNECTION, "keep-alive",
                        HttpHeaders.HOST, "nominatim.openstreetmap.org",
                        HttpHeaders.TE, "Trailers",
                        HttpHeaders.UPGRADE, "1",
                        HttpHeaders.USER_AGENT, "Rename mediafiles")
                .build();

    }

    public OSMLocation getLocation(ExifInfo exifInfo) {
        String format = "json";
        int details = 1;
        int zoom = 18;
        return this.client.get()
                .uri("?format={format}&lat={latitude}&lon={longitude}&addressdetails={details}&zoom={zoom}",
                    format,
                    exifInfo.getLatitude(),
                    exifInfo.getLongitude(),
                    details,
                    zoom)
                .retrieve()
                .body(OSMLocation.class);
    }

}
