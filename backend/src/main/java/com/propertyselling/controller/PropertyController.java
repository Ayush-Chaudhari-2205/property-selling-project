package com.propertyselling.controller;

import com.propertyselling.dtos.PropertyRequestDTO;
import com.propertyselling.dtos.PropertyStatusUpdateDTO;
import com.propertyselling.dtos.PropertyUpdateDTO;
import com.propertyselling.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/all-active")
    public ResponseEntity<?> getAllActiveProperties() {
        return ResponseEntity.ok(propertyService.getAllActiveProperties());
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

    @PutMapping("/status")
    public ResponseEntity<?> updatePropertyStatus(@RequestBody PropertyStatusUpdateDTO dto) {
        return ResponseEntity.ok(propertyService.updatePropertyStatus(dto));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getPropertiesBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(propertyService.getPropertiesBySeller(sellerId));
    }

    @GetMapping("/count/active")
    public ResponseEntity<?> countActiveProperties() {
        return ResponseEntity.ok(propertyService.countActiveProperties());
    }


    //filter for all the properties
    @GetMapping("/filter/all")
    public ResponseEntity<?> filterProperties(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean furnished) {

        // ✅ Convert empty strings to null
        type = (type != null && !type.trim().isEmpty()) ? type : null;
        city = (city != null && !city.trim().isEmpty()) ? city : null;
        state = (state != null && !state.trim().isEmpty()) ? state : null;

        return ResponseEntity.ok(propertyService.filterProperties(type, minPrice, maxPrice, city, state, furnished));
    }
}
