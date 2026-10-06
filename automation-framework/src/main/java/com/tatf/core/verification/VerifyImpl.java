package com.tatf.core.verification;

import org.junit.jupiter.api.Assertions;

class VerifyImpl implements IVerify {
    /**
     * Verifica que el valor sea verdadero.
     */
    @Override
    public void verifyTrue(boolean actual, String message) {
        Assertions.assertTrue(actual, message);
    }

    /**
     * Verifica que todos los valores sean verdaderos.
     */
    @Override
    public void verifyTrue(boolean[] actual, String message) {
        for (boolean b : actual)
            this.verifyTrue(b, message);
    }

    /**
     * Verifica que el valor sea falso.
     */
    @Override
    public void verifyFalse(boolean actual, String message) {
        Assertions.assertFalse(actual, message);
    }

    /**
     * Verifica que todos los valores sean falsos.
     */
    @Override
    public void verifyFalse(boolean[] actual, String message) {
        for (boolean b : actual)
            this.verifyFalse(b, message);
    }

    /**
     * Fuerza una falla con el mensaje indicado.
     */
    @Override
    public void verifyFail(String message) {
        Assertions.fail(message);
    }

    /**
     * Verifica que el valor esperado y el actual coincidan.
     */
    @Override
    public void verify(Object expected, Object actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    /**
     * Verifica que los valores esperados y los actuales coincidan.
     */
    @Override
    public void verify(Object[] expected, Object[] actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    /**
     * Verifica que el valor no sea nulo.
     */
    @Override
    public void verifyNotNull(Object actual, String message) {
        Assertions.assertNotNull(actual, message);
    }

    /**
     * Verifica que los valores no sean nulos.
     */
    @Override
    public void verifyNotNull(Object[] actual, String message) {
        Assertions.assertNotNull(actual, message);
    }

    /**
     * Verifica que el valor sea nulo.
     */
    @Override
    public void verifyNull(Object actual, String message) {
        Assertions.assertNull(actual, message);
    }

    /**
     * Verifica que los valores sean nulos.
     */
    @Override
    public void verifyNull(Object[] actual, String message) {
        Assertions.assertNull(actual, message);
    }

    /**
     * Verifica que el valor esperado y el actual no coincidan.
     */
    @Override
    public void notVerify(Object expected, Object actual, String message) {
        Assertions.assertNotEquals(expected, actual, message);
    }

    /**
     * Verifica que los valores esperados y los actuales no coincidan.
     */
    @Override
    public void notVerify(Object[] expected, Object[] actual, String message) {
        Assertions.assertNotEquals(expected, actual, message);
    }
}
