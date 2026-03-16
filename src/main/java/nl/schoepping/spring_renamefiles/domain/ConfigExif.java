package nl.schoepping.spring_renamefiles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigExif {

    private String[] author = {"EXIF:Artist", "EXIF:XPAuthor", "XMP:Creator", "XMP:CaptionWriter", "IPTC:By-line", "IPTC:Writer-Editor"};
    private String[] copyright = {"EXIF:Copyright", "XMP:Rights", "IPTC:CopyrightNotice"};
    private String[] comment = {"EXIF:UserComment", "EXIF:XPComment"};
    private String[] countryCode2 = {"XMP:CountryCode"};
    private String[] countryCode3 = {"IPTC:Country-PrimaryLocationCode"};
    private String[] country = {"XMP:Country", "IPTC:Country-PrimaryLocationName"};
    private String[] province = {"XMP:State", "IPTC:Province-State"};
    private String[] city = {"XMP:City"};
    private String[] location = {"IPTC:Sub-location", "IPTC:ObjectName"};
    private String[] title = {"XMP:Title", "EXIF:ImageDescription", "EXIF:XPTitle", "XMP:ImageDescription"};
    private String[] url = {"XMP:BaseURL", "Photoshop:BaseURL"};
    private String[] description = {"XMP:Headline", "EXIF:XPSubject", "IPTC:Caption-Abstract"};
    private String[] keys1 = {"XMP:XPSubject", "IPTC:Keywords", "XMP:LastKeywordXMP", "XMP:LastKeywordIPTC"};
    private String[] keys2 = {"EXIF:XPKeywords"};
    private String[] instructions = {"IPTC:SpecialInstructions", "XMP:Instructions"};
}
