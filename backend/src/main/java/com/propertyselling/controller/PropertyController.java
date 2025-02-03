package com.propertyselling.controller;

import com.propertyselling.dtos.PropertyRequestDTO;
import com.propertyselling.dtos.PropertyUpdateDTO;
import com.propertyselling.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
public class    PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<?> addProperty(@RequestBody @Validated PropertyRequestDTO dto) {
        return ResponseEntity.ok(propertyService.addProperty(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProperty(@RequestBody PropertyUpdateDTO dto) {
        return ResponseEntity.ok(propertyService.updateProperty(dto));
    }

    @DeleteMapping("/delete/{propertyId}/seller/{sellerId}")
    public ResponseEntity<?> softDeleteProperty(@PathVariable Long propertyId, @PathVariable Long sellerId) {
        return ResponseEntity.ok(propertyService.softDeleteProperty(propertyId, sellerId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPropertiesByName(@RequestParam String name) {
        return ResponseEntity.ok(propertyService.searchPropertiesByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterPropertiesByType(@RequestParam String type) {
        return ResponseEntity.ok(propertyService.filterPropertiesByType(type));
    }

    @GetMapping("/filter/price")
    public ResponseEntity<?> filterPropertiesByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return ResponseEntity.ok(propertyService.filterPropertiesByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/filter/location")
    public ResponseEntity<?> filterPropertiesByLocation(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        return ResponseEntity.ok(propertyService.filterPropertiesByLocation(city, state));
    }

    @GetMapping("/filter/furnished")
    public ResponseEntity<?> filterPropertiesByFurnishingStatus(@RequestParam boolean furnished) {
        return ResponseEntity.ok(propertyService.filterPropertiesByFurnishingStatus(furnished));
    }
}
