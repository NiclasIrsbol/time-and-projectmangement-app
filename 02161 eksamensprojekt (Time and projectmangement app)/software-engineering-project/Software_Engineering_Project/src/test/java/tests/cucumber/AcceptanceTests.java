package tests.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "summary",
        features = {"features"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        publish = false
)
public class AcceptanceTests {
}
