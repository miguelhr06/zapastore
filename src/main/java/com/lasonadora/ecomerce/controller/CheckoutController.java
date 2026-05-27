package com.lasonadora.ecomerce.controller;

import com.lasonadora.ecomerce.model.*;
import com.lasonadora.ecomerce.repository.*;
import com.lasonadora.ecomerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CheckoutController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    // ---------------------------------------------------------
    // 🔥 1. RECIBIR CARRITO DESDE JAVASCRIPT
    // ---------------------------------------------------------
    @PostMapping("/checkout/data")
    @ResponseBody
    public void recibirCarrito(@RequestBody Map<String, Map<String, Object>> carrito,
                               HttpSession session) {

        List<CarritoItem> items = new ArrayList<>();

        carrito.forEach((id, data) -> {
            Producto producto = productoService.obtenerPorId(Integer.valueOf(id));
            int cantidad = (int) data.get("cantidad");

            items.add(new CarritoItem(producto, cantidad));
        });

        session.setAttribute("carrito", items);
    }

    // ---------------------------------------------------------
    // 🔥 2. MOSTRAR CHECKOUT CON LOS PRODUCTOS REALES
    // ---------------------------------------------------------
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito == null) carrito = new ArrayList<>();

        double total = carrito.stream()
                .mapToDouble(CarritoItem::getSubtotal)
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "checkout";
    }

    // ---------------------------------------------------------
    // 🔥 3. PROCESAR COMPRA Y GENERAR REPORTE
    // ---------------------------------------------------------
    @PostMapping("/checkout")
    public String procesarCheckout(@RequestParam("nombre") String nombre,
                                   @RequestParam("correo") String correo,
                                   @RequestParam("direccion") String direccion,
                                   @RequestParam("metodoPago") String metodoPago,
                                   HttpSession session,
                                   Model model) {

        // 🛒 Obtener items reales desde la sesión
        List<CarritoItem> items = (List<CarritoItem>) session.getAttribute("carrito");
        if (items == null) items = new ArrayList<>();

        double total = items.stream().mapToDouble(CarritoItem::getSubtotal).sum();

        // 💾 Guardar venta
        Venta venta = new Venta();
        venta.setNombreCliente(nombre);
        venta.setCorreo(correo);
        venta.setDireccion(direccion);
        venta.setMetodoPago(metodoPago);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);

        Venta ventaGuardada = ventaRepository.save(venta);

        // 💾 Guardar detalles
        for (CarritoItem item : items) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setNombreProducto(item.getProducto().getNombre());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detalle.setSubtotal(item.getSubtotal());
            detalleVentaRepository.save(detalle);
        }

        // 🧹 Limpiar carrito
        session.removeAttribute("carrito");

        // 🔎 Obtener detalles guardados
        List<DetalleVenta> detalles =
                detalleVentaRepository.findByVenta_Id(ventaGuardada.getId());

        model.addAttribute("venta", ventaGuardada);
        model.addAttribute("detalles", detalles);

        return "reporte";
    }

    // ---------------------------------------------------------
    // 🔥 4. VER REPORTE DIRECTO
    // ---------------------------------------------------------
    @GetMapping("/reporte")
    public String mostrarReporte(Model model) {

        Venta venta = ventaRepository.findTopByOrderByIdDesc().orElse(null);

        if (venta != null) {
            List<DetalleVenta> detalles =
                    detalleVentaRepository.findByVenta_Id(venta.getId());
            model.addAttribute("venta", venta);
            model.addAttribute("detalles", detalles);
        }

        return "reporte";
    }

    // ---------------------------------------------------------
    // 🔥 5. VOLVER AL MENÚ
    // ---------------------------------------------------------
    @PostMapping("/carrito/finalizarReporte")
    public String finalizarReporte() {
        return "redirect:/productos";
    }



}
