package com.earezki.accounts.exposition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeResource {

    @GetMapping(path = "/")
    String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }

}
