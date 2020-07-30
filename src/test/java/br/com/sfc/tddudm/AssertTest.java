package br.com.sfc.tddudm;

import br.com.sfc.tddudm.entidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertTest {

    @Test
    public void test() {
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);

        Assertions.assertEquals(5, 5);
        Assertions.assertEquals(0.51234, 0.512, 0.01);

        Assertions.assertEquals("CSA", "CSA");
        Assertions.assertTrue("CSA".equalsIgnoreCase("csa"));

        Usuario u1 = new Usuario("Usuário 1");
        Usuario u2 = new Usuario("Usuário 1");
        Usuario u3 = null;

        Assertions.assertEquals(u1, u2);

        //Assertions.assertSame(u1, u2);

        Assertions.assertNull(u3);
    }

}
