package com.mvdact3_t4.actividadcrud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvdact3_t4.actividadcrud.models.Marca;
import com.mvdact3_t4.actividadcrud.models.Telefono;
import com.mvdact3_t4.actividadcrud.services.TelefonoService;

@RestController
@RequestMapping("/api/telefonos")
public class TelefonoRestController {

    @Autowired
    private TelefonoService telefonoService;

    // 1. GET
    @GetMapping
    public List<Telefono> obtenerTodos() {
        return telefonoService.listarTelefonos();
    }

    // 2. GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Telefono> obtenerPorId(@PathVariable Long id) {
        return telefonoService.buscarTelefonoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST 
    @PostMapping
    public ResponseEntity<Telefono> crear(@RequestBody Telefono telefono) {
        if (telefono.getMarca() != null && telefono.getMarca().getId() != null) {
            Marca marca = telefonoService.buscarMarcaPorId(telefono.getMarca().getId()).orElse(null);
            telefono.setMarca(marca);
        }
        Telefono nuevo = telefonoService.guardarTelefono(telefono);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Telefono> actualizar(@PathVariable Long id, @RequestBody Telefono telefono) {
        if (telefono.getMarca() != null && telefono.getMarca().getId() != null) {
            Marca marca = telefonoService.buscarMarcaPorId(telefono.getMarca().getId()).orElse(null);
            telefono.setMarca(marca);
        }
        Telefono actualizado = telefonoService.actualizarTelefono(id, telefono);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return telefonoService.eliminarTelefono(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
