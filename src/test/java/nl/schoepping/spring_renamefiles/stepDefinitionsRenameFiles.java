package nl.schoepping.spring_renamefiles;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.schoepping.spring_renamefiles.action.ReadConfig;
import nl.schoepping.spring_renamefiles.action.ReadFiles;
import nl.schoepping.spring_renamefiles.action.ReadTimeLine;
import nl.schoepping.spring_renamefiles.action.WriteExifInfo;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class stepDefinitionsRenameFiles {
    private ReadTimeLine readTimeLine = null;
    private ReadFiles readFiles;
    private ReadConfig config;
    private String errorMessage = null;

    @Given("root directory")
    public void rootDirectory() {
        // Write code here that turns the phrase above into concrete actions
    }

    @And("config file is {string}")
    public void configFileIs(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        config = new ReadConfig("config.yml");
    }

    @And("timeline file is {string}")
    public void timelineFileIs(String fileName) {
        // Write code here that turns the phrase above into concrete actions
        readTimeLine = new ReadTimeLine(fileName);
    }

    @When("rename all files")
    public void renameAllFiles() {
        // Write code here that turns the phrase above into concrete actions
        readFiles = new ReadFiles("../files", config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.ALL), ReadFiles.Divider.TIME);
        readFiles.setFiles();
        List<ReadFile> files = readFiles.getFiles();
        for (ReadFile file : files) {
            WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), false);
            writeExifInfo.writeExifInfoToFile();
        }
    }

    @Then("in subdirectory {string} {int} files will be found")
    public void inSubdirectoryFilesWillBeFound(String subdirectory, int count) {
        // Write code here that turns the phrase above into concrete actions
        File dir = new File("../files/" + subdirectory);
        File[] files = dir.listFiles();
        Assert.assertEquals(count, files.length);
    }

    @And("clean subdirectory {string}")
    public void cleanSubdirectory(String subdirectory) {
        // Write code here that turns the phrase above into concrete actions
        try {
            FileUtils.cleanDirectory(new File("../files/" + subdirectory));
        }
        catch (IOException e) {
            errorMessage =  e.getMessage();
        }
    }

    @When("rename all timelaps files in subdir {string}")
    public void renameAllTimelapsFilesInSubdir(String subdirectory) {
        // Write code here that turns the phrase above into concrete actions
        readFiles = new ReadFiles("../files/" + subdirectory, config, readTimeLine, config.getRegexMedia(ReadConfig.FileFormat.PHOTO), ReadFiles.Divider.COUNTER);
        readFiles.setFiles();
        List<ReadFile> files = readFiles.getFiles();
        for (ReadFile file : files) {
            WriteExifInfo writeExifInfo = new WriteExifInfo(file, config.getConfigExif(), false);
            writeExifInfo.writeExifInfoToFile();
        }
    }
}
