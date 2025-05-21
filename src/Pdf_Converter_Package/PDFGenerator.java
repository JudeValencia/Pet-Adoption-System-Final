package Pdf_Converter_Package;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.Arrays;

public class PDFGenerator {
    // Constants with improved visibility
    public static final String REPORTS_DIR = "reports";
    public static final String ADOPTION_REPORTS_DIR = REPORTS_DIR + File.separator + "adoption_reports";
    public static final String REMAINING_PETS_DIR = REPORTS_DIR + File.separator + "remaining_pets_reports";

    public PDFGenerator() {
        createDirectories();
    }

    private void createDirectories() {
        try {
            createDir(REPORTS_DIR);
            createDir(ADOPTION_REPORTS_DIR);
            createDir(REMAINING_PETS_DIR);
        } catch (IOException e) {
            System.err.println("❌ Failed to create report directories: " + e.getMessage());
        }
    }

    private void createDir(String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory: " + dir.getAbsolutePath());
        }
    }

    // Main_Package.Main PDF generation method
    public void generateAdoptionReport(String textFilePath) throws IOException, DocumentException {
        validateInputFile(textFilePath);
        String outputPath = buildOutputPath(textFilePath, ADOPTION_REPORTS_DIR);
        convertTextToPDF(textFilePath, outputPath, "Adoption Report");
        System.out.println("✅ Adoption report generated: " + outputPath);
    }

    public void generateRemainingPetsReport(String textFilePath) throws IOException, DocumentException {
        validateInputFile(textFilePath);
        String outputPath = buildOutputPath(textFilePath, REMAINING_PETS_DIR);
        convertTextToPDF(textFilePath, outputPath, "Remaining Pets Report");
        System.out.println("✅ Remaining pets report generated: " + outputPath);
    }

    // Helper methods
    private void validateInputFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Input file not found: " + file.getAbsolutePath());
        }
        if (file.length() == 0) {
            try {
                throw new IOException("Input file is empty: " + path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String buildOutputPath(String inputPath, String outputDir) {
        String fileName = new File(inputPath).getName().replace(".txt", ".pdf");
        return outputDir + File.separator + fileName;
    }

    private void convertTextToPDF(String textFilePath, String outputPath, String title)
            throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            addTitle(document, title);
            addContent(document, textFilePath);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addTitle(Document document, String title) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(title, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(20);
        document.add(paragraph);
    }

    private void addContent(Document document, String filePath) throws IOException, DocumentException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            while ((line = reader.readLine()) != null) {
                document.add(new Paragraph(line, font));
            }
        }
    }

    // Improved file search with debugging
    public String getMostRecentTextFile(String directoryPath) {
        File dir = new File(directoryPath);
        logDirectoryContents(dir); // Debug helper

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.err.println("⚠️ No .txt files found in: " + dir.getAbsolutePath());
            return null;
        }

        return Arrays.stream(files)
                .max(File::compareTo)
                .map(File::getAbsolutePath)
                .orElse(null);
    }

    private void logDirectoryContents(File dir) {
        System.out.println("\n📁 Directory contents of " + dir.getAbsolutePath() + ":");
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(f ->
                    System.out.println(f.getName() + " (last modified: " + f.lastModified() + ")"));
        }
    }
}