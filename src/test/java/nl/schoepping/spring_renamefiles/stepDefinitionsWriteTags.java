package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.spring_renamefiles.action.*;
import nl.schoepping.spring_renamefiles.domain.OSMLocation;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import org.junit.Assert;

import java.io.File;

public class stepDefinitionsWriteTags {

    private ReadFile readFile;
    private ReadConfig config;
    private OSMLocation location;
    private String errorMessage;
    private WriteExifInfo writeExifInfo;

    @Given("file to read {string}")
    public void fileToReadAndWrite(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        config =  new ReadConfig("config.yml");
        ReadTimeLine timeLines = new ReadTimeLine("timeline.yml");
        ReadAddress address = new ReadAddress();
        try {
            File file = new File("../files/" + fileName);
            readFile = new ReadFiles("../files",config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME).getFile(file, config, timeLines, address,1);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @When("write Author {string}")
    public void writeAuthor(String author) {
        // Write code here that turns the phrase above into concrete actions
        readFile.getTimeLine().setAuthor(author);
        writeExifInfo = new WriteExifInfo(readFile, config.getConfigExif(), true);
    }

    @Then("tag {string} should contain author {string}")
    public void tagShouldContainAuthor(String tag, String author) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(author, writeExifInfo.getTag(tag));
    }

    @When("write City {string}")
    public void writeCity(String city) {
        // Write code here that turns the phrase above into concrete actions
        readFile.getAddress().setCity(city);
        writeExifInfo = new WriteExifInfo(readFile, config.getConfigExif(), true);
    }

    @Then("tag {string} should contain city {string}")
    public void tagShouldContainCity(String tag, String city) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(city, writeExifInfo.getTag(tag));
    }

    @When("write Title {string}")
    public void writeTitle(String title) {
        // Write code here that turns the phrase above into concrete actions
        readFile.getAddress().setTitle(title);
        writeExifInfo = new WriteExifInfo(readFile, config.getConfigExif(), true);
    }

    @Then("tag {string} should contain title {string}")
    public void tagShouldContainTitle(String tag, String title) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(title, writeExifInfo.getTag(tag));
    }

    @When("write Keys {string}")
    public void writeKeys(String keys) {
        // Write code here that turns the phrase above into concrete actions
        readFile.getTimeLine().setKeys(keys);
        writeExifInfo = new WriteExifInfo(readFile, config.getConfigExif(), true);
    }

    @Then("tag {string} should contain keys {string}")
    public void tagShouldContainKeys(String tag, String keys) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(keys, writeExifInfo.getTag(tag));
    }

    @When("write Country {string}")
    public void writeCountry(String country) {
        // Write code here that turns the phrase above into concrete actions
        readFile.getAddress().setCountry(country);
        writeExifInfo = new WriteExifInfo(readFile, config.getConfigExif(), true);
    }

    @Then("tag {string} should contain country {string}")
    public void tagShouldContainCountry(String tag, String country) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(country, writeExifInfo.getTag(tag));
    }

}
