package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import nl.schoepping.spring_renamefiles.action.ReadTimeLine;
import org.junit.Assert;

public class stepDefinitions {

    private ReadTimeLine readTimeLine;

    @Given("timeline file")
    public void timelineFile() {
        // Write code here that turns the phrase above into concrete actions
        readTimeLine = new ReadTimeLine();
    }

    @Then("number of timelines should be {int}")
    public void numberOfTimelinesShouldBe(int count) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(count, readTimeLine.getTimeLines().size());
    }

}
