package com.lasonadora.ecomerce.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.lasonadora.ecomerce.model.DetalleVenta;
import com.lasonadora.ecomerce.model.Usuario;
import com.lasonadora.ecomerce.model.Venta;
import com.lasonadora.ecomerce.repository.DetalleVentaRepository;
import com.lasonadora.ecomerce.repository.VentaRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PerfilController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // 🧾 OBTENER TODAS LAS VENTAS DEL USUARIO
        List<Venta> ventas = ventaRepository.findByCorreo(usuario.getCorreo());

        // Añadir los detalles dentro de cada venta
        for (Venta v : ventas) {
            List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_Id(v.getId());
            v.setDetalles(detalles);
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("ventas", ventas);

        return "perfil";
    }

    // 📄 DESCARGAR PDF DE UNA VENTA
    @GetMapping("/venta/{id}/pdf")
    public void generarPdf(@PathVariable Long id, HttpServletResponse response) throws Exception {

        // Venta usa Integer
        Venta venta = ventaRepository.findById(id.intValue()).orElse(null);
        if (venta == null) return;

        // DetalleVenta usa Long
        List<DetalleVenta> detalles = detalleVentaRepository.findByVenta_Id(id);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=venta_" + id + ".pdf");

        Document documento = new Document();
        PdfWriter.getInstance(documento, response.getOutputStream());
        documento.open();

        Font titulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        documento.add(new Paragraph("Reporte de Compra", titulo));
        documento.add(new Paragraph(" "));

        documento.add(new Paragraph("Fecha: " + venta.getFecha()));
        documento.add(new Paragraph("Método de Pago: " + venta.getMetodoPago()));
        documento.add(new Paragraph("Total: S/. " + venta.getTotal()));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Producto");
        tabla.addCell("Cantidad");
        tabla.addCell("Precio Unit.");
        tabla.addCell("Subtotal");

        for (DetalleVenta d : detalles) {
            tabla.addCell(d.getNombreProducto());
            tabla.addCell(String.valueOf(d.getCantidad()));
            tabla.addCell("S/. " + d.getPrecioUnitario());
            tabla.addCell("S/. " + d.getSubtotal());
        }

        documento.add(tabla);
        documento.close();
    }


}


