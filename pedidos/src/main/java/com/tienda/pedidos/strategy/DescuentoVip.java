package com.tienda.pedidos.strategy;

import org.springframework.stereotype.Component;

@Component("VIP")
public class DescuentoVip implements EstrategiaDescuento {
    public double calcularDescuento(double subtotal) { return subtotal * 0.15; }
}
