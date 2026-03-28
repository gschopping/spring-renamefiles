package nl.schoepping.renamefiles.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigExif {

    private String author = "EXIF:Artist EXIF:XPAuthor XMP:Creator XMP:CaptionWriter IPTC:By-line IPTC:Writer-Editor";
    private String copyRight = "EXIF:Copyright XMP:Rights IPTC:CopyrightNotice";
    private String comment = "EXIF:UserComment EXIF:XPComment";
    private String countryCode2 = "XMP:CountryCode";
    private String countryCode3 = "IPTC:Country-PrimaryLocationCode";
    private String country = "XMP:Country IPTC:Country-PrimaryLocationName";
    private String province = "XMP:State IPTC:Province-State";
    private String city = "XMP:City";
    private String location = "IPTC:Sub-location IPTC:ObjectName";
    private String title = "XMP:Title EXIF:ImageDescription EXIF:XPTitle XMP:ImageDescription";
    private String url = "XMP:BaseURL Photoshop:BaseURL";
    private String description = "XMP:Headline EXIF:XPSubject IPTC:Caption-Abstract";
    private String keys1 = "XMP:XPSubject IPTC:Keywords XMP:LastKeywordXMP XMP:LastKeywordIPTC";
    private String keys2 = "EXIF:XPKeywords";
    private String instructions = "IPTC:SpecialInstructions XMP:Instructions";

    public String[] getAuthorList() {
        return this.author.split(" ");
    }

    public String[] getCopyRightList() {
        return this.copyRight.split(" ");
    }

    public String[] getCommentList() {
        return this.comment.split(" ");
    }

    public String[] getCountryCode2List() {
        return this.countryCode2.split(" ");
    }

    public String[] getCountryCode3List() {
        return this.countryCode3.split(" ");
    }

    public String[] getCountryList() {
        return this.country.split(" ");
    }

    public String[] getProvinceList() {
        return this.province.split(" ");
    }

    public String[] getCityList() {
        return this.city.split(" ");
    }

    public String[] getLocationList() {
        return this.location.split(" ");
    }

    public String[] getTitleList() {
        return this.title.split(" ");
    }

    public String[] getUrlList() {
        return this.url.split(" ");
    }

    public String[] getDescriptionList() {
        return this.description.split(" ");
    }

    public String[] getKeys1List() {
        return this.keys1.split(" ");
    }

    public String[] getKeys2List() {
        return this.keys2.split(" ");
    }

    public String[] getInstructionList() {
        return this.instructions.split(" ");
    }

}
