package localdbms.model;

import localdbms.AppConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserTest {

    private static UserFactory userFactory;

    @BeforeClass
    public static void initAppContext() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        userFactory = context.getBean("userFactory", UserFactory.class);
    }

    @Test
    public void checkForEquality() {
        User user1 = userFactory.getUser();
        User user2 = userFactory.getUser();

        user1.setLogin("Andrew");
        user1.setPassword("tEst12345");
        user1.setEmail("andrew@rozklad.tk");
        user2.setLogin("Andrew");
        user2.setPassword("tEst12345");
        user2.setEmail("andrew@rozklad.tk");

        Assert.assertEquals(user1.hashCode(), user2.hashCode());
        Assert.assertEquals(user1, user2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createUserWithInvalidPass() {
        User user = userFactory.getUser();
        user.setLogin("Igor");
        user.setPassword("N!kolaeV");
        user.setEmail("igor@nikolaev.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithInvalidEmail() {
        new User("Aaron", "qwePty12345", "@RT.by");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithEmptyLogin() {
        new User("", "qwePty12345", "@rozklad.tk");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithEmptyPassword() {
        new User("Ivan", "", "@rozklad.tk");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithEmptyEmptyEmail() {
        new User("Ivan", "123aA678", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUserWithEmailWithoutAtSign() {
        new User("Ivan", "aA345678", "email.rozklad.tk");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void changeLoginAfterDefinition() {
        User user = new User("Ivan", "123456aA", "email@rozklad.tk");
        user.setLogin("Elena");
    }

    @Test
    public void createValidUser() {
        User user1 = userFactory.getUser();
        user1.setLogin("Igor");
        user1.setPassword("N1kolaeV");
        user1.setEmail("igor@nikolaev.com");
    }

    @Test
    public void createSeveralDifferentInstances() {
        User user1 = userFactory.getUser();
        User user2 = userFactory.getUser();

        user1.setLogin("Andrew");
        user1.setPassword("tEst12345");
        user1.setEmail("andrew@rozklad.tk");
        user2.setLogin("Anna");
        user2.setPassword("tEst12345");
        user2.setEmail("anna@rozklad.tk");

        Assert.assertNotEquals(user1, user2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTooSmallAverageScore() {
        User user = userFactory.getUser();
        user.setAverageScore(User.MIN_SCORE-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTooHighAverageScore() {
        User user = userFactory.getUser();
        user.setAverageScore(User.MAX_SCORE+1);
    }

    @Test
    public void setCorrectAverageScore() {
        User user = userFactory.getUser();
        user.setAverageScore((User.MAX_SCORE + User.MIN_SCORE)/2);
    }

    @Test
    public void setAverageScoreEqualsMaxScore() {
        User user = userFactory.getUser();
        user.setAverageScore(User.MAX_SCORE);
    }

    @Test
    public void setAverageScoreEqualsMinScore() {
        User user = userFactory.getUser();
        user.setAverageScore(User.MIN_SCORE);
    }
}
