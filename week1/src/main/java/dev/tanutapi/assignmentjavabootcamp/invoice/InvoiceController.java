package dev.tanutapi.assignmentjavabootcamp.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/checkout/{userId}/{method}")
    InvoiceResponse checkout(@PathVariable Integer userId, @PathVariable String method) {
        // TODO: check JWT to allow checkout of its own sub
        return invoiceService.checkout(userId, method);
    }
}
