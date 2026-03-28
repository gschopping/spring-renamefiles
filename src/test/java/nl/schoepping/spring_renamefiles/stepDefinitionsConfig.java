package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.renamefiles.action.ReadConfig;
import nl.schoepping.renamefiles.domain.FileType;
import org.junit.Assert;

import java.util.Arrays;

public class stepDefinitionsConfig {
    private ReadConfig config;
    private FileType fileType;
    private String[] tags;
    private String errorMessage;

    @Given("config file {string}")
    public void configFile(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        try {
            config = new ReadConfig(fileName);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("^number of fileTypes should be (\\d+)$")
    public void numberOfFileTypes(int count) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(count, config.getConfig().getFileTypes().size());
    };

    @When("get item {int}")
    public void getItem(int index) {
        // Write code here that turns the phrase above into concrete actions
        fileType = config.getConfig().getFileTypes().get(index-1);
    }

    @Then("fileType should be {string}")
    public void filetypeShouldBe(String filetype) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(filetype, fileType.getFileType());
    }

    @And("extension should be {string}")
    public void extensionShouldBe(String extension) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(extension, fileType.getExtension());
    }

    @And("datetime should be {string}")
    public void datetimeShouldBe(String datetime) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(datetime, fileType.getDateTime());
    }

    @And("timezone should be null")
    public void timezoneShouldBeNull() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertNull(fileType.getTimeZone());
    }

    @And("isWritable should be true")
    public void isWritableShouldBeTrue() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(true, fileType.getIsWritable());
    }

    @And("isPhotoFormat should be true")
    public void isPhotoformatShouldBeTrue() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(true, fileType.getIsPhotoFormat());
    }

    @And("gpslatitude should be {string}")
    public void gpslatitudeShouldBe(String latitude) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(latitude, fileType.getGpsLatitude());
    }

    @And("gpslongitude should be {string}")
    public void gpslongitudeShouldBe(String longitude) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(longitude, fileType.getGpsLongitude());
    }

    @When("get the author exif tags")
    public void getTheAuthorExifTags() {
        // Write code here that turns the phrase above into concrete actions
        tags = config.getConfig().getExif().getAuthorList();
    }

    @Then("tags should contain {string}")
    public void tagsShouldContain(String tag) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertTrue(Arrays.asList(tags).contains(tag));
    }

    @When("get the location exif tags\"")
    public void getTheLocationExifTags() {
        // Write code here that turns the phrase above into concrete actions    throw new io.cucumber.java.PendingException();}
        tags = config.getConfig().getExif().getLocationList();
    }

    @When("get the title exif tags")
    public void getTheTitleExifTags() {
        // Write code here that turns the phrase above into concrete actions
        tags = config.getConfig().getExif().getTitleList();
    }

    @When("get the description exif tags")
    public void getTheDescriptionExifTags() {
        // Write code here that turns the phrase above into concrete actions
        tags = config.getConfig().getExif().getDescriptionList();
    }

    @When("get the title osm tags")
    public void getTheTitleOsmTags() {
        // Write code here that turns the phrase above into concrete actions
        tags = config.getConfig().getOpenStreetMap().getTitleList();
    }

    @When("get the description osm tags")
    public void getTheDescriptionOsmTags() {
        // Write code here that turns the phrase above into concrete actions
        tags = config.getConfig().getOpenStreetMap().getDescriptionList();
    }

    @And("regexMedia should be {string}")
    public void regexmediaShouldBe(String regex) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(regex, config.getRegexMedia(ReadConfig.FileFormat.ALL));
    }

    @When("get path settings")
    public void getPathSettings() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("pathResults should be {string}")
    public void pathresultsShouldBe(String path) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(path, config.getConfig().getPath().getPathForResults());
    }

    @And("pathTimeLaps should be {string}")
    public void pathtimelapsShouldBe(String path) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(path, config.getConfig().getPath().getPathForTimeLaps());
    }

    @And("pathGps should be {string}")
    public void pathgpsShouldBe(String path) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(path, config.getConfig().getPath().getPathForGps());
    }

    @Then("an error should be thrown with the message {string}")
    public void anErrorShouldBeThrownWithTheMessage(String message) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(message, errorMessage);
    }

    @Then("config should be empty")
    public void configShouldBeEmpty() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertNull(this.config.getConfig().getExif());
    }
}
