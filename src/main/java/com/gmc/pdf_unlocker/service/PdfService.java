package com.gmc.pdf_unlocker.service;


import com.gmc.pdf_unlocker.exception.PdfProcessingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

  private static final String OUTPUT_DIR =
      System.getProperty("os.name").toLowerCase().contains("win")
          ? "C:/unlocked_pdfs/" : "/tmp/unlocked_pdfs/";

  public List<String> unlockPdfs(MultipartFile[] files, String password) throws IOException {
    // Create output directory if it doesn't exist
    Path outputDirPath = Paths.get(OUTPUT_DIR);
    if (!Files.exists(outputDirPath)) {
      Files.createDirectories(outputDirPath);
    }

    List<String> results = new ArrayList<>();

    // Process each file
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        results.add("File " + file.getOriginalFilename() + ": Empty file uploaded");
        continue;
      }

      // Create temporary file for the uploaded PDF
      File tempFile = File.createTempFile("temp_pdf_", ".pdf");
      try {
        file.transferTo(tempFile);

        try (PDDocument document = PDDocument.load(tempFile, password)) {
          if (document.isEncrypted()) {
            document.setAllSecurityToBeRemoved(true);
            String outputFileName = file.getOriginalFilename();
            Path outputPath = Paths.get(OUTPUT_DIR + outputFileName);
            document.save(outputPath.toFile());
            results.add(
                "File " + file.getOriginalFilename() + ": Unlocked and saved to " + outputPath);
          } else {
            results.add("File " + file.getOriginalFilename() + ": Not encrypted");
          }
        } catch (IOException e) {
          results.add(
              "File " + file.getOriginalFilename() + ": Failed to unlock - " + e.getMessage());
        }
      } catch (Exception e) {
        results.add(
            "File " + file.getOriginalFilename() + ": Error processing - " + e.getMessage());
      } finally {
        tempFile.delete();
      }
    }

    if (results.isEmpty()) {
      throw new PdfProcessingException("No files were processed");
    }
    return results;
  }
}