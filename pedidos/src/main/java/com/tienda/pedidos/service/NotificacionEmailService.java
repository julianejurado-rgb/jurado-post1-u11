package com.tienda.pedidos.service;

public class NotificacionEmailService {

    public void enviar(String destinatario, String mensaje) {
        // simulacion de envio de correo (fuera del alcance de este laboratorio)
        System.out.println("Correo enviado a " + destinatario + ": " + mensaje);
    }
}
