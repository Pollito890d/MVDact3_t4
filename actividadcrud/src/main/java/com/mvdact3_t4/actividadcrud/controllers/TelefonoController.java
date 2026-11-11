package com.mvdact3_t4.actividadcrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvdact3_t4.actividadcrud.models.Marca;
import com.mvdact3_t4.actividadcrud.models.Telefono;
import com.mvdact3_t4.actividadcrud.services.TelefonoService;

@Controller
@RequestMapping("/telefonos")
public class TelefonoController {

    @Autowired
    private TelefonoService telefonoService;

    //Listar
    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("telefonos", telefonoService.listarTelefonos());
        return "telefonos/listar";
    }

    //Formulario nuevo
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("telefono", new Telefono());
        model.addAttribute("marcas", telefonoService.listarMarcas());
        return "telefonos/formulario";
    }

    //Formulario Editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Telefono tel = telefonoService.buscarTelefonoPorId(id).orElse(null);
        if (tel == null) {
            return "redirect:/telefonos";
        }
        model.addAttribute("telefono", tel);
        model.addAttribute("marcas", telefonoService.listarMarcas());
        return "telefonos/formulario";
    }

    @GetMapping("/marcas/nueva")
    public String mostrarFormularioMarca(Model model) {
        model.addAttribute("marca", new Marca());
        return "telefonos/formulario_marca";
    }

    @PostMapping("/marcas/guardar")
    public String guardarMarca(@ModelAttribute("marca") Marca marca) {
        telefonoService.guardarMarca(marca);
        // Redirige al catálogo de teléfonos tras registrar la marca
        return "redirect:/telefonos";
    }

    //Guardar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("telefono") Telefono telefono, @RequestParam("marcaId") Long marcaId) {
        Marca marca = telefonoService.buscarMarcaPorId(marcaId).orElse(null);
        telefono.setMarca(marca);
        telefonoService.guardarTelefono(telefono);
        return "redirect:/telefonos";
    }

    //Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        telefonoService.eliminarTelefono(id);
        return "redirect:/telefonos";
    }
}
