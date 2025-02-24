package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

class ImageResizerAppTest {

    @Test
    void testResizeImage_ValidImage() throws IOException {
        // Arrange: Set up a valid image path and desired dimensions
        String imagePath = "src/test/resources/print-t-shirt-XL.jpg";
        int newWidth = 100;
        int newHeight = 100;

        // Act: Call the resizeImage method
        byte[] result = ImageResizerApp.resizeImage(imagePath, newWidth, newHeight);

        // Assert: The result should not be null
        assertNotNull(result, "Resized image should not be null");

        // Further, verify that the byte array represents an image with the correct dimensions.
        ByteArrayInputStream bais = new ByteArrayInputStream(result);
        BufferedImage resizedImage = ImageIO.read(bais);
        assertNotNull(resizedImage, "Resized image should be a valid BufferedImage");
        assertEquals(newWidth, resizedImage.getWidth(), "Width should match the expected value");
        assertEquals(newHeight, resizedImage.getHeight(), "Height should match the expected value");
    }

    @Test
    void testResizeImage_InvalidPath() {
        // Arrange: Use an invalid image path
        String imagePath = "src/test/resources/nonexistent.jpg";
        int newWidth = 100;
        int newHeight = 100;

        // Act: Attempt to resize an image from an invalid path
        byte[] result = ImageResizerApp.resizeImage(imagePath, newWidth, newHeight);

        // Assert: Expect a null result
        assertNull(result, "Result should be null when the image path is invalid");
    }
}