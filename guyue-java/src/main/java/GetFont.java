/**
 * @ClassName GetFont
 * @Description TOOD
 * @Author lipeng
 * @Date 2019-10-11 21:12
 */
import java.awt.GraphicsEnvironment;

public class GetFont {
	public static void main(String[] args) {
		String[] fontNames=GraphicsEnvironment.getLocalGraphicsEnvironment().
			getAvailableFontFamilyNames();
		for(String fontName:fontNames){
			System.out.println(fontName);
		}
	}
}