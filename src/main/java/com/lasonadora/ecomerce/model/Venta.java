package com.lasonadora.ecomerce.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha = LocalDateTime.now();

    private double total;

   // @Enumerated(EnumType.STRING)
    //private EstadoVenta estado = EstadoVenta.PENDIENTE;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    private String correo;
    private String direccion;

    @Column(name = "metodo_pago")
    private String metodoPago;

    // 🔹 Constructor vacío
    public Venta() {}

    // 🔹 Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    //public EstadoVenta getEstado() { return estado; }
    //public void setEstado(EstadoVenta estado) { this.estado = estado; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    @Transient
    private List<DetalleVenta> detalles;

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

}
