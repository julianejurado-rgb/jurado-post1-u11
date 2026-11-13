package com.tienda.pedidos.strategy;

import org.springframework.stereotype.Component;

@Component("REGULAR")
public class DescuentoRegular implements EstrategiaDescuento {
    public double calcularDescuento(double subtotal) { return 0.0; }
}
