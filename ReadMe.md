# Purpose
This image renames image files and video files into a readable filename. The filename given by the camera which usually starts with a prefix and some number doesn´t mean anything, it's hard to order it and to pick the right file for your goal.

So what I did is to rename the file based on the meta data (exif data) within the image or video file which contains the actual creation dateTime. What is however missing is an appropriate title for the file. This can be retrieved from the GPS data (if available) and extract it to an actual location, or (when no GPS data available) it reads it from a separate yaml file (timeline.yml).

# Read metadata from files
It starts with reading all files in a certain directory (the root directory). From each file basic elements are retrieved:
- filetype (is not exact the same as the extension)
- dateTime
- timeZone (if applicable)
- GPS latitude
- GPS longitude

### Not each filetype has stored the same information in their metadata
The first thing you should configure is which exif tag is used for each filetype for the above information

## Exif 
### Config.yml
Based on the *filetype* (also found within the metadata of the file) you can determine which exif tag to use for the creationdate, which will be used to rename the file.
```yaml
standard: &default
    dateTime: dateTimeOriginal
    gpsLatitude: GPSLatitude
    gpsLongitude: GPSLongitude
    isWritable: false
    isPhotoFormat: false

fileTypes:
    - fileType: MP4
      extension: MP4
      <<: *default
      dateTime: CreateDate
      timeZone: timeZone
      isWritable: true
    - fileType: AVI
      extension: AVI
      <<: *default
      dateTime: FileModifyDate
      timeZone: FileModifyDate
    - fileType: M2TS
      extension: M2TS
      <<: *default
      dateTime: FileModifyDate
      timeZone: FileModifyDate
    - fileType: JPEG
      extension: JPG
      <<: *default
      isWritable: true
      isPhotoFormat: true
    - fileType: ARW
      extension: ARW
      <<: *default
      isWritable: true
      isPhotoFormat: true
    - fileType: DNG
      extension: DNG
      <<: *default
      isWritable: true
      isPhotoFormat: true
    - fileType: HEIF
      extension: HIF
      <<: *default
      dateTime: CreateDate
      timeZone: OffsetTime
      isWritable: true
      isPhotoFormat: true
```
With this information the application knows where to find in the metadata to fill the fields as mentioned under *Read metadata from files*.
With the *dateTime* it then can look in the **timeline.yml** which title should be used for the new name of the file. 
And besides that you can add extra fields which will be written back to the file in the metadata. So at the end of the
process the files will be renamed and updated as well.
Now you should think why use the GPS data from the metadata? Instead of looking in the **timeline.yml** it can also retrieve
the title and additional information based on the GPS data when you can find the information from an internet source, in our
case from Open Street Maps.

## Filetype:
- **fileType**: this is the tag as found in the metadata (exif) of the file
- **extensiom**: the extension, which is used to loop through all image and video files. So it takes all the extensions as mentioned in the *config.yml* file to loop through these files only. So in case you have added a file with an extension which is not mentioned in this config file, it won´t be picked up.
- **dateTime**: tag to pick for the renaming (base on dateTime) of the filename
- **timeZone**: the tag to use for timeZone, not always used in that case use the same tag as used for *dateTime*
- **isWritable**: exiftool can´t write back tags to all files, certain filetypes are not supported. To avoid unnecessary trials, you can set isWritable in that case to *false*.
- **isPhotoFormat**: this is only for timelaps files, which always should be of photo format, so only picks the extensions where isPhotoFormat is set to *true*.
- **gpslatitude**,
- **gpslongitude**: which tag to use for retrieving GPS data, so far itś always *GPSLatitude* and *GPSLongitude*.


## Internal fields in use
However to get these things mapped correctly you should understand that in the application the following fields are considered:
- **title** (used for renaming the file)
- **description** (will be written to the metadata)
- **location** (will be written to the metadata)
- **city** (will be written to the metadata)
- **province** (will be written to the metadata)
- **country** (will be written to the metadata)
- **countryCode** (will be written to the metadata)
- **author** (will be written to the metadata)
- **copyRight** (will be written to the metadata)
- **comment** (will be written to the metadata)
- **url** (will be written to the metadata)
- **keys** (will be written to the metadata)
- **instructions** (will be written to the metadata)

## Map internal fields to exif tags
To write back the internal fields to the correct exif tag, that can be configured as well in the *config.yml*:
```yaml
exif:
    author:
        EXIF:Artist
        EXIF:XPAuthor
        XMP:Creator
        XMP:CaptionWriter
        IPTC:By-line
        IPTC:Writer-Editor
    copyRight:
        EXIF:Copyright
        XMP:Rights
        IPTC:CopyrightNotice
    comment:
        EXIF:UserComment
        EXIF:XPComment
    countryCode2: # two letter code
        XMP:CountryCode
    countryCode3: # three letter code
        IPTC:Country-PrimaryLocationCode
    country:
        XMP:Country
        IPTC:Country-PrimaryLocationName
    province:
        XMP:State
        IPTC:Province-State
    city:
        XMP:City
    location:
        IPTC:Sub-location
        IPTC:ObjectName
    title:
        XMP:Title
        EXIF:ImageDescription
        EXIF:XPTitle
        XMP:ImageDescription
    url:
        XMP:BaseURL
        Photoshop:BaseURL
    description:
        XMP:Headline
        EXIF:XPSubject
        IPTC:Caption-Abstract
    keys1: # set as array list
        XMP:XPSubject
        IPTC:Keywords
        XMP:LastKeywordXMP
        XMP:LastKeywordIPTC
    keys2: # keys as one string divided by spaces
        EXIF:XPKeywords
    instructions:
        IPTC:SpecialInstructions
        XMP:Instructions
```
If you don´t configure it in the config.yml, then these are the default values in the application.
Maybe you notice that is **countryCode2** and **countryCode3**. In the internal field you set only the 2 letter countryCode and
the application translate it to a 3 letter code. For keys you see also two separate fields: **keys1** and **keys2**. In the
internal fields you just set keys with comma separated and the application converts it to an array or keeps it as a string.

## Map the Open Street Map data to the internal fields
The opposite is also true, you should define in the *config.yml* how to map the fields which come from the Open Street Maps API
into the internal fields.
```yaml
# you can choose from the following fields:
#   city
#   city_district
#   country
#   country_code
#   county
#   district
#   footway
#   municipality
#   neighbourhood
#   parking
#   postcode
#   quarter
#   road
#   state
#   state_district
#   suburb
#   town
#   village
#   display_name
#   name

openStreetMap:
    title:
        town
        city
        village
        municipality
    description:
        display_name
    location:
        suburb
        neighbourhood
        city_district
        quarter
        district
        road
        footway
    city:
        town
        city
        village
        municipality
    province:
        state
        county
    country:
        country
    countryCode:
        country_code
```
Of course fields like author and copyRight don´t exist in the Open Street Maps data. If you need to fill these fields (which
is not required, only the title field is mandatory).
Also these fields will be set as default in the application if you don´t fill them.

You also find some other information like
- **pathTimelaps**: the subfolder for looking for timelaps images. It looks only for image files (isPhotoFormat: true) and it renames the file to sequentialnumber instead of using time like so: 20251220-0001, and so on. This is done because video editing programmes like Davinci Resolve take images with a sequential number as a video. So if you put each sequence in seperate subfolder: Timelaps1, Timelaps2, etc. then it keeps the sequence separate.
- **pathGPS**: if you have camera with built in GPS, it usually takes time before the GPS sensor makes a connection to the GPS satelites. If you know images are taken at the same location, then by bringing them together in one subfolder the application knows that if a file doesn´t contain GPS data, it will look further in other files until it finds GPS data and these data will be used in the files without GPS.
- **pathResults**: subfolder where the renamed files will reside. So the application Takes an image or vide file, tries to rename it and moves it to the *results* subfolder. When a file can´t be renamed it remains in the original location. The subfolder *results* is also used as subfolder for timelaps and GPS.
```yaml
path:
    pathForTimelaps: "^(Timelaps\\d+)$"
    pathForGps: "^(GPS\\d+)$"
    pathForResults: "results"
```
If you don´t set the above values, it will be set as default by the application.

# Timeline yaml
This is the main file where you can tell the application which title to take for renaming and also which other information to write back in the meta data of the file.
It looks like:
```yaml
# possible attributes
#
# compulsory: 
#   startDate
#   title
# optional:
#   countryCode
#   country (can be automatically retrieved from countryCode)
#   province
#   city
#   location
#   description
#   creator
#   website
#   copyRight
#   comment
#   keys (comma separated)
#   instructions
#   override: use the information in timeline instead of GPS data

enabled: true

standard: &default
    countryCode: NL
    province: Noord-Holland
    city: Amsterdam
    author: Guido Schöpping
    website: www.schopping.net
    copyRight: Guido Schöpping (2025)
    overrideTitle: false
    overrideLocation: false
    
timeLines:
    - startDate: "2025-11-02 09:00:00"
      title: Some title
      <<: *default
      location: Some location
      description: A description
    - startDate: "2025-10-31 09:00:00"
      title: Another title
      <<: *default
      location: Another location
      city: Groningen
      province: Groningen
      countryCode: NL
      description: Centrum of Groningen
    - startDate: "2025-10-30 17:00:00"
      title: Walking in the Veluwe
      <<: *default
      location: Veluwe
      city: Apeldoorn
      province: Gelderland
      countryCode: NL
      description: Walking in the Veluwe
```
In case there is both information in the timeline and there is GPS data available in the metadata then you can choose what to do
by setting the following parameters:
- overrideTitle: if true then the title in the timeline will be used for renaming the file
- overrideLocation: if true then the address data as set in the timeline will be used for writing back to the metadata.

# Folders
## Root folder
This is the main folder where all files (both photo and video) can be dropped to get renamed.

## GPS folder
When you have a camera with built-in GPS then you probably notice that it takes a while before you receive a GPS signal.
Each time you switch off your camera and back on it takes a while (sometimes a long while). In that case the photos and videos
which don´t have GPS data can be updated by using the photos or videos which have and for which you know they are shot in
the same area. In that case you can bring all these files together in a subfolder called *GPS1*, *GPS2*, etc. The
application searches then for the first phot or video with GPS data and updates it in the ones which lack this information.

## Timelaps
If you do the art of making timelapses then you collect all photos together to make one video of it. Video editors like
Davinci Resolve treat photos with a sequential number as one video. So instead of using a data + time in the name of the file
you can choose to use a date + sequential number in order for the video editor to treat them as one video shot. You put
each of these sequences in a separate subfolder *Timelaps1*, *Timelaps2*, etc.

# Using the docker image
Suppose you have a whole load of images used for a timelaps then it can be wise to set *enabled* to *false* to not renaming the files before you uploaded all files. Otherwise the numbering would be messed up.

## Bind mounts
| Host path    | Container path           | Comment |
|--------------|--------------------------|---------|
| config.yml   | /app/config/config.yml   | file    |
| timeline.yml | /app/config/timeline.yml | file    |
| files        | /app/files               | folder  |

## Environment variables
| Tag                   | Default value       | Comment                                                                                                                                                                                                                        |
|-----------------------|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| MAX_NODES             | 1000                | Maximum number of timelines, see the [snakeyaml documentation](https://bitbucket.org/snakeyaml/snakeyaml/wiki/Documentation) for further explanation.                                                                          |
| dateTimeFORMAT_YAML   | yyyy-MM-dd HH:mm:ss | The [format](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/format/dateTimeFormatter.html) used to read the startDate in the timeline.yml. Note that the method *dateTimeFormatter.ofPattern* is used. |
| dateTimeFORMAT_OUTPUT | yyyyMMdd-HHmmss     | Output format when date and time is used to rename the filename                                                                                                                                                                |
| DATEFORMAT_OUTPUT     | yyyyMMdd            | Output form when only date is used (in combination with a sequential number) to rename the filename                                                                                                                            |

## Docker compose
You can use also a docker compose file to configure the bind mounts and environments:
```yaml
version: '3.1'

services:
  rename-images:
    image: gschopping/rename-images:latest
    ports:
      - 8080:8080
    volumes:
      - /volume1/Onbewerkt/config/config.yml:/app/config/config.yml
      - /volume1/Onbewerkt/config/timeline.yml:/app/config/timeline.yml
      - /volume1/Onbewerkt:/app/files
    environment:
      - MAX_NODES=1000
      - dateTimeFORMAT_YAML=yyyy-MM-dd HH:mm:ss
      - dateTimeFORMAT_OUTPUT=yyyyMMdd-HHmmss
      - DATEFORMAT_OUTPUT=yyyyMMdd
    restart: always
```

# Endpoints
To access the application you have the following endpoints:

| Name                 | Request | Usage                                                              |
|----------------------|---------|--------------------------------------------------------------------|
| /config/{fileType}   | GET     | Gives the filetype information for a given filetype                |
| /config/path         | GET     | Gives the information as mentioned under config in the config.yml  |
| /config/exif         | GET     |                                                                    |
| /config/osm          | GET     |                                                                    |
| /timeline            | GET     | Reads the all timelines                                            |
| /timeline/{dateTime} | GET     | Gives the timeline for which this dateTime is valid                |
| /files/root          | GET     | Read all files with additional information from the root directory |
| /files/{subfolder}   | GET     | Read all files with additional information from given subfolder    |
| /renamefiles         | POST    | Renamefiles                                                        |
|                      |         | Body: { "path": "all", "action": "move" } |

- **path** can be: 
  - *all* (all subdirectories + root)
  - [name of subdirectory] only the specified subdirectory will be renamed
  - *root* only the root directory will be renamed
- **action** can be:
  - *move* move the files to the results directory
  - *copy* copy the files to the results directory (keeping the originals)
