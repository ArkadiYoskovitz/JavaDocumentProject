package app;

import controller.MainViewController;

public class AppDelegate {
	// Attribute
	private static MainViewController mainViewController;

	// Main 
	public static void main(String[] args) {
		setMainViewController(new MainViewController());
	}

	public static MainViewController getMainViewController() {
		return mainViewController;
	}

	public static void setMainViewController(MainViewController mainViewController) {
		AppDelegate.mainViewController = mainViewController;
	}
}
