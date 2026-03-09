package nl.schoepping.spring_renamefiles.domain;

import lombok.Data;


//    https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=-34.44076&lon=-58.70521&layer=address
// Example of json output
//  {
//        "place_id":"134140761",
//        "licence":"Data © OpenStreetMap contributors, ODbL 1.0. https:\/\/www.openstreetmap.org\/copyright",
//        "osm_type":"way",
//        "osm_id":"280940520",
//        "lat":"-34.4391708",
//        "lon":"-58.7064573",
//        "place_rank":"26",
//        "category":"highway",
//        "type":"motorway",
//        "importance":"0.1",
//        "addresstype":"road",
//        "display_name":"Autopista Pedro Eugenio Aramburu, El Triángulo, Partido de Malvinas Argentinas, Buenos Aires, 1.619, Argentina",
//        "name":"Autopista Pedro Eugenio Aramburu",
//        "address":{
//            "road":"Autopista Pedro Eugenio Aramburu",
//            "village":"El Triángulo",
//            "state_district":"Partido de Malvinas Argentinas",
//            "state":"Buenos Aires",
//            "postcode":"1.619",
//            "country":"Argentina",
//            "country_code":"ar"
//        },
//        "boundingbox":["-34.44159","-34.4370994","-58.7086067","-58.7044712"]
// }

@Data
public class OSMLocation {
    private String category;
    private String type;
    private String addresstype;
    private String name;
    private String display_name;
    private OSMAddress address;
}
