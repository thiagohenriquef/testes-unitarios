package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.User;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test() {
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals("Comparação", 2, 2);
        Assert.assertEquals(2, 2);
        Assert.assertEquals(0.5123, 0.512, 0.001);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        Assert.assertEquals("foo", "foo");
        Assert.assertTrue("Foo".equalsIgnoreCase("foo"));
        Assert.assertTrue("Foo".startsWith("F"));

        User user = new User("Thiago");
        User user2 = new User("Thiago");
        User user3 = null;

        Assert.assertEquals(user, user2);
        Assert.assertNull(user3);
        Assert.assertNotSame(user, user2);
    }
}
