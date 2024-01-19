import controller.*;
import model.*;

public class Main {
	
	public static void main(String[] args) {
		IModel model = new Model();
		new Controller(model);
	}
	
}
