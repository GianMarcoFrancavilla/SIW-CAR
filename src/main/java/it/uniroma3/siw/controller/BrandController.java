package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;


import it.uniroma3.siw.model.Brand;
import it.uniroma3.siw.repository.BrandRepository;
import it.uniroma3.siw.repository.ModelRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;
    
    @Autowired
    private ModelRepository modelRepository;

    @GetMapping(value = "/admin/indexBrand")
    public String indexBrand() {
        return "admin/indexbrand.html";
    }

    @GetMapping(value = "/admin/formNewBrand")
    public String formNewBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/formNewBrand.html";
    }
    
    @GetMapping(value = "/admin/formUpdateBrands")
    public String formUpdateBrands(Model model) {
    	List<Brand> brands = this.brandRepository.findAll();
        model.addAttribute("brands", brands);
        return "admin/formUpdateBrands.html";
    }

    @PostMapping("/brand")
    public String newBrand(@Valid @ModelAttribute("brand") Brand brand, BindingResult bindingResult, Model model) {
        // Esegui la validazione specifica della marca se necessario
        // this.brandValidator.validate(brand, bindingResult);

        if (!bindingResult.hasErrors()) {
            brandRepository.save(brand);
            model.addAttribute("brand", brand);
            model.addAttribute("brands", this.brandRepository.findAll());
            return "admin/formUpdateBrands"; // Reindirizza alla pagina della marca creata con successo
        } else {
            return "admin/formNewBrand.html"; // Torna al form di creazione della marca con gli errori
        }
    }
    
    @Transactional
    @PostMapping("/admin/elimina-brand/{id}")
    public String eliminaBrand(@PathVariable Long id, Model model) {
        // Trova il brand da eliminare
        Optional<Brand> brandDaEliminareOptional = brandRepository.findById(id);

        if (brandDaEliminareOptional.isPresent()) {
            Brand brandDaEliminare = brandDaEliminareOptional.get();

            // Per ogni modello associato al brand, elimina tutte le auto
            brandDaEliminare.getModels().forEach(modello -> {
                modello.getCars().forEach(car -> {
                    car.setBrand(null); // Dissocia l'auto dal brand
                });
                modelRepository.delete(modello); // Elimina il modello e le sue auto
            });

            // Elimina il brand dalla repository
            brandRepository.delete(brandDaEliminare);
        }

        // Aggiorna la lista dei brand nel model
        List<Brand> brands = brandRepository.findAll();
        model.addAttribute("brands", brands);

        return "admin/formUpdateBrands.html";
    }
}
