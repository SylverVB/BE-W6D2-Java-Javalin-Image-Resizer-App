import io.javalin.Javalin;
import io.javalin.http.Context;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class ImageResizerApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Image resizing endpoint
        app.get("/resize-image/{imagePath}/{width}/{height}", ctx -> {
            handleResizeImageRequest(ctx); // Call a separate method to handle resizing
        });
    }

    // Refactored method to handle resizing request
    private static void handleResizeImageRequest(Context ctx) {
        // Extract parameters from the URL
        String imagePath = ctx.pathParam("imagePath");
        int width = Integer.parseInt(ctx.pathParam("width"));
        int height = Integer.parseInt(ctx.pathParam("height"));

        // Call the method to resize the image
        byte[] resizedImage = resizeImage(imagePath, width, height); // Call method
        if (resizedImage != null) {
            ctx.contentType("image/jpeg"); // Set content type to image/jpeg
            ctx.result(resizedImage); // Return the resized image in the response
        } else {
            // Handle error if resizing fails
            ctx.status(500).result("Error resizing image: Could not process the image.");
        }
    }

    // Method to resize the image
    private static byte[] resizeImage(String imagePath, int width, int height) {
        try {
            // Load the image from the given path
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Ensure the image was loaded successfully
            if (originalImage == null) {
                return null; // Return null if image couldn't be loaded
            }

            // Create a new image with the specified width and height
            BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();

            // Write the resized image to a byte array and return it
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, "jpg", baos);
                baos.flush();
                return baos.toByteArray();
            }
        } catch (IOException e) {
            System.err.println("Error resizing image: " + e.getMessage());
            return null; // Return null if an error occurs
        }
    }
}