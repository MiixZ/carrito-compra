package org.example.carritocompra.controller;

import lombok.AllArgsConstructor;
import org.example.carritocompra.services.DatabaseService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/adminApi")
public class AdminController {

    private final DatabaseService databaseService;

    @GetMapping("/exportDatabase")
    public ResponseEntity<ByteArrayResource> exportDatabase() {
        byte[] sql = databaseService.exportDatabaseToSql();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=carritodb.sql");

        ByteArrayResource resource = new ByteArrayResource(sql);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(sql.length)
                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
