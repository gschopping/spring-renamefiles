package nl.schoepping.spring_renamefiles;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.renamefiles.action.*;
import nl.schoepping.renamefiles.domain.OSMLocation;
import nl.schoepping.renamefiles.domain.ReadFile;
import org.junit.Assert;

import java.io.File;

public class stepDefinitionsGps {

    private ReadFile readFile;
    private ReadConfig config;
    private OSMLocation location;
    private String errorMessage;
    private WriteExifInfo writeExifInfo;

    @Given("mediaFile {string}")
    public void mediaFile(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        config =  new ReadConfig("config.yml");
        ReadTimeLine timeLines = new ReadTimeLine("timeline.yml");
        ReadAddress address = new ReadAddress();
        try {
            File file = new File("../files/" + fileName);
            readFile = new ReadFiles("../files", config, timeLines, config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME).getFile(file, config, timeLines, address,1);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @When("^read GPS tags$")
    public void readGPSTags() {
        location = readFile.getLocation();
    }

    @Then("latitude should be {string}")
    public void latitudeShouldBe(String latitude) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(latitude, readFile.getExifInfo().getLatitude().toString());
    }

    @And("longitude should be {string}")
    public void longitudeShouldBe(String longitude) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(longitude, readFile.getExifInfo().getLongitude().toString());
    }


    @And("street should be {string}")
    public void streetShouldBe(String street) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(street, location.getAddress().getRoad());
    }

    @And("location should be {string}")
    public void locationShouldBe(String loc) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(loc, location.getAddress().getVillage());
    }

    @And("city should be {string}")
    public void cityShouldBe(String city) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(city, location.getAddress().getTown());
    }

    @When("read GPS tags and write address information to file {string}")
    public void readGPSTagsAndWriteAddressInformationToFile(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(fileName, String.format(readFile.getNewFileName(), ""));
        writeExifInfo = new WriteExifInfo(readFile, config.getConfig().getExif(), true);
    }

    @Then("tag {string} should contain {string}")
    public void tagShouldContain(String tag, String value) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(value, writeExifInfo.getTag(tag));
    }

    @Then("latitude should be null")
    public void latitudeShouldBeNull() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertNull(readFile.getExifInfo().getLatitude());
    }


    @And("longitude should be null")
    public void longitudeShouldBeNull() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertNull(readFile.getExifInfo().getLongitude());
    }

}
