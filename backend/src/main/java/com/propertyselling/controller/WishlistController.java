package com.propertyselling.controller;

import com.propertyselling.dtos.ApiResponse;
import com.propertyselling.dtos.WishlistRequestDTO;
import com.propertyselling.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
    @RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistRequestDTO dto) {
        return ResponseEntity.ok(wishlistService.addToWishlist(dto));
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<?> getWishlist(@PathVariable Long buyerId) {
        return ResponseEntity.ok(wishlistService.getWishlist(buyerId));
    }

    @DeleteMapping("/remove/{buyerId}/{propertyId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long buyerId, @PathVariable Long propertyId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(buyerId, propertyId));
    }
}
