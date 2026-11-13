package com.tienda.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

// DTO de entrada — datos del cliente hacia la API
public class PedidoRequestDTO {
    @NotBlank(message = "El email del cliente es obligatorio")
    private String clienteEmail;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    @Pattern(regexp = "(?i)REGULAR|VIP|MAYORISTA",
             message = "El tipo de cliente debe ser REGULAR, VIP o MAYORISTA")
    private String tipoCliente;      // REGULAR, VIP o MAYORISTA

    // @Positive acepta null, por eso se combina con @NotNull
    @NotNull(message = "El subtotal es obligatorio")
    @Positive(message = "El subtotal debe ser mayor a cero")
    private Double subtotal;

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
