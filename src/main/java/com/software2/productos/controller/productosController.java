package com.software2.productos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.apirest.model.Producto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/productos")
public class productosController {

    private List<Producto> productos;

    public productosController() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Producto[] productoArray = objectMapper.readValue(new ClassPathResource("productos.json").getFile(), Producto[].class); 
            productos = new ArrayList<>(Arrays.asList(productoArray));
        } catch (IOException e) {
            e.printStackTrace();
            productos = new ArrayList<>();
        }
    }

    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productos;
    }

    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable Long id) {
        return productos.stream().filter(producto -> producto.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        productos.add(producto);
        return producto;
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            return producto;
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productos.removeIf(producto -> producto.getId().equals(id));
    }
}
