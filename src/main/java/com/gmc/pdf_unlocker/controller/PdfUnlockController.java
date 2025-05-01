package com.gmc.pdf_unlocker.controller;

import com.gmc.pdf_unlocker.service.PdfService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PdfUnlockController {

  @Autowired
  private PdfService pdfService;

  @PostMapping("/unlock-pdf")
  public ResponseEntity<List<String>> unlockPdf(
      @RequestParam("files") MultipartFile[] files,
      @RequestParam("password") String password) {
    try {
      List<String> results = pdfService.unlockPdfs(files, password);
      return ResponseEntity.ok(results);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(List.of("Error: " + e.getMessage()));
    }
  }
}
