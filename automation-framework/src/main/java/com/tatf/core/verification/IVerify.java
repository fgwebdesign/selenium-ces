package com.tatf.core.verification;


/**
 * Interfaz que define métodos para verificar resultados esperados con obtenidos y agregar mensajes asociados.
 */
public interface IVerify {
    /**
     * Devuelve una instancia de verificación rigurosa.
     */
    static IVerify create() {
        return new VerifyImpl();
    }

    /**
     * Verifica que el valor actual sea verdadero.
     *
     * @param actual  Valor actual a verificar.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyTrue(boolean actual, String message);

    /**
     * Verifica que los valores actuales sean verdaderos.
     *
     * @param actual  Valores actuales a verificar.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyTrue(boolean[] actual, String message);

    /**
     * Verifica que el valor actual sea falso.
     *
     * @param actual  Valor actual a verificar.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyFalse(boolean actual, String message);

    /**
     * Verifica que los valores actuales sean falsos.
     *
     * @param actual  Valores actuales a verificar.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyFalse(boolean[] actual, String message);

    /**
     * Agrega una verificación de falla.
     *
     * @param message Mensaje asociado a la verificación de falla.
     */
    void verifyFail(String message);

    /**
     * Verifica que los valores esperados y los actuales coincidan.
     *
     * @param expected Valor esperado.
     * @param actual   Valor actual.
     * @param message  Mensaje asociado a la verificación.
     */
    void verify(Object expected, Object actual, String message);

    /**
     * Verifica que los valores esperados y los actuales coincidan.
     *
     * @param expected Valores esperados.
     * @param actual   Valores actuales.
     * @param message  Mensaje asociado a la verificación.
     */
    void verify(Object[] expected, Object[] actual, String message);

    /**
     * Verifica que el valor esperado no sea nulo.
     *
     * @param actual  Valor esperado.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyNotNull(Object actual, String message);

    /**
     * Verifica que los valores esperados no sean nulos.
     *
     * @param actual  Valores esperados.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyNotNull(Object[] actual, String message);

    /**
     * Verifica que el valor esperado sea nulo.
     *
     * @param actual  Valor esperado.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyNull(Object actual, String message);

    /**
     * Verifica que los valores esperados sean nulos.
     *
     * @param actual  Valores esperados.
     * @param message Mensaje asociado a la verificación.
     */
    void verifyNull(Object[] actual, String message);

    /**
     * Verifica que los valores esperados y los actuales no coincidan.
     *
     * @param expected Valor esperado.
     * @param actual   Valor actual.
     * @param message  Mensaje asociado a la verificación.
     */
    void notVerify(Object expected, Object actual, String message);

    /**
     * Verifica que los valores esperados y los actuales no coincidan.
     *
     * @param expected Valores esperados.
     * @param actual   Valores actuales.
     * @param message  Mensaje asociado a la verificación.
     */
    void notVerify(Object[] expected, Object[] actual, String message);
}