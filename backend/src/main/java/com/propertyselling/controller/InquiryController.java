package com.propertyselling.controller;

import com.propertyselling.dtos.InquiryRequestDTO;
import com.propertyselling.dtos.InquiryResponseUpdateDTO;
import com.propertyselling.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitInquiry(@RequestBody InquiryRequestDTO dto) {
        return ResponseEntity.ok(inquiryService.submitInquiry(dto));
    }

    @GetMapping("/property/{propertyId}/seller/{sellerId}")
    public ResponseEntity<?> getInquiriesOnProperty(@PathVariable Long propertyId, @PathVariable Long sellerId) {
        return ResponseEntity.ok(inquiryService.getInquiriesOnProperty(propertyId, sellerId));
    }

    @PutMapping("/respond")
    public ResponseEntity<?> respondToInquiry(@RequestBody InquiryResponseUpdateDTO dto) {
        return ResponseEntity.ok(inquiryService.respondToInquiry(dto));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getInquiriesForSeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(inquiryService.getInquiriesForSeller(sellerId));
    }


}

