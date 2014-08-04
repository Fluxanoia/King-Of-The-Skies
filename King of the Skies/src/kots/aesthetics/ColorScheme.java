package kots.aesthetics;

import java.awt.Color;

public class ColorScheme {

	private Color light;
	private Color dark;
	private Color border;
	private Color inner;
	private Color font;
	private Color fontAlt;
	
	public ColorScheme(Color light, Color dark, Color border, Color inner, Color font1, Color font2) {
		
		this.light = light;
		this.dark = dark;
		this.border = border;
		this.inner = inner;
		this.font = font1;
		this.fontAlt = font2;
		
	}

	public Color getLight() {
		return light;
	}

	public Color getDark() {
		return dark;
	}

	public Color getBorder() {
		return border;
	}

	public Color getInner() {
		return inner;
	}
	
	public Color getFont() {
		return font;
	}
	
	public Color getAlternateFont() {
		return fontAlt;
	}

}
