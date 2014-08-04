package kots.main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageLoader {

	public ImageLoader() {
	}

	public BufferedImage loadImage(String path) {
		
		BufferedImage i = null;
		
		try {
			
			//AUTO FILLS 'SRC/RESOURCES'
			i = ImageIO.read(this.getClass().getResourceAsStream(path));
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
		return i;
		
	}
	
}
