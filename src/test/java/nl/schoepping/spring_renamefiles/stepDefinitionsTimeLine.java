package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.spring_renamefiles.action.ReadTimeLine;
import nl.schoepping.spring_renamefiles.domain.TimeLine;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class stepDefinitionsTimeLine {

    private ReadTimeLine readTimeLine = null;
    private TimeLine timeLine = null;
    private String errorMessage = null;

    @Given("timeline file {string}")
    public void timelineFile(String filename) {
        // Write code here that turns the phrase above into concrete actions
        try {
            readTimeLine = new ReadTimeLine(filename);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("number of timelines should be {int}")
    public void numberOfTimelinesShouldBe(int count) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(count, readTimeLine.getTimeLines().size());
    }

    @When("get element {int}")
    public void getElement(int element) {
        // Write code here that turns the phrase above into concrete actions
        timeLine = readTimeLine.getTimeLines().get(element - 1);
    }

    @Then("copyright should be {string}")
    public void copyrightShouldBe(String copyright) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(copyright, timeLine.getCopyRight());
    }

    @And("country should be {string}")
    public void countryShouldBe(String country) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(country, timeLine.getCountry());
    }

    @And("description should be {string}")
    public void descriptionShouldBe(String description) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(description, timeLine.getDescription());
    }

    @And("startdate should be {string}")
    public void startdateShouldBe(String startdate) {
        // Write code here that turns the phrase above into concrete actions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(startdate, timeLine.getStartDate().format(formatter));
    }

    @And("enddate should be {string}")
    public void enddateShouldBe(String enddate) {
        // Write code here that turns the phrase above into concrete actions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(enddate, timeLine.getEndDate().format(formatter));
    }

    @When("date and time is {string}")
    public void dateAndTimeIs(String datetime) {
        // Write code here that turns the phrase above into concrete actions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
        try {
            timeLine = readTimeLine.getTimeLine(dateTime);
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("an error {string} should be shown")
    public void anErrorShouldBeShown(String message) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(message, errorMessage);
    }

    @Then("variable enabled should be set to false")
    public void variable_enabled_should_be_set_to_false() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(Boolean.FALSE, readTimeLine.getEnabled());
    }

}
