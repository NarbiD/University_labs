package localdbms;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfigTest {

    @Test
    public void createAppConfigTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getApplicationName();
    }
}
