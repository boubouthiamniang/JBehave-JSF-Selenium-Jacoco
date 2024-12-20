package poc.stories;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import poc.steps.LoginSteps;

import java.util.Arrays;
import java.util.List;

public class StoryRunner extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass())) // Load stories from classpath
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats()
                        .withFormats(
                                Format.CONSOLE,
                                Format.TXT,
                                Format.HTML,
                                Format.XML,
                                Format.STATS // Add summarized statistics
                        )
                        .withRelativeDirectory("target/jbehave-reports") // Save reports in target directory
                );
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new LoginSteps()); // Inject steps
    }

    @Override
    public List<String> storyPaths() {
        return List.of("stories/login.story"); // Adjust path if needed
    }
}
