package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión de productos de la tienda (CRUD completo)")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve la lista completa de productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        List<ProductoResponseDTO> respuesta = productoService.obtenerTodos()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto específico según su ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new com.utn.productos.exception.ProductoNotFoundException("Producto no encontrado con id: " + id));
        return ResponseEntity.ok(toResponseDTO(producto));
    }

    @Operation(summary = "Listar productos por categoría", description = "Devuelve los productos pertenecientes a una categoría específica")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> respuesta = productoService.obtenerPorCategoria(categoria)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto nuevo = toEntity(dto);
        Producto guardado = productoService.crearProducto(nuevo);
        return ResponseEntity
                .created(URI.create("/api/productos/" + guardado.getId()))
                .body(toResponseDTO(guardado));
    }

    @Operation(summary = "Actualizar producto", description = "Reemplaza completamente un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO dto) {
        Producto actualizado = productoService.actualizarProducto(id, toEntity(dto));
        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @Operation(summary = "Actualizar stock", description = "Modifica únicamente el stock de un producto existente")
    @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarStockDTO dto) {
        Producto actualizado = productoService.actualizarStock(id, dto.getStock());
        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto según su ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    private ProductoResponseDTO toResponseDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
        );
    }

    private Producto toEntity(ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());
        return p;
    }
}
