package nl.schoepping.spring_renamefiles;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import nl.schoepping.renamefiles.action.ReadConfig;
import nl.schoepping.renamefiles.action.ReadExifInfo;
import org.junit.Assert;

import java.time.format.DateTimeFormatter;

public class stepDefinitionsCreationDate {

    private ReadExifInfo exifInfo;
    private String errorMessage;

    @Given("File {string}")
    public void file(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        ReadConfig config =  new ReadConfig("config.yml");
        try {
            exifInfo = new ReadExifInfo("../files/" + fileName, config);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the creationDate is {string}")
    public void theCreationDateIs(String creationDate) {
        // Write code here that turns the phrase above into concrete actions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(creationDate, exifInfo.getExifInfo().getCreationDate().format(formatter));
    }

}
