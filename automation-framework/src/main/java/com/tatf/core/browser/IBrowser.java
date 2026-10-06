package com.tatf.core.browser;

import com.tatf.core.find.Find;
import com.tatf.core.interaction.Interaction;
import com.tatf.core.wait.Wait;

/**
 * Interfaz que define operaciones comunes para la gestión del navegador y la espera de elementos en una página web.
 */
public interface IBrowser {
    /**
     * Devuelve el buscador de elementos.
     */
    Find find();

    /**
     * Devuelve el manejador de interacciones del navegador.
     */
    Interaction interaction();

    /**
     * Devuelve una espera sobre un localizador puntual.
     *
     * @param locator Localizador sobre el que se va a esperar.
     */
    Wait wait(String locator);

    /**
     * Devuelve una espera por tiempo fijo, en segundos.
     *
     * @param seconds Cantidad de segundos a esperar.
     */
    Wait wait(int seconds);
}
