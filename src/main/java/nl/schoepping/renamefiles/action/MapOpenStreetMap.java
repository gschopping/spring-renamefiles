package nl.schoepping.renamefiles.action;

import lombok.Getter;
import nl.schoepping.renamefiles.domain.Address;
import nl.schoepping.renamefiles.domain.OSMLocation;

public class MapOpenStreetMap {

    private final OSMLocation location;
    @Getter
    private Address address = new Address();

    public MapOpenStreetMap(ReadConfig config, OSMLocation location) {
        this.location = location;
        this.address.setTitle(getMap(config.getConfig().getOpenStreetMap().getTitle()));
        this.address.setDescription(getMap(config.getConfig().getOpenStreetMap().getDescription()));
        this.address.setLocation(getMap(config.getConfig().getOpenStreetMap().getLocation()));
        this.address.setCity(getMap(config.getConfig().getOpenStreetMap().getCity()));
        this.address.setProvince(getMap(config.getConfig().getOpenStreetMap().getProvince()));
        this.address.setCountry(getMap(config.getConfig().getOpenStreetMap().getCountry()));
        this.address.setCountryCode(getMap(config.getConfig().getOpenStreetMap().getCountryCode()));
    }

    private String getMap(String[] keys) {
        String value = null;
        int i = 0;
        boolean found = false;
        while (i < keys.length && !found) {
            switch (keys[i]) {
                case "display_name":
                    value = this.location.getDisplay_name();
                    break;
                case "name":
                    value = this.location.getName();
                    break;
                case "city":
                    value = this.location.getAddress().getCity();
                    break;
                case "city_district":
                    value = this.location.getAddress().getCity_district();
                    break;
                case "country":
                    value = this.location.getAddress().getCountry();
                    break;
                case "country_code":
                    value = this.location.getAddress().getCountry_code();
                    break;
                case "state":
                    value = this.location.getAddress().getState();
                    break;
                case "state_district":
                    value = this.location.getAddress().getState_district();
                    break;
                case "county":
                    value = this.location.getAddress().getCounty();
                    break;
                case "district":
                    value = this.location.getAddress().getDistrict();
                    break;
                case "footway":
                    value = this.location.getAddress().getFootway();
                    break;
                case "municipality":
                    value = this.location.getAddress().getMunicipality();
                    break;
                case "neighbourhood":
                    value = this.location.getAddress().getNeighbourhood();
                    break;
                case "parking":
                    value = this.location.getAddress().getParking();
                    break;
                case "postcode":
                    value = this.location.getAddress().getPostcode();
                    break;
                case "quarter":
                    value = this.location.getAddress().getQuarter();
                    break;
                case "road":
                    value = this.location.getAddress().getRoad();
                    break;
                case "suburb":
                    value = this.location.getAddress().getSuburb();
                    break;
                case "town":
                    value = this.location.getAddress().getTown();
                    break;
                case "village":
                    value = this.location.getAddress().getVillage();
                    break;
            }
            if (value != null) {
                found = true;
            }
            i++;
        }
        return value;
    }


}
