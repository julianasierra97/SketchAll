package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

class Login {

	String userName;
	String passWord;
	Properties prop = new Properties();

	public boolean readFile(String userName, String passsword) {

		try {
			System.out.println("entro");

			InputStream input = new FileInputStream("../SketchAll_v2/src/main/users.properties");
			System.out.println("entro");
			// System.out.println("input" + input);
			prop.load(input);
			// System.out.println("USERNAME PROP"+prop.getProperty(userName));
			// System.out.println("USERNAME pais"+prop.getProperty(pais));
			// prop.setProperty("userName", "holi");

			System.out.println("entro");
			System.out.println(prop.getProperty(userName));

			if (prop.getProperty(userName) != null) {

				if (prop.getProperty(userName).equals(passsword))

				{

					System.out.println("User Exist");
					return true;

				} else {
					System.out.println("Wrong Password");
					return false;
				}

			} else {
				System.out.println("Create user");
				return false;
			}

		} catch (Exception e) { // TODO: handle exception }
			e.printStackTrace();
		}
		return false;
	}

	public void writeFile(String userName, String passsword) {

		try (InputStream in = new FileInputStream("../SketchAll_v2/src/main/users.properties")) {
			prop.load(in);
			prop.setProperty(userName, passsword);

			OutputStream out = new FileOutputStream("../SketchAll_v2/src/main/users.properties");
			prop.store(out, userName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// printProperties(prop);
	}
}
