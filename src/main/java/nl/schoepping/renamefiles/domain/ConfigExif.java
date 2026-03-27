package nl.schoepping.renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigExif {

    @Getter(AccessLevel.NONE)
    private String author = "EXIF:Artist EXIF:XPAuthor XMP:Creator XMP:CaptionWriter IPTC:By-line IPTC:Writer-Editor";
    @Getter(AccessLevel.NONE)
    private String copyRight = "EXIF:Copyright XMP:Rights IPTC:CopyrightNotice";
    @Getter(AccessLevel.NONE)
    private String comment = "EXIF:UserComment EXIF:XPComment";
    @Getter(AccessLevel.NONE)
    private String countryCode2 = "XMP:CountryCode";
    @Getter(AccessLevel.NONE)
    private String countryCode3 = "IPTC:Country-PrimaryLocationCode";
    @Getter(AccessLevel.NONE)
    private String country = "XMP:Country IPTC:Country-PrimaryLocationName";
    @Getter(AccessLevel.NONE)
    private String province = "XMP:State IPTC:Province-State";
    @Getter(AccessLevel.NONE)
    private String city = "XMP:City";
    @Getter(AccessLevel.NONE)
    private String location = "IPTC:Sub-location IPTC:ObjectName";
    @Getter(AccessLevel.NONE)
    private String title = "XMP:Title EXIF:ImageDescription EXIF:XPTitle XMP:ImageDescription";
    @Getter(AccessLevel.NONE)
    private String url = "XMP:BaseURL Photoshop:BaseURL";
    @Getter(AccessLevel.NONE)
    private String description = "XMP:Headline EXIF:XPSubject IPTC:Caption-Abstract";
    @Getter(AccessLevel.NONE)
    private String keys1 = "XMP:XPSubject IPTC:Keywords XMP:LastKeywordXMP XMP:LastKeywordIPTC";
    @Getter(AccessLevel.NONE)
    private String keys2 = "EXIF:XPKeywords";
    @Getter(AccessLevel.NONE)
    private String instructions = "IPTC:SpecialInstructions XMP:Instructions";

    public String[] getAuthor() {
        return this.author.split(" ");
    }

    public String[] getCopyRight() {
        return this.copyRight.split(" ");
    }

    public String[] getComment() {
        return this.comment.split(" ");
    }

    public String[] getCountryCode2() {
        return this.countryCode2.split(" ");
    }

    public String[] getCountryCode3() {
        return this.countryCode3.split(" ");
    }

    public String[] getCountry() {
        return this.country.split(" ");
    }

    public String[] getProvince() {
        return this.province.split(" ");
    }

    public String[] getCity() {
        return this.city.split(" ");
    }

    public String[] getLocation() {
        return this.location.split(" ");
    }

    public String[] getTitle() {
        return this.title.split(" ");
    }

    public String[] getUrl() {
        return this.url.split(" ");
    }

    public String[] getDescription() {
        return this.description.split(" ");
    }

    public String[] getKeys1() {
        return this.keys1.split(" ");
    }

    public String[] getKeys2() {
        return this.keys2.split(" ");
    }

    public String[] getInstructions() {
        return this.instructions.split(" ");
    }

}
