package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.spring_renamefiles.action.ReadConfig;
import nl.schoepping.spring_renamefiles.action.ReadFiles;
import nl.schoepping.spring_renamefiles.action.ReadTimeLine;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class stepDefinitionsReadFiles {

    private ReadConfig config;
    private ReadFiles readFiles;
    private List<ReadFile> files = new ArrayList<>();
    private File[] dirs;
    private int dirCount;

    @Given("root directory {string}")
    public void rootDirectory(String path) {
        // Write code here that turns the phrase above into concrete actions
        config = new ReadConfig("config.yml");
        readFiles = new ReadFiles(path, config, new ReadTimeLine("timeline.yml"), config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        File dir = new File(path);
        dirs = dir.listFiles();
    }

    @When("read all media files")
    public void readAllMediaFiles() {
        // Write code here that turns the phrase above into concrete actions
        files = readFiles.getFiles();
    }

    @Then("the number of files should be {int}")
    public void theNumberOfFilesShouldBe(int count) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(count, files.size());
    }

    @And("the first file should be {string}")
    public void theFirstFileShouldBe(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(fileName, files.getFirst().getFileName());
    }

    @And("the last file should be {string}")
    public void theLastFileShouldBe(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(fileName, files.getLast().getFileName());
    }

    @When("read all timeLaps directories")
    public void readAllTimeLapsDirectories() {
        // Write code here that turns the phrase above into concrete actions
        dirCount = 0;
        for (File dir : dirs) {
            if (dir.isDirectory() && dir.getName().matches(config.getPathForTimelaps())) {
                dirCount++;
            }
        }
    }

    @Then("the number of directories should be {int}")
    public void theNumberOfDirectoriesShouldBe(int count) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(count, dirCount);
    }
}
